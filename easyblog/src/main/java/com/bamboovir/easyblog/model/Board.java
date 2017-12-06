package com.bamboovir.easyblog.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;


public class Board implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7487436909683304795L;
	
	@JsonProperty("board_id")
	private String boardId;
	@JsonProperty("user_id")
	private String userId;
	@JsonProperty("board_name")
	private String boardName;
	@JsonProperty("created_at")
	private Long createdAt;
	@JsonProperty("cover_pic")
	private String coverPic;
	@JsonProperty("is_private")
	private Boolean isPrivate;
	@JsonProperty("post_num")
	private Integer postNum;
	
	private String description;
	private String url;
	
	public Board() {
		this.boardId = "";
		this.userId = "";
		this.boardName = "";
		this.createdAt = 0L;
		this.description = "";
		this.coverPic = "";
		this.isPrivate = false;
		this.postNum = 0;
		this.url = "";
	}
	
    public void setUrl(String url) {
        this.url = url;
    }
    public String getUrl() {
        return url;
    }
	
	public void setBoardId(String boardId) {
		this.boardId = boardId; 
	}
	
	public String getBoardId() {
		return this.boardId;
	}
	
	public void setBoardName(String boardName) {
		this.boardName = boardName; 
	}
	
	public String getBoardName() {
		return this.boardName; 
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public void setDescription(String description) {
		this.description = description; 
	}
	
	public Long getCreatedAt() {
		return this.createdAt;
	}
	
	public void setCreatedAt(Long createdAt) {
		this.createdAt = createdAt; 
	}
	
	public String getCoverPic() {
		return this.coverPic;
	}
	
	public void setCoverPic(String coverPic) {
		this.coverPic = coverPic; 
	}
	
	public Boolean getIsPrivate() {
		return this.isPrivate;
	}
	
	public void setIsPrivate(Boolean isPrivate ) {
		this.isPrivate = isPrivate ; 
	}
	
	public String getUserId() {
		return this.userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId; 
	}
	
	public Integer getPostNum() {
		return this.postNum;
	}
	
	public void setPostNum(Integer postNum) {
		this.postNum = postNum;
	}
	
}
