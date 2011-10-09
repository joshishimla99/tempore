package ar.fi.uba.tempore.dto;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class TaskUserDTO implements IsSerializable{
	
	private Integer id;
//	private Integer userId;
//	private Integer taskId;
	private Integer hourCount;
	private String comment;
	private Date date;
	private TaskDTO task;
	private UserDTO user;
	
	public TaskUserDTO(){
		super();
		task = new TaskDTO();
		user = new UserDTO();
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
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

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getDate() {
		return date;
	}

	public void setTask(TaskDTO task) {
//		this.task.setId(task.getId());
//		this.task.setDescription(task.getDescription());
//		this.task.setName(task.getName());
//		this.task.setProject(task.getProject());
		this.task = task;
		
		
	}

	public TaskDTO getTask() {
		return task;
	}

	public void setUser(UserDTO user) {
//		this.user.setId(user.getId());
		this.user = user;
	}

	public UserDTO getUser() {
		return user;
	}
	
	
}
