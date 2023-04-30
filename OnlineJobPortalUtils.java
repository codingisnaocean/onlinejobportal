package com.dev.ojp.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Part;

public class OnlineJobPortalUtils {

	public static String getSubmittedFileName(Part part) {
		for (String cd : part.getHeader("content-disposition").split(";")) {
			if (cd.trim().startsWith("filename")) {
				String fileName = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
				return fileName.substring(fileName.lastIndexOf('/') + 1).substring(fileName.lastIndexOf('\\') + 1); // MSIE
																													// fix.
			}
		}
		return null;
	}
	
	public static String getStatusValue(String statusCode) {
		switch(statusCode){  
	    case "200": return "OK";  
	    case "201": return "Created";  
	    case "204": return "No Content";  
	    case "302": return "Found";  
	    case "304": return "Not Modified";  
	    case "400": return "Bad request";  
	    case "401": return "UnAuthorized";  
	    case "403": return "Forbidden";  
	    case "404": return "Not Found";  
	    case "408": return "Request Timeout";  
	    case "409": return "Conflict";  
	    default:return "NOT OK";  
	    }  
	}
	

	public static String getPercentageValue(String str) {
	    Pattern pattern = Pattern.compile("(\\d+\\.\\d+%|\\d+%|\\d+ %|\\d+\\.\\d+ %)");
	    Matcher matcher = pattern.matcher(str);
	    String number = "";
	    while (matcher.find()) {
	        number = matcher.group(0);
	    }
	    return number.replaceAll("%", "").trim();
	}
}
