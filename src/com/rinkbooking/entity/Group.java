package com.rinkbooking.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="groups")
public class Group {

	@Column(name="first_name")
	String firstName;
	
	@Column(name="last_name")
	String lastName;
	
	@Column(name="country")
	String country;
	
	@Column(name="address")
	String address;
	
	@Column(name="state")
	String state;
	
	@Column(name="zip_code")
	String zipcode;
	@Id
	@Column(name="username")
	String userName;
	
	@Column(name="password")
	String password;
	
	@Column(name="credit_card_num")
	String creditCardNum;
	
	@Column(name="sec_code")
	String secCode;
	
	public Group() {
	}
	
	public Group(String firstName, String lastName, String country, String address, String state, String zipcode, String userName, String password, String creditCardNum, String secCode) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.country = country;
		this.address = address;
		this.state = state;
		this.zipcode = zipcode;
		this.userName = userName;
		this.password = password;
		this.creditCardNum = creditCardNum;
		this.secCode = secCode;
	}

	public String getUsername() {
		return this.userName;
	}

	public String getPassword() {
		return this.password;
	}
	
}

