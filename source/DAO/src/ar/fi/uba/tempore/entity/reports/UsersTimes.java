package ar.fi.uba.tempore.entity.reports;

import java.io.Serializable;

public class UsersTimes implements Serializable {

	private static final long serialVersionUID = 8476516607407005673L;

	private String userName;
	private String userLastName;
	private String user;
	private Long hourCounted;
	
	public UsersTimes(String userName, String userLastName, String user, Long hourCounted) {
		this.userName = userName;
		this.userLastName = userLastName;
		this.user = user;
		this.hourCounted = hourCounted;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserLastName() {
		return userLastName;
	}
	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}
	public Long getHourCounted() {
		return hourCounted;
	}
	public void setHourCounted(Long hourCounted) {
		this.hourCounted = hourCounted;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
}
