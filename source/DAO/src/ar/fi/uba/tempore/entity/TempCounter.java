package ar.fi.uba.tempore.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TEMPCOUNTER")
public class TempCounter implements Serializable {

	private static final long serialVersionUID = -886744575805615342L;

	private Integer userId;
	private Task task;
	private Long timeIni;
	private Long timeAcumulated;
	private Integer control;

	@Id
	@Column(name="userId")
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	
	@ManyToOne
	@JoinColumn(name="taskId")
	public Task getTask() {
		return task;
	}
	public void setTask(Task task) {
		this.task = task;
	}

	@Column(name="timeIni")
	public Long getTimeIni() {
		return timeIni;
	}
	public void setTimeIni(Long timeIni) {
		this.timeIni = timeIni;
	}
	
	@Column(name="timeAcumulated")
	public Long getTimeAcumulated() {
		return timeAcumulated;
	}
	public void setTimeAcumulated(Long timeAcumulated) {
		this.timeAcumulated = timeAcumulated;
	}
	
	@Column(name="control")
	public Integer getControl() {
		return control;
	}
	public void setControl(Integer control) {
		this.control = control;
	}
	
}
