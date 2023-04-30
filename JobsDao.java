package com.dev.ojp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.dev.ojp.beans.JobDetails;
import com.dev.ojp.utils.MySQLConnUtils;

public class JobsDao {

	
	private final String insertQuery = "insert into jobdetails(jobTitle,jobDescription,keywords,createdby,company,experience,location,cutoff) values(?,?,?,?,?,?,?,?)";
	private final String updateQuery = "update jobdetails set jobTitle=?,jobDescription=?,keywords=?,company=?,experience=?,location=?,cutoff=? where id=?";
	private final String retrieveQuery = "select id,jobTitle,jobDescription,keywords,createdby,company,experience,location,cutoff from jobdetails";
	private final String retrieveByUserQuery = "select id,jobTitle,jobDescription,keywords,createdby,company,experience,location,cutoff from jobdetails where createdby=?";
	private final String retrieveByIdQuery = "select id,jobTitle,jobDescription,keywords,createdby,company,experience,location,cutoff from jobdetails where id=?";
	
	public int saveJobDetails(JobDetails jobDetails) {
		try(Connection connection = MySQLConnUtils.getMySQLConnection(); PreparedStatement prSt = connection.prepareStatement(insertQuery);){
            prSt.setString(1, jobDetails.getJobTitle());
            prSt.setString(2, jobDetails.getJobDescription());
            prSt.setString(3, jobDetails.getKeywords());
            prSt.setString(4, jobDetails.getCreatedby());
            prSt.setString(5, jobDetails.getCompany());
            prSt.setString(6, jobDetails.getExperience());
            prSt.setString(7, jobDetails.getLocation());
            prSt.setDouble(8, jobDetails.getCutoff());
            return prSt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
        }
		return 0;
	}
	
	public List<JobDetails> getJobDetails(){
		List<JobDetails> jobDetails =new ArrayList<>();
		try(Connection connection = MySQLConnUtils.getMySQLConnection();Statement stmt = connection.createStatement();) {
			ResultSet rs = stmt.executeQuery(retrieveQuery);
			while(rs.next()){
				JobDetails 	jobDetail = new JobDetails(rs.getInt("id"), rs.getString("jobTitle"), rs.getString("jobDescription"), rs.getString("keywords"),rs.getString("createdby"),rs.getString("company"),rs.getString("experience"),rs.getString("location"),rs.getDouble("cutoff"));
				jobDetails.add(jobDetail);
			}
			return jobDetails;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public JobDetails getJobDetailsById(Integer id) {
		JobDetails details =null;
		try(Connection connection = MySQLConnUtils.getMySQLConnection();PreparedStatement stmt = connection.prepareStatement(retrieveByIdQuery)) {
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
				details = new JobDetails(rs.getInt("id"), rs.getString("jobTitle"), rs.getString("jobDescription"), rs.getString("keywords"),rs.getString("createdby"),rs.getString("company"),rs.getString("experience"),rs.getString("location"),rs.getDouble("cutoff"));
			}
			return details;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public List<JobDetails> getJobDetailsByUser(Integer id) {
		List<JobDetails> jobDetails =new ArrayList<>();
		try(Connection connection = MySQLConnUtils.getMySQLConnection();PreparedStatement stmt = connection.prepareStatement(retrieveByUserQuery)) {
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
				JobDetails details = new JobDetails(rs.getInt("id"), rs.getString("jobTitle"), rs.getString("jobDescription"), rs.getString("keywords"),rs.getString("createdby"),rs.getString("company"),rs.getString("experience"),rs.getString("location"),rs.getDouble("cutoff"));
				jobDetails.add(details);
			}
			return jobDetails;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public int updateJobDetails(JobDetails jobDetails) {
		try(Connection connection = MySQLConnUtils.getMySQLConnection(); PreparedStatement prSt = connection.prepareStatement(updateQuery);){
            prSt.setString(1, jobDetails.getJobTitle());
            prSt.setString(2, jobDetails.getJobDescription());
            prSt.setString(3, jobDetails.getKeywords());
            prSt.setString(4, jobDetails.getCompany());
            prSt.setString(5, jobDetails.getExperience());
            prSt.setString(6, jobDetails.getLocation());
            prSt.setDouble(7, jobDetails.getCutoff());
            prSt.setInt(8, jobDetails.getId());
            return prSt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
        }
		return 0;
	}
}
