package com.dev.ojp.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dev.ojp.beans.JobDetails;
import com.dev.ojp.beans.UserDetails;
import com.dev.ojp.dao.JobsDao;
import com.dev.ojp.dao.UserDao;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 10, // 10 MB
maxFileSize = 1024 * 1024 * 50, // 50 MB
maxRequestSize = 1024 * 1024 * 100)
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<JobDetails> jobDetails = new JobsDao().getJobDetails();
		request.setAttribute("jobDetailsList", jobDetails);
		getServletContext().getRequestDispatcher("/jobdetailslist.jsp").forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserDetails details = new UserDao().login(request.getParameterValues("emailAddress")[0], request.getParameterValues("password")[0], "user");
		request.setAttribute("data", details);
		if(details== null) {
			response.sendRedirect("/OnlineJobPortal/login.html");
		}else {
			response.sendRedirect("/OnlineJobPortal/userhome.html?id="+details.getId());
		}
	}
}
