package com.rinkbooking.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="bookings")

public class Booking {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ref_num")
	private int refNum;
	
	@Column(name = "rink_num")
	private int rinkNum;
	
	@Column(name= "group_name")
	private String groupName;
	
	@Column(name = "start_time")
	private String startTime;
	
	@Column(name = "end_time")
	private String endTime;
	
	@Column(name="date")
	private String date;
	
	public Booking() {
		
	}
	
	public Booking(int refNum, int rinkNum, String groupName, String startTime, String endTime, String date) {
		this.refNum = refNum;
		this.rinkNum = rinkNum;
		this.groupName = groupName;
		this.startTime = startTime;
		this.endTime = endTime;
		this.date = date;
	}

	public int getRefNum() {
		return refNum;
	}

	public void setRefNum(int refNum) {
		this.refNum = refNum;
	}

	public int getRinkNum() {
		return rinkNum;
	}

	public void setRinkNum(int rinkNum) {
		this.rinkNum = rinkNum;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getStartTime() {
		return this.startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return this.endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getDate() {
		return this.date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
