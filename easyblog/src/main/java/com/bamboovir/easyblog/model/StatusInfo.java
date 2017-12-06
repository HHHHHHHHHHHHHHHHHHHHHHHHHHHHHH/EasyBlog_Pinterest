package com.bamboovir.easyblog.model;

import java.io.Serializable;

public class StatusInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3217841332168408101L;

	Boolean isLogined;
	Boolean isEmailAuth;
	String statusInfo;
	
	public StatusInfo() {
		this.isLogined = false;
		this.isEmailAuth = false;
		this.statusInfo = "";
	}
	
	public void setIsLogined(boolean isLogined) {
		this.isLogined = isLogined;
	}
	
	public Boolean getIsLogined() {
		return this.isLogined;
	}
	
	public void setIsEmailAuth(boolean isEmailAuth) {
		this.isEmailAuth = isEmailAuth;
	}
	
	public Boolean getIsEmailAuth() {
		return this.isEmailAuth;
	}
	public void setStatusInfo(String statusInfo) {
		this.statusInfo = statusInfo;
	}
	
	public String getStatusInfo() {
		return this.statusInfo;
	}
	
}
