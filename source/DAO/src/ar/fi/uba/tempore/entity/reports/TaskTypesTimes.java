package ar.fi.uba.tempore.entity.reports;

import java.io.Serializable;
import java.util.Date;

public class TaskTypesTimes implements Serializable {

	private static final long serialVersionUID = 8476516607407005673L;

	private String taskTypeName;
	private Date date;
	private Long hourCounted;
	
	public TaskTypesTimes(){}
	
	public TaskTypesTimes(String taskTypeName, Date date, Long hourCounted) {
		super();
		this.taskTypeName = taskTypeName;
		this.date = date;
		this.hourCounted = hourCounted;
	}
	public TaskTypesTimes(String taskTypeName, Long hourCounted) {
		super();
		this.taskTypeName = taskTypeName;
		this.hourCounted = hourCounted;
	}
	public TaskTypesTimes(Date date, Long hourCounted) {
		super();
		this.date = date;
		this.hourCounted = hourCounted;
	}
	public Long getHourCounted() {
		return hourCounted;
	}
	public void setHourCounted(Long hourCounted) {
		this.hourCounted = hourCounted;
	}
	public String getTaskTypeName() {
		return taskTypeName;
	}
	public void setTaskTypeName(String taskTypeName) {
		this.taskTypeName = taskTypeName;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	@Override
	public String toString() {
		return "["+date +","+ taskTypeName +","+ hourCounted+"]";
	}

}
