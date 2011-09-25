package ar.fi.uba.tempore.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class TaskUserDTO implements IsSerializable{
	
	private Integer id;
//	private Integer userId;
//	private Integer taskId;
	private Integer hourCount;
	private String comment;
//	private String date;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
//	public Integer getUser() {
//		return userId;
//	}
//	
//	public void setUser(Integer userId) {
//		this.userId = userId;
//	}
//	
//	public Integer getTask() {
//		return taskId;
//	}
//	public void setTask(Integer taskId) {
//		this.taskId = taskId;
//	}
	
	public Integer getHourCount() {
		return hourCount;
	}
	public void setHourCount(Integer hourCount) {
		this.hourCount = hourCount;
	}
	
	
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	
}
