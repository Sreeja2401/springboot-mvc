package com.epam.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class AdminAndUser {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	String userType;
	String userName;
	String password;
	public AdminAndUser()
	{
		
	}
	public AdminAndUser(String userType, String userName, String password) {
		super();
		this.userType = userType;
		this.userName = userName;
		this.password = password;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "AdminAndUser [id=" + id + ", userType=" + userType + ", userName=" + userName + ", password=" + password
				+ "]";
	}
	
}
