package ar.fi.uba.tempore.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
	private ClientDTO client;
	private Integer isOwner;

	//private List<Task> taskList = new ArrayList<Task>();
	//private List<Client> clientList = new ArrayList<Client>();
	private List<UserProjectDTO> userProjectList = new ArrayList<UserProjectDTO>();

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
*/
	public List<UserProjectDTO> getUserProjectList() {
		return userProjectList;
	}

	public void setUserProjectList(List<UserProjectDTO> userProjectList) {
		this.userProjectList = userProjectList;
	}

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

	public void setIsOwner(Integer isOwner) {
		this.isOwner = isOwner;
	}

	public Integer getIsOwner() {
		return this.isOwner;
	}

	public ClientDTO getClient() {
		return client;
	}

	public void setClient(ClientDTO client) {
		this.client = client;
	}

}
