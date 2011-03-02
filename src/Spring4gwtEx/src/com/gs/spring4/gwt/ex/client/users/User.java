package com.gs.spring4.gwt.ex.client.users;

import java.io.Serializable;

/*
 * Esta clase representa al usuario genérico. Esta clase debera ser utilizada por el resto de los usuarios 
 */

public class User extends Person implements Serializable {
	private static final long serialVersionUID = 1L;
	private String userName;
	private String password;
		
	public User(String username, String password){
		super();
		this.userName = username;
		this.password = password;
	}
	
	public User(){
		super();
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


}
