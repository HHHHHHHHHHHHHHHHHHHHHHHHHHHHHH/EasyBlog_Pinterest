package com.bamboovir.easyblog.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Channle implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4960566292059096406L;
	
	@JsonProperty("channle_id")
	private String channleId;
	@JsonProperty("channle_name")
	private String channleName;
	private String description;
	@JsonProperty("user_id")
	private String userId;
	@JsonProperty("create_at")
	private Long createAt;
	@JsonProperty("post_Num")
	private Integer postNum;
	private String url;
	
	public Channle() {
		this.channleId = "";
    	this.channleName = "";
    	this.description = "";
    	this.createAt = 0L;
    	this.url = "";
    	this.userId = "";
	}
	
	public Long getCreateAt() {
		return this.createAt;
	}
	
	public void setCreateAt(Long createAt) {
		this.createAt = createAt;
	}
    
    public String getDescription() {
		return this.description;
	}
	
	public void setDescription(String description) {
		this.description = description; 
	}
	
    public String getChannleName(){
    	return this.channleName;
    }
    
    public void setChannleName(String channleName) {
    	this.channleName = channleName;
    }
    
    public void setPostNum(Integer postNum) {
    	this.postNum = postNum;
    }
    
    public Integer getPostNum() {
    	return this.postNum;
    }
            
    public void setChannleId(String channleId) {
         this.channleId = channleId;
     }
    
     public String getChannleId() {
         return this.channleId;
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