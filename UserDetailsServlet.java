package com.dev.ojp.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;

import com.dev.ojp.beans.UserDetails;
import com.dev.ojp.dao.UserDao;

/**
 * Servlet implementation class UserDetailsServlet
 */
@WebServlet("/UserDetailsServlet")
public class UserDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserDetailsServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserDetails details = new UserDao().getUserDetailsById(Integer.valueOf(request.getParameterValues("userId")[0]));
		request.setAttribute("userDetails", details);
		getServletContext().getRequestDispatcher("/userdetails.jsp").forward(request, response);
	}

	private static final String UPLOAD_DIRECTORY = "upload";
	private static final int THRESHOLD_SIZE = 1024 * 1024 * 3; // 3MB
	private static final int MAX_FILE_SIZE = 1024 * 1024 * 40; // 40MB
	private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 50; // 50MB

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, String> params = new HashMap<>();
		String newpath = "";
		if (!ServletFileUpload.isMultipartContent(request)) {
			PrintWriter writer = response.getWriter();
			writer.flush();
			return;
		}

		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(THRESHOLD_SIZE);
		factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setFileSizeMax(MAX_FILE_SIZE);
		upload.setSizeMax(MAX_REQUEST_SIZE);

		String uploadPath = getServletContext().getRealPath("") +UPLOAD_DIRECTORY;
		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists()) {
			uploadDir.mkdir();
		}
		try {
			List formItems = upload.parseRequest(request);
			Iterator iter = formItems.iterator();
			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next();
				if (!item.isFormField()) {
					String fileName = new File(item.getName()).getName();
					if (!StringUtils.isEmpty(fileName)) {
						newpath = uploadPath + File.separator +System.currentTimeMillis()+fileName;
						File storeFile = new File(newpath);
						item.write(storeFile);
					}
				} else {
					params.put(item.getFieldName(), item.getString());
				}
			}
			request.setAttribute("message", "Upload has been done successfully!");
		} catch (Exception ex) {
			request.setAttribute("message", "There was an error: " + ex.getMessage());
		}
		if(newpath.equals("")) {
			newpath= params.get("filesrc");
		}
		UserDetails details = new UserDetails(Integer.valueOf(params.get("userid")), params.get("firstName"), params.get("lastName"),
				"", "", newpath, "user", params.get("mobile"),
				"", "",params.get("location"));
		new UserDao().updateUserDetails(details);
		response.sendRedirect("/OnlineJobPortal/userprofile.jsp?id="+params.get("userid"));
	}

}
