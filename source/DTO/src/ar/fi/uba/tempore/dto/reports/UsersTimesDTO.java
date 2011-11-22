package ar.fi.uba.tempore.dto.reports;

import java.io.Serializable;

public class UsersTimesDTO implements Serializable {

	private static final long serialVersionUID = -5765500120111822154L;

	private String userName;
	private String userLastName;
	private String user;
	private Long hourCounted;
	
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
		return hourCounted==null?0:hourCounted;
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
