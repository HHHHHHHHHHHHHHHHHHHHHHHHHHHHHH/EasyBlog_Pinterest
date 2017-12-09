package com.bamboovir.easyblog.model;

import java.io.Serializable;

import org.apache.ibatis.annotations.Update;
import org.omg.CORBA.PUBLIC_MEMBER;

public class Behavior implements Serializable{

	private static final long serialVersionUID = -7391588650222539354L;
	
	private String behaviorId;
	private String masterId;
	private String slaveId;
	private String behaviorType;
	private Long behaviorTime;
	
	public Behavior() {
		this.behaviorId = "";
		this.masterId = "";
		this.slaveId = "";
		this.behaviorType = "";
		this.behaviorTime = 0L;
	}
	
	public void setBehaviorId(String behaviorId) {
		this.behaviorId = behaviorId;
	}
	
	public String getBehaviorId() {
		return this.behaviorId;
	}
	
	public void setMasterId(String masterId) {
		this.masterId = masterId;
	}
	
	public String getMasterId() {
		return this.masterId;
	}
	
	public void setSlaveId(String slaveId) {
		this.slaveId = slaveId;
	}
	
	public String getSlaveId() {
		return this.slaveId;
	}
	
	public void setBehaviorType(String behaviorType) {
		this.behaviorType = behaviorType;
	}
	
	public String getBehaviorType() {
		return this.behaviorType;
	}
	
	public void setBehaviorTime(Long behaviorTime) {
		this.behaviorTime = behaviorTime;
	}
	
	public Long getBehaviorTime() {
		return this.behaviorTime;
	}
	
}
