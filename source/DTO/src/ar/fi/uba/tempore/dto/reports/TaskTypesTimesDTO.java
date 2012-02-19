package ar.fi.uba.tempore.dto.reports;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class TaskTypesTimesDTO implements IsSerializable {

	private String taskTypeName;
	private Date date;
	private Long hourCounted;
	
	public TaskTypesTimesDTO(){}
	
	public TaskTypesTimesDTO(String taskTypeName, Date date, Long hourCounted) {
		this.taskTypeName = taskTypeName;
		this.date = date;
		this.hourCounted = hourCounted;
	}
	public TaskTypesTimesDTO(String taskTypeName, Long hourCounted) {
		this.taskTypeName = taskTypeName;
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
