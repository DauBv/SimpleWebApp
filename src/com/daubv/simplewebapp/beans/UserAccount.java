package com.daubv.simplewebapp.beans;

public class UserAccount {
	public static final String GENDER_MALE = "M";
	public static final String GENDER_FEMALE = "F";

	private String userName;
	private String gender;
	private String password;

	public UserAccount() {

	}

	/**
	 * Method Constructor UserAccount
	 * 
	 * @param userName
	 * @param password
	 */
	public UserAccount(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}

	/**
	 * Method Constructor UserAccount
	 * @param userName
	 * @param password
	 * @param gender
	 */
	public UserAccount(String userName, String password, String gender) {
		this.userName = userName;
		this.password = password;
		this.gender = gender;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
