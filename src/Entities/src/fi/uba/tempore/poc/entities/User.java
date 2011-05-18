package fi.uba.tempore.poc.entities;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
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
	private String state;
	private List<TaskUser> taskUserList = new ArrayList<TaskUser>();
	private List<UserProject> userProjectList = new ArrayList<UserProject>();
	
	
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
	public List<UserProject> getUserProjectList() {
		return userProjectList;
	}
	public void setUserProjectList(List<UserProject> userProjectList) {
		this.userProjectList = userProjectList;
	}

	@Column(name="state")
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
}

