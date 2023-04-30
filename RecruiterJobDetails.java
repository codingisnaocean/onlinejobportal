package com.dev.ojp.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
 * Servlet implementation class RecruiterJobDetails
 */
@WebServlet("/RecruiterJobDetails")
public class RecruiterJobDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RecruiterJobDetails() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<JobDetails> jobDetails = new JobsDao().getJobDetailsByUser(Integer.valueOf(request.getParameterValues("userid")[0]));
		request.setAttribute("jobDetailsList", jobDetails);
		UserDetails details = new UserDao().getUserDetailsById(Integer.valueOf(request.getParameterValues("userid")[0]));
		request.setAttribute("userDetails", details);
		getServletContext().getRequestDispatcher("/recruit/jobdetailslist.jsp").forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
