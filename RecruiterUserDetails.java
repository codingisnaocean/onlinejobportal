package com.dev.ojp.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import com.dev.ojp.beans.JobDetails;
import com.dev.ojp.beans.UserDetails;
import com.dev.ojp.dao.JobsDao;
import com.dev.ojp.dao.UserDao;
import com.dev.ojp.utils.OnlineJobPortalUtils;

/**
 * Servlet implementation class RecruiterUserDetails
 */
@WebServlet("/RecruiterUserDetails")
public class RecruiterUserDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RecruiterUserDetails() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<UserDetails> userlist = new UserDao().getUserDetails();
		UserDetails userDetails= new UserDao().getUserDetailsById(Integer.valueOf(request.getParameterValues("userId")[0]));
		JobDetails jobDetail = new JobsDao().getJobDetailsById(Integer.valueOf(request.getParameterValues("jobId")[0]));
		List<UserDetails> userDetailslist = new ArrayList<>();

		for (UserDetails details : userlist) {
			File file = new File(details.getResume());
			List<Double> percentages = new ArrayList<>();
			boolean qualify = true;
			WordExtractor extractor = null;
			try (FileInputStream fis = new FileInputStream(file.getAbsolutePath())) {
				StringBuilder builder = new StringBuilder();
				if (details.getResume().endsWith("docx")) {
					XWPFDocument xwpfDocument = new XWPFDocument(fis);
					List<XWPFParagraph> paragraphs = xwpfDocument.getParagraphs();
					for (XWPFParagraph para : paragraphs) {
						builder.append(para.getText());
						String percentageValue =OnlineJobPortalUtils.getPercentageValue(para.getText());
						if(!percentageValue.equals("")) {
							percentages.add(Double.valueOf(percentageValue));
						}
					}
				} else {
					HWPFDocument document = new HWPFDocument(fis);
					extractor = new WordExtractor(document);
					String[] fileData = extractor.getParagraphText();
					for (int i = 0; i < fileData.length; i++) {
						if (fileData[i] != null) {
							String percentageValue =OnlineJobPortalUtils.getPercentageValue(fileData[i]);
							if(!percentageValue.equals("")) {
								percentages.add(Double.valueOf(percentageValue));
							}
							builder.append(fileData[i]);
						}
					}
				}
				int matchedKeys=0;
				if(percentages.size() > 0) {
					for (Double double1 : percentages) {
						if(jobDetail.getCutoff() > double1) {
							qualify = false;
						}
					}
				}
				if(!qualify) {
					continue;
				}
				String text = builder.toString().toLowerCase();
				List<String> tokens = Arrays.asList(StringUtils.stripAll(jobDetail.getKeywords().split(",")));
				tokens = tokens.stream().map(s -> s.toLowerCase()).collect(Collectors.toList());
				for (String token : tokens) {
					boolean contains = text.matches(".*\\b("+token+")\\b.*");
					if(contains) {
						matchedKeys+=1;
					}
				}
				if (matchedKeys>0) {
					details.setMatchPercent(String.valueOf(new DecimalFormat("#.00").format(((float)matchedKeys/tokens.size())*100)));
					userDetailslist.add(details);
				}
			} catch (Exception exep) {
				exep.printStackTrace();
			}
		}
		request.setAttribute("jobDetails", jobDetail);
		request.setAttribute("userDetails", userDetails);
		request.setAttribute("userList", userDetailslist);
		getServletContext().getRequestDispatcher("/recruit/jobuserlist.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserDetails details = new UserDetails(Integer.valueOf(request.getParameterValues("userid")[0]), request.getParameterValues("firstName")[0], request.getParameterValues("lastName")[0],
				"", "", "", "recruiter", request.getParameterValues("mobile")[0],
				request.getParameterValues("company")[0], "","");
		new UserDao().updateUserDetails(details);
		response.sendRedirect("/OnlineJobPortal/recruit/profile.jsp?id="+request.getParameterValues("userid")[0]);
	}

}
