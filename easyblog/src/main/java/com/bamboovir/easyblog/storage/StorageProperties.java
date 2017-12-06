package com.bamboovir.easyblog.storage;

import java.io.Serializable;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("storage")
public class StorageProperties implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -1412832801405505815L;
	/**
     * Folder location for storing files
     */
    private String location = "upload-dir";
    private String picLocation = "upload-pic-dir";
    private String tempLocation = "upload-temp-dir";

    public String getLocation() {
        return location;
    }
    
    public String getPicLocation() {
    	return picLocation;
    }
    
    public String getTempLocation() {
    	return tempLocation;
    }
    
    public void setTempLocation(String tempLocation) {
    	this.tempLocation = tempLocation;
    }
    
    public void setPicLocation(String picLocation) {
    	this.picLocation = picLocation;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}