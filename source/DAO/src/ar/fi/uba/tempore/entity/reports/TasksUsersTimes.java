package ar.fi.uba.tempore.entity.reports;

import java.io.Serializable;

public class TasksUsersTimes implements Serializable {

	private static final long serialVersionUID = 8476516607407005673L;

	
	private String taskName;
	private String userName;
	private String userLastName;
	private Long hourCounted;
	
	public TasksUsersTimes(String taskName, String userName,
			String userLastName, Long hourCounted) {
		this.taskName = taskName;
		this.userName = userName;
		this.userLastName = userLastName;
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
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
}
