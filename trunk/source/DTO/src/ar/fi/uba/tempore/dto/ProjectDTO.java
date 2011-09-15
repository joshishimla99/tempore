package ar.fi.uba.tempore.dto;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

/*
 * Este DTO maneja la lista de proyectos. Me devolvera idProyecto y nombreProyecto
 */
public class ProjectDTO implements IsSerializable {

	private Integer id;
	private String name;
	private String description;
	private Date initDate;
	private Date endDate;
	private Float budget;
	private ProjectStateDTO projectState;

	//private List<Task> taskList = new ArrayList<Task>();
	//private List<Client> clientList = new ArrayList<Client>();
	//private List<UserProject> userProjectList = new ArrayList<UserProject>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ProjectStateDTO getProjectState() {
		return projectState;
	}

	public void setProjectState(ProjectStateDTO projectState) {
		this.projectState = projectState;
	}

/*	public List<Task> getTaskList() {
		return taskList;
	}

	public void setTaskList(List<Task> taskList) {
		this.taskList = taskList;
	}

	public List<Client> getClientList() {
		return clientList;
	}

	public void setClientList(List<Client> clientList) {
		this.clientList = clientList;
	}

	public void addClient(Client client) {
		this.getClientList().add(client);
	}

	public List<UserProject> getUserProjectList() {
		return userProjectList;
	}

	public void setUserProjectList(List<UserProject> userProjectList) {
		this.userProjectList = userProjectList;
	}
*/
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getInitDate() {
		return initDate;
	}

	public void setInitDate(Date initDate) {
		this.initDate = initDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setBudget(Float budget) {
		this.budget = budget;
	}

	public Float getBudget() {
		return budget;
	}

}
