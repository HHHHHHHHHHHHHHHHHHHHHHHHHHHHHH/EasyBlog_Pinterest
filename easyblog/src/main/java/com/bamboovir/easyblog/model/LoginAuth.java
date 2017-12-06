package com.bamboovir.easyblog.model;

import java.io.Serializable;

public class LoginAuth implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1399008775804019616L;
	
	private String usernm;
	private String passwd;
	private String email;
	
	public LoginAuth() {
		this.usernm = "";
		this.passwd = "";
		this.email = "";
	}
	
	public void setEmail(String email){
		this.email = email;
	}
	
	public String getEmail(){
		return this.email;
	}
	
	public void setUsernm(String usernm){
		this.usernm = usernm;
	}
	
	public String getUsernm(){
		return this.usernm;
	}
	
	public void setPasswd(String passwd){
		this.passwd = passwd;
	}
	
	public String getPasswd(){
		return this.passwd;
	}
}
