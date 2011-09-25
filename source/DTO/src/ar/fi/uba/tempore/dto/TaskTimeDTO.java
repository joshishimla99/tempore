package ar.fi.uba.tempore.dto;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class TaskTimeDTO implements IsSerializable{
	
	private Integer id;
	private String name;
	private String description;
	private Integer taskId;
	
//	private TaskType taskType;
//	private Project project;
	private List<TaskTimeDTO> taskTimeList;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public void setTaskTimeList(List<TaskTimeDTO> taskTimeList) {
		this.taskTimeList = taskTimeList;
	}

	public List<TaskTimeDTO> getTaskTimeList() {
		return taskTimeList;
	}

	public void setTaskId(Integer idParent) {
		this.taskId = idParent;
	}

	public Integer getTaskId() {
		return taskId;
	}
	
	
}
