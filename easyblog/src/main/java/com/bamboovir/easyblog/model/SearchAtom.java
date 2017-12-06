package com.bamboovir.easyblog.model;
import java.io.Serializable;

public class SearchAtom implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3541745786772620959L;
	
	private String url;
    private String query;
    
    
   public SearchAtom() {
    	this.query = "";
    	this.url = "";
    }
    
   public void setQuery(String query) {
         this.query = query;
     }
    
   public String getQuery() {
         return query;
    }
    
   public void setUrl(String url) {
        this.url = url;
    }
   
   public String getUrl() {
        return this.url;
    }

     
}
