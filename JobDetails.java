package com.dev.ojp.beans;

public class JobDetails {

	private Integer id;
	private String jobTitle;
	private String jobDescription;
	private String keywords;
	private String createdby;
	private String company;
	private String experience;
	private String location;
	private String matchPercent;
	private double cutoff;
	
	public JobDetails() {
		super();
	}

	public JobDetails(Integer id, String jobTitle, String jobDescription, String keywords,String createdby,String company,
			String experience,String location,double cutoff) {
		super();
		this.id = id;
		this.jobTitle = jobTitle;
		this.jobDescription = jobDescription;
		this.keywords = keywords;
		this.createdby = createdby;
		this.company = company;
		this.experience = experience;
		this.location = location;
		this.cutoff = cutoff;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getJobTitle() {
		return jobTitle;
	}
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	public String getJobDescription() {
		return jobDescription;
	}
	public void setJobDescription(String jobDescription) {
		this.jobDescription = jobDescription;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getCreatedby() {
		return createdby;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getExperience() {
		return experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
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

	public double getCutoff() {
		return cutoff;
	}

	public void setCutoff(double cutoff) {
		this.cutoff = cutoff;
	}
	
}
