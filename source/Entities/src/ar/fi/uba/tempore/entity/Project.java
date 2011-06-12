package ar.fi.uba.tempore.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name="PROJECT")
public class Project implements Serializable {

	private static final long serialVersionUID = -725210458722870857L;
	private Integer id;
	private String name;
	private String description;
	private Date initDate;
	private Date endDate;
	private Double budget;
	private ProjectState projectState;
	
	private List<Task> taskList = new ArrayList<Task>();
	private List<Client> clientList = new ArrayList<Client>();
	private List<UserProject> userProjectList = new ArrayList<UserProject>();
	
	
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
	
	@OneToMany(
			targetEntity=Task.class, 
			mappedBy="project", 
			cascade=CascadeType.ALL
	)
	@LazyCollection(LazyCollectionOption.TRUE)
	@OrderBy("name")
	public List<Task> getTaskList() {
		return taskList;
	}
	public void setTaskList(List<Task> taskList) {
		this.taskList = taskList;
	}	
	
	@ManyToMany(
			targetEntity=Client.class
	)
	@JoinTable(
			name="PROJECTCLIENT",
			joinColumns=@JoinColumn(name="projectId"), 
			inverseJoinColumns=@JoinColumn(name="clientId")
	)
	@LazyCollection(LazyCollectionOption.TRUE)
	public List<Client> getClientList() {
		return clientList;
	}
	public void setClientList(List<Client> clientList) {
		this.clientList = clientList;
	}
	public void addClient(Client client) {
		this.getClientList().add(client);
	}

	@OneToMany(
			targetEntity=UserProject.class, 
			mappedBy="project"
	)
	@LazyCollection(LazyCollectionOption.TRUE)
	public List<UserProject> getUserProjectList() {
		return userProjectList;
	}
	public void setUserProjectList(List<UserProject> userProjectList) {
		this.userProjectList = userProjectList;
	}
	
	@Column(name="description")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name="initDate")
	public Date getInitDate() {
		return initDate;
	}
	public void setInitDate(Date initDate) {
		this.initDate = initDate;
	}
	
	@Column(name="endDate")
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	@Column(name="budget")
	public void setBudget(Double budget) {
		this.budget = budget;
	}
	public Double getBudget() {
		return budget;
	}
}
