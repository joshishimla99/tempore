package ar.fi.uba.tempore.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

@Entity
@Table(name = "TASK")
public class Task implements Serializable {

	private static final long serialVersionUID = 7342154066758593998L;
	
	private Integer id;
	private String name;
	private String description;
	
	private TaskType taskType;
	private Project project;
	private List<TaskUser> taskUserList;
	
	
	@Id
	@GeneratedValue
	@Column(name="id")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "description")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@ManyToOne
	@JoinColumn(name="taskTypeId")
	public TaskType getTaskType() {
		return taskType;
	}
	public void setTaskType(TaskType taskType) {
		this.taskType = taskType;
	}

	@ManyToOne
	@LazyToOne(LazyToOneOption.PROXY)	
	@JoinColumn(name="projectId")
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	
	@OneToMany(
			targetEntity=TaskUser.class, 
			mappedBy="task"
	)
	@LazyCollection(LazyCollectionOption.TRUE)
	public List<TaskUser> getTaskUserList() {
		return taskUserList;
	}
	public void setTaskUserList(List<TaskUser> taskUserList) {
		this.taskUserList = taskUserList;
	}
}
