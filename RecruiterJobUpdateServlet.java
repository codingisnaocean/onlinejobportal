package com.dev.ojp.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dev.ojp.beans.JobDetails;
import com.dev.ojp.dao.JobsDao;

/**
 * Servlet implementation class RecruiterJobUpdateServlet
 */
@WebServlet("/RecruiterJobUpdateServlet")
public class RecruiterJobUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RecruiterJobUpdateServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JobDetails jobDetails = new JobsDao().getJobDetailsById(Integer.valueOf(request.getParameterValues("jobid")[0]));
		request.setAttribute("jobDetails", jobDetails);
		getServletContext().getRequestDispatcher("/recruit/editjob.jsp").forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JobDetails jobDetail = new JobDetails(Integer.valueOf(request.getParameterValues("jobid")[0]), request.getParameterValues("jobTitle")[0], request.getParameterValues("jobDescription")[0], request.getParameterValues("keywords")[0],request.getParameterValues("userid")[0]
				,request.getParameterValues("company")[0],request.getParameterValues("experience")[0],request.getParameterValues("location")[0],Double.valueOf(request.getParameterValues("cutoff")[0]));
		new JobsDao().updateJobDetails(jobDetail);
		List<JobDetails> jobDetails = new JobsDao().getJobDetailsByUser(Integer.valueOf(request.getParameterValues("userid")[0]));
		request.setAttribute("jobDetailsList", jobDetails);
		response.sendRedirect("/OnlineJobPortal/recruit/recruiterhome.html?id="+request.getParameterValues("userid")[0]);
	}

}
