package com.gs.spring4.gwt.ex.client.users;

/*
 * Esta clase contiene los datos de una persona fisica 
 */

public class Person {
	
	private String name;
	private String surname;
	private String email;
	private String telephoneNumber;
	private String mobileNumber;
	private String address;
	
	public Person(String name, String surname, String email, String telephoneNumber, String mobileNumber, String address){
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.telephoneNumber = telephoneNumber;
		this.mobileNumber = mobileNumber;
		this.address = address;
	}

	public Person(){
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephoneNumber() {
		return telephoneNumber;
	}

	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	

}
