package fi.uba.tempore.poc.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="TASKUSER")
public class TaskUser implements Serializable{

	private static final long serialVersionUID = -3491623829513634518L;
	private Integer id;
	private User user;
	private Task task;
	private Integer hourCount;
	private Date dateHour;
	
	@Id
	@GeneratedValue
	@Column(name="id")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@ManyToOne
	@JoinColumn(name="userId")
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	@ManyToOne
	@JoinColumn(name="taskId")
	public Task getTask() {
		return task;
	}
	public void setTask(Task task) {
		this.task = task;
	}
	
	@Column(name="hourCount")
	public Integer getHourCount() {
		return hourCount;
	}
	public void setHourCount(Integer hourCount) {
		this.hourCount = hourCount;
	}
	
	@Column(name="dateHour")
	public Date getDateHour() {
		return dateHour;
	}
	public void setDateHour(Date dateHour) {
		this.dateHour = dateHour;
	}	
}
