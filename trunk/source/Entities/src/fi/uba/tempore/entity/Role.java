package fi.uba.tempore.entity;

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
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name="ROLE")
public class Role implements Serializable {

	private static final long serialVersionUID = -5295656074327028578L;
	private Integer id;
	private String name;

	private List<UserProject> userProjectList = new ArrayList<UserProject>();
	private List<Privilege> privilegeList = new ArrayList<Privilege>();
	
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

	@ManyToMany(
			targetEntity=UserProject.class,
			mappedBy="roleList"
	)
	@LazyCollection(LazyCollectionOption.TRUE)
	public List<UserProject> getUserProjectList() {
		return userProjectList;
	}
	public void setUserProjectList(List<UserProject> userProjectList) {
		this.userProjectList = userProjectList;
	}
	
	@ManyToMany(
			targetEntity=Privilege.class
	)
	@JoinTable(
			name="PRIVILEGEROLE",
			joinColumns=@JoinColumn(name="roleId"), 
			inverseJoinColumns=@JoinColumn(name="privilegeId")
	)
	@LazyCollection(LazyCollectionOption.TRUE)
	public List<Privilege> getPrivilegeList() {
		return privilegeList;
	}
	public void setPrivilegeList(List<Privilege> privilegeList) {
		this.privilegeList = privilegeList;
	}		
}
