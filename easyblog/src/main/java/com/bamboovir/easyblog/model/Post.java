package com.bamboovir.easyblog.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Post implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8060013629256772291L;
	
	@JsonProperty("post_id")
    private String postId;
	@JsonProperty("user_id")
	private String userId;
	@JsonProperty("post_name")
	private String postName;
    @JsonProperty("post_time")
    private Long postTime;
    @JsonProperty("pic_num")
    private Integer picNum;
    @JsonProperty("is_private")
    private Boolean isPrivate;
    
	private String description;
    private String url;
    
    public Post() {
    	this.postId = "";
    	this.userId = "";
    	this.postName = "";
    	this.postTime = 0L;
    	this.url = "";
    	this.picNum = 0;
    	isPrivate = false;
    	description = "";
    }
    
    public String getDescription() {
		return this.description;
	}
	
	public void setDescription(String description) {
		this.description = description; 
	}
	
    public String getPostName(){
    	return this.postName;
    }
    
    public void setPostName(String postName) {
    	this.postName = postName;
    }
    
    public Boolean getIsPrivate() {
    	return this.isPrivate;
    }
    
    public void setIsPrivate(Boolean isPrivate) {
    	this.isPrivate = isPrivate;
    }
    
    public void setPicNum(Integer picsNum) {
    	this.picNum = picsNum;
    }
    
    public Integer getPicNum() {
    	return this.picNum;
    }
            
    public void setPostId(String postId) {
         this.postId = postId;
     }
     public String getPostId() {
         return postId;
     }

    public void setPostTime(Long postTime) {
         this.postTime = postTime;
     }
     public Long getPostTime() {
         return postTime;
     }

    public void setUserId(String userId) {
         this.userId = userId;
     }
     public String getUserId() {
         return userId;
     }

    public void setUrl(String url) {
         this.url = url;
     }
     public String getUrl() {
         return url;
     }

}
