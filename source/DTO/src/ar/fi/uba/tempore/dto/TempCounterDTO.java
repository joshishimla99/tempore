package ar.fi.uba.tempore.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class TempCounterDTO implements IsSerializable {

	private Integer userId;
	private TaskDTO task;
	private Long timeIni;
	private Long timeAcumulated;
	private Integer control;

	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Long getTimeIni() {
		return timeIni;
	}
	public void setTimeIni(Long timeIni) {
		this.timeIni = timeIni;
	}

	public Long getTimeAcumulated() {
		return timeAcumulated;
	}
	public void setTimeAcumulated(Long timeAcumulated) {
		this.timeAcumulated = timeAcumulated;
	}

	public Integer getControl() {
		return control;
	}
	public void setControl(Integer control) {
		this.control = control;
	}

	public TaskDTO getTask() {
		return task;
	}
	public void setTask(TaskDTO task) {
		this.task = task;
	}

}
