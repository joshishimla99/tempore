package fi.uba.tempore.poc.entities;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name="USER")
@PrimaryKeyJoinColumn(name="personId")
public class User extends Person {

	private static final long serialVersionUID = -6663691212226586135L;
	
	private List<TaskUser> taskUserList = new ArrayList<TaskUser>();
	private List<UserProject> taskProjectList = new ArrayList<UserProject>();
	private List<Role> roleList = new ArrayList<Role>();
	
	@OneToMany(
			targetEntity=TaskUser.class, 
			mappedBy="user"
	)
	@LazyCollection(LazyCollectionOption.TRUE)
	public List<TaskUser> getTaskUserList() {
		return taskUserList;
	}
	public void setTaskUserList(List<TaskUser> taskUserList) {
		this.taskUserList = taskUserList;
	}
	
	@OneToMany(
			targetEntity=UserProject.class, 
			mappedBy="user"
	)
	@LazyCollection(LazyCollectionOption.TRUE)
	public List<UserProject> getTaskProjectList() {
		return taskProjectList;
	}
	public void setTaskProjectList(List<UserProject> taskProjectList) {
		this.taskProjectList = taskProjectList;
	}
	
	@ManyToMany(
			targetEntity=Role.class
	)
	@JoinTable(
			name="ROLEUSER",
			joinColumns=@JoinColumn(name="userId"), 
			inverseJoinColumns=@JoinColumn(name="roleId")
	)
	@LazyCollection(LazyCollectionOption.TRUE)
	public List<Role> getRoleList() {
		return roleList;
	}
	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}
}

