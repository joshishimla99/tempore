package ar.fi.uba.tempore.entity.reports;

import java.io.Serializable;

public class TasksTimes implements Serializable {

	private static final long serialVersionUID = 9201442143606465959L;
	
	private Integer taskId;
	private String taskName;
	private Long hourCounted;
	

	public TasksTimes(Integer taskId, String taskName, Long hourCounted) {
		this.setTaskId(taskId);
		this.taskName = taskName;
		this.hourCounted = hourCounted;
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

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}
}
