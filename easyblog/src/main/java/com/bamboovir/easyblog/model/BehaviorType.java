package com.bamboovir.easyblog.model;
public enum BehaviorType {
	
	// 未来可能增加 订阅 Event 的 概念
	// superUser 的 概念 -> 所有的创建用户事件由 superUser 级别权限 控制
	// 增加 Follow 库  
	// 增加 Channel 库 （多用户概念） 
	LOGIN("login"),
	LOGOUT("logout"),
	SEARCHQUERY("search"),
	FOLLOWUSER("follow"),
	UNFOLLOWUSER("unfollow"),
	LIKEPost("likePost"),
	UNLIKEPost("unlikePost"),
	LIKEBOARD("likeBoard"),
	UNLIKEBOARD("unlikeBoard"),
	CREATEPOST("createPost"),
	CREATEBOARD("createBoard"),
	CREATEUSER("createUser"),
	UPDATEPOST("updatePost"),
	UPDATEBOARD("updateBoard"),
	UPDATEUSER("updateUser"),
	DELETEPOST("deletePost"),
	DELETEUSER("deleteUser"),
	DELETEBOARD("deleteBoard"),
	GETPOST("getPost"),
	GETUSER("getUser"),
	GETBOARD("getBoard"),
	READPOST("readPost"),
	READUSER("readUser"),
	READBOARD("readBoard")
	;
	
	private String type;
	
	private BehaviorType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return this.type;
	}
}