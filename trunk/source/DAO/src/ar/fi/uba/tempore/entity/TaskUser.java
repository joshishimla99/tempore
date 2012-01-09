package ar.fi.uba.tempore.entity;

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
	private Long hourCount;
	private Date date;
	private String comment;
	
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
	public Long getHourCount() {
		return hourCount;
	}
	public void setHourCount(Long hourCount) {
		this.hourCount = hourCount;
	}
	
	@Column(name="date")
	public Date getDate() {
		return date;
	}
	public void setDate(Date dateHour) {
		this.date = dateHour;
	}
	
	@Column(name="comment")
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
}
