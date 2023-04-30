package com.dev.ojp.beans;

public class UserDetails {
	
	private Integer id;
	private String firstName;
	private String lastname;
	private String emailAddress;
	private String password;
	private String resume;
	private String type;
	private String mobile;
	private String company;
	private String profileimage;
	private String location;
	private String matchPercent;
	
	public UserDetails() {
		super();
	}
	public UserDetails(Integer id, String firstName, String lastname, String emailAddress, String password,
			String resume,String type,String mobile, String company,String profileimage,String location) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastname = lastname;
		this.emailAddress = emailAddress;
		this.password = password;
		this.resume = resume;
		this.type = type;
		this.mobile=mobile;
		this.company=company;
		this.profileimage=profileimage;
		this.location=location;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getResume() {
		return resume;
	}
	public void setResume(String resume) {
		this.resume = resume;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getProfileimage() {
		return profileimage;
	}
	public void setProfileimage(String profileimage) {
		this.profileimage = profileimage;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getMatchPercent() {
		return matchPercent;
	}
	public void setMatchPercent(String matchPercent) {
		this.matchPercent = matchPercent;
	}
	
}
