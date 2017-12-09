package com.bamboovir.easyblog.model;

import java.io.Serializable;

import org.apache.ibatis.annotations.Update;

public class Image implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1054391091586097762L;
	
	private String imageId;
	private String imageType;
	private String userPostBoardId;
	private String url;
	
	public void setImageId(String imageId) {
		this.imageId = imageId;
	}
	
	public String getImageId() {
		return this.imageId;
	}
	
	public void setImageType(String imageType) {
		this.imageType = imageType;
	}
	
	public String getImageType() {
		return this.imageType;
	}
	
	public String getUserPostBoardId() {
		return this.userPostBoardId;
	}
	
	public void setUserPostBoardId(String UserPostBoardId) {
		this.userPostBoardId = UserPostBoardId;
	}
	
	public String getUrl() {
		return this.url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
}
