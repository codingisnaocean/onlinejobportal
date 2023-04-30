package com.dev.ojp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.dev.ojp.beans.UserDetails;
import com.dev.ojp.utils.MySQLConnUtils;

public class UserDao {

	
	private final String insertQuery = "insert into userdetails(firstName,lastName,emailAddress,password,resume,type,mobile,company,profileimage,location) values(?,?,?,?,?,?,?,?,?,?)";
	private final String updateQuery = "update userdetails set firstName=?,lastName=?,resume=?,mobile=?,company=?,profileimage=?,location=? where id=?";
	private final String retrieveQuery = "select id,firstName,lastName,emailAddress,password,resume,type,mobile,company,profileimage,location from userdetails where type='user'";
	private final String retrieveUserById = "select id,firstName,lastName,emailAddress,password,resume,type,mobile,company,profileimage,location from userdetails where id=?";
	private final String loginQuery = "select id,firstName,lastName,emailAddress,password,resume,type,mobile,company,profileimage,location from userdetails where emailAddress=? and password=? and type=?";
	
	public int saveUserDetails(UserDetails userDetails) {
		try(Connection connection = MySQLConnUtils.getMySQLConnection(); PreparedStatement prSt = connection.prepareStatement(insertQuery);){
            prSt.setString(1, userDetails.getFirstName());
            prSt.setString(2, userDetails.getLastname());
            prSt.setString(3, userDetails.getEmailAddress());
            prSt.setString(4, userDetails.getPassword());
            prSt.setString(5, userDetails.getResume());
            prSt.setString(6, userDetails.getType());
            prSt.setString(7, userDetails.getMobile());
            prSt.setString(8, userDetails.getCompany());
            prSt.setString(9, userDetails.getProfileimage());
            prSt.setString(10, userDetails.getLocation());
            return prSt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
        }
		return 0;
	}
	
	public UserDetails login(String emailAddress,String password,String type){
		
		UserDetails userDetails =null;
		try(Connection connection = MySQLConnUtils.getMySQLConnection();PreparedStatement stmt = connection.prepareStatement(loginQuery)) {
			stmt.setString(1, emailAddress);
			stmt.setString(2, password);
			stmt.setString(3, type);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
				userDetails = new UserDetails(rs.getInt("id"), rs.getString("firstName"), rs.getString("lastname"), rs.getString("emailAddress"), rs.getString("password"),
						rs.getString("resume"), rs.getString("type"),rs.getString("mobile"),rs.getString("company"),rs.getString("profileimage"),rs.getString("location"));
			}
			return userDetails;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<UserDetails> getUserDetails(){
		try(Connection connection = MySQLConnUtils.getMySQLConnection();Statement stmt = connection.createStatement();) {
			ResultSet rs = stmt.executeQuery(retrieveQuery);
			List<UserDetails> list = new ArrayList<>();
			while(rs.next()){
				UserDetails userDetails = new UserDetails(rs.getInt("id"), rs.getString("firstName"), rs.getString("lastname"), rs.getString("emailAddress"),
						rs.getString("password"), rs.getString("resume"), rs.getString("type"),rs.getString("mobile"),rs.getString("company"),rs.getString("profileimage"),rs.getString("location"));
				list.add(userDetails);
			}
			return list;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public UserDetails getUserDetailsById(Integer id) {
		UserDetails userDetails =null;
		try(Connection connection = MySQLConnUtils.getMySQLConnection();PreparedStatement stmt = connection.prepareStatement(retrieveUserById)) {
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
				userDetails = new UserDetails(rs.getInt("id"), rs.getString("firstName"), rs.getString("lastname"), rs.getString("emailAddress"),
						rs.getString("password"), rs.getString("resume"), rs.getString("type"),rs.getString("mobile"),rs.getString("company"),rs.getString("profileimage"),rs.getString("location"));
			}
			return userDetails;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public int updateUserDetails(UserDetails userDetails) {
		try(Connection connection = MySQLConnUtils.getMySQLConnection(); PreparedStatement prSt = connection.prepareStatement(updateQuery);){
            prSt.setString(1, userDetails.getFirstName());
            prSt.setString(2, userDetails.getLastname());
            prSt.setString(3, userDetails.getResume());
            prSt.setString(4, userDetails.getMobile());
            prSt.setString(5, userDetails.getCompany());
            prSt.setString(6, userDetails.getProfileimage());
            prSt.setString(7, userDetails.getLocation());
            prSt.setInt(8, userDetails.getId());
            return prSt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
        }
		return 0;
	}

}
