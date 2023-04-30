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

/**
 * Servlet implementation class UserJobDetails
 */
@WebServlet("/UserJobDetails")
public class UserJobDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserJobDetails() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserDetails details = new UserDao().getUserDetailsById(Integer.valueOf(request.getParameterValues("userId")[0]));
		File file = new File(details.getResume());
		List<JobDetails> jobDetails = new ArrayList<>();
        WordExtractor extractor = null;
        try(FileInputStream fis = new FileInputStream(file.getAbsolutePath())) {
        	StringBuilder builder = new StringBuilder();
            if(details.getResume().endsWith("docx")) {
            	XWPFDocument xwpfDocument = new XWPFDocument(fis);
            	List<XWPFParagraph> paragraphs = xwpfDocument.getParagraphs();
                for (XWPFParagraph para : paragraphs) {
                	builder.append(para.getText());
                }
            }else {
            	HWPFDocument document = new HWPFDocument(fis);
            	extractor = new WordExtractor(document);
            	String[] fileData = extractor.getParagraphText();
            	for (int i = 0; i < fileData.length; i++){
            		if (fileData[i] != null) {
            			builder.append(fileData[i].replaceAll("\r\n","").replaceAll("\n","").trim());
            		}
            	}
            }
            	
        	List<JobDetails> list = new JobsDao().getJobDetails();
        	for (JobDetails jobDetails2 : list) {
        		int matchedKeys=0;
        		String text = builder.toString();
        		List<String> tokens = Arrays.asList(StringUtils.stripAll(jobDetails2.getKeywords().split(",")));
				tokens = tokens.stream().map(s -> s.toLowerCase()).collect(Collectors.toList());
				for (String token : tokens) {
					boolean contains = text.toLowerCase().matches(".*\\b("+token+")\\b.*");
					if(contains) {
						matchedKeys+=1;
					}
				}
				
        		if(matchedKeys > 0) {
        			jobDetails2.setMatchPercent(String.valueOf(new DecimalFormat("#.00").format(((float)matchedKeys/tokens.size())*100)));
        			jobDetails.add(jobDetails2);
        		}
			}
        	request.setAttribute("userDetails", details);
            request.setAttribute("jobDetailsList", jobDetails);
    		getServletContext().getRequestDispatcher("/joblist.jsp").forward(request, response);
        }catch (Exception exep){
            exep.printStackTrace();
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
