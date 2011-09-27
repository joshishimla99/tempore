package ar.fi.uba.tempore.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name="USERPROJECT")
public class UserProject implements Serializable {

	private static final long serialVersionUID = -5123217767892856110L;
	private Integer id;
	private Integer owner;
	private User user;
	private Project project;
	private List<Alert> alertList = new ArrayList<Alert>();
	private List<Role> roleList = new ArrayList<Role>();
	
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
	@JoinColumn(name="projectId")
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	
	
	@OneToMany(
			targetEntity=Alert.class, 
			mappedBy="userProject"
	)
	@LazyCollection(LazyCollectionOption.TRUE)
	public List<Alert> getAlertList() {
		return alertList;
	}
	public void setAlertList(List<Alert> alertList) {
		this.alertList = alertList;
	}
		
	@ManyToMany(
			targetEntity=Role.class
	)
	@JoinTable(
			name="ROLEUSERPROJECT",
			joinColumns=@JoinColumn(name="userProjectId"), 
			inverseJoinColumns=@JoinColumn(name="roleId")
	)
	@LazyCollection(LazyCollectionOption.TRUE)
	public List<Role> getRoleList() {
		return roleList;
	}
	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}
	
	@Column(name="owner")
	public Integer getOwner() {
		return owner;
	}
	public void setOwner(Integer owner) {
		this.owner = owner;
	}
}
