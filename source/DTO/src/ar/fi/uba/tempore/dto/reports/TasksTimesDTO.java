package ar.fi.uba.tempore.dto.reports;

import java.io.Serializable;

public class TasksTimesDTO implements Serializable {

	private static final long serialVersionUID = 6318760921111508786L;

	private String taskName;
	private Long hourCounted;
	
	public Long getHourCounted() {
		return hourCounted==null?0:hourCounted;
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
