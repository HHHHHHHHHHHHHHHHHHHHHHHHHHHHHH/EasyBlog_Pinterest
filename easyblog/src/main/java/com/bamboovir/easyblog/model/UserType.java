package com.bamboovir.easyblog.model;

public enum UserType {
	
	UNLOGINUSER("unloginuser"),
	UNAUTHUSER("unauthuser"),
	NORMALUSER("normaluser"),
	SUPERUSER("superuser");
	
	private UserType(String type) {
		
	}
}
