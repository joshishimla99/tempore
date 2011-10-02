package ar.fi.uba.tempore.dto;

import java.io.Serializable;

public class UserProjectDTO implements Serializable {

	private static final long serialVersionUID = -5123217767892856110L;
	private Integer id;	
	private Integer owner;

	private UserDTO user;
	private ProjectDTO project;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOwner() {
		return owner;
	}
	public void setOwner(Integer owner) {
		this.owner = owner;
	}
	public UserDTO getUser() {
		return user;
	}
	public void setUser(UserDTO user) {
		this.user = user;
	}
	public ProjectDTO getProject() {
		return project;
	}
	public void setProject(ProjectDTO project) {
		this.project = project;
	}
	
//	public List<Alert> getAlertList() {
//		return alertList;
//	}
//	public void setAlertList(List<Alert> alertList) {
//		this.alertList = alertList;
//	}
//		
//	public List<Role> getRoleList() {
//		return roleList;
//	}
//	public void setRoleList(List<Role> roleList) {
//		this.roleList = roleList;
//	}
	
}
