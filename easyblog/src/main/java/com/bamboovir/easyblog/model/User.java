package com.bamboovir.easyblog.model;

import java.io.Serializable;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User implements Serializable{
	
	 /**
	 * 
	 */
		private static final long serialVersionUID = -7256914758680549121L;
	
		private String id;
	    private String type;
	    private String gender;
	    
	    @JsonProperty("user_Name")
	    private String userName;
	    @JsonProperty("full_name")
	    private String fullName;
	    @JsonProperty("first_name")
	    private String firstName;
	    @JsonProperty("last_name")
	    private String lastName;
	    private String email;
	    @JsonProperty("phone_number")
	    private String phoneNumber;
	    @JsonProperty("gplus_url")
	    private String gplusUrl;
	    @JsonProperty("domain_url")
	    private String domainUrl;
	    @JsonProperty("twitter_url")
	    private String twitterUrl;
	    @JsonProperty("facebook_url")
	    private String facebookUrl;
	    @JsonProperty("created_at")
	    private Long createdAt;
	    private String country;
	    @JsonProperty("image_medium_url")
	    private String imageMediumUrl;
	    @JsonProperty("image_small_url")
	    private String imageSmallUrl;
	    @JsonProperty("image_large_url")
	    private String imageLargeUrl;
	    private Integer posts;
	    private String authId;
	    @JsonProperty("passwd_md5")
	    private String passwdMD5;
	    private String description;
	    
	    
	    public User() {
	    	this.id = "";
	    	this.type = "UnAuthUser";
	    	this.gender = "Unknown";
	    	this.userName = "";
	    	this.fullName = "";
	    	this.firstName = "";
	    	this.lastName = "";
	    	this.email = "";
	    	this.phoneNumber = "";
	    	this.gplusUrl = "";
	    	this.domainUrl = "";
	    	this.twitterUrl = "";
	    	this.facebookUrl = "";
	    	this.createdAt = 0L;
	    	this.country = "";
	    	this.imageLargeUrl = "";
	    	this.imageMediumUrl = "";
	    	this.imageSmallUrl = "";
	    	this.posts = 0;
	    	this.authId = "";
	    	this.passwdMD5 = "";
	    	this.description = "";
	    }
	    
	    public void setDescription(String description) {
	         this.description = description;
	     }
	     public String getDescription() {
	         return this.description;
	     }
	    
	    public void setPasswdMD5(String passwdMD5) {
	         this.passwdMD5 = passwdMD5;
	     }
	     public String getPasswdMD5() {
	         return this.passwdMD5;
	     }
	    
	    
	    public void setId(String id) {
	         this.id = id;
	     }
	     public String getId() {
	         return this.id;
	     }
	     
	     public void setPosts(Integer posts) {
	         this.posts = posts;
	     }
	     public Integer getPosts() {
	         return this.posts;
	     }
	     
	     public void setAuthId(String authId) {
	         this.authId = authId;
	     }
	     public String getAuthId() {
	         return this.authId;
	     }

	    public void setType(String type) {
	         this.type = type;
	     }
	     public String getType() {
	         return type;
	     }

	    public void setGender(String gender) {
	         this.gender = gender;
	     }
	     public String getGender() {
	         return gender;
	     }

	    public void setUserName(String userName) {
	         this.userName = userName;
	     }
	     public String getUserName() {
	         return userName;
	     }

	    public void setFullName(String fullName) {
	         this.fullName = fullName;
	     }
	     public String getFullName() {
	         return fullName;
	     }

	    public void setFirstName(String firstName) {
	         this.firstName = firstName;
	     }
	     public String getFirstName() {
	         return firstName;
	     }

	    public void setLastName(String lastName) {
	         this.lastName = lastName;
	     }
	     public String getLastName() {
	         return lastName;
	     }

	    public void setEmail(String email) {
	         this.email = email;
	     }
	     public String getEmail() {
	         return email;
	     }

	    public void setPhoneNumber(String phoneNumber) {
	         this.phoneNumber = phoneNumber;
	     }
	     public String getPhoneNumber() {
	         return phoneNumber;
	     }

	    public void setGplusUrl(String gplusUrl) {
	         this.gplusUrl = gplusUrl;
	     }
	     public String getGplusUrl() {
	         return gplusUrl;
	     }

	    public void setDomainUrl(String domainUrl) {
	         this.domainUrl = domainUrl;
	     }
	     public String getDomainUrl() {
	         return domainUrl;
	     }

	    public void setTwitterUrl(String twitterUrl) {
	         this.twitterUrl = twitterUrl;
	     }
	     public String getTwitterUrl() {
	         return twitterUrl;
	     }

	    public void setFacebookUrl(String facebookUrl) {
	         this.facebookUrl = facebookUrl;
	     }
	     public String getFacebookUrl() {
	         return facebookUrl;
	     }

	    public void setCreatedAt(Long createdAt) {
	         this.createdAt = createdAt;
	     }
	     public Long getCreatedAt() {
	         return this.createdAt;
	     }

	    public void setCountry(String country) {
	         this.country = country;
	     }
	     public String getCountry() {
	         return country;
	     }

	    public void setImageMediumUrl(String imageMediumUrl) {
	         this.imageMediumUrl = imageMediumUrl;
	     }
	     public String getImageMediumUrl() {
	         return imageMediumUrl;
	     }

	    public void setImageSmallUrl(String imageSmallUrl) {
	         this.imageSmallUrl = imageSmallUrl;
	     }
	     public String getImageSmallUrl() {
	         return imageSmallUrl;
	     }
	     
	     public void setImageLargeUrl(String imageLargeUrl) {
	         this.imageLargeUrl = imageLargeUrl;
	     }
	     public String getImageLargeUrl() {
	         return this.imageLargeUrl;
	     }
}
