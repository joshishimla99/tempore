package fi.uba.tempore.poc.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name="Project")
public class Project implements Serializable {

	private static final long serialVersionUID = -725210458722870857L;
	private Integer id;
	private String name;
	private ProjectState projectState;
	
	private List<Task> taskList = new ArrayList<Task>();
	
	@Id
	@GeneratedValue
	@Column(name="id")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name="name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@ManyToOne
	@JoinColumn(name="projectStateId")
	public ProjectState getProjectState() {
		return projectState;
	}
	public void setProjectState(ProjectState projectState) {
		this.projectState = projectState;
	}
	
	@OneToMany(mappedBy="project")
	@LazyCollection(LazyCollectionOption.TRUE)
	@OrderBy("name")
	public List<Task> getTaskList() {
		return taskList;
	}
	public void setTaskList(List<Task> taskList) {
		this.taskList = taskList;
	}
}
