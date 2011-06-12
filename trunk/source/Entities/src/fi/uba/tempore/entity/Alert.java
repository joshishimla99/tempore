package fi.uba.tempore.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="Alert")
public class Alert implements Serializable {

	private static final long serialVersionUID = -9016795581745868699L;
	private Integer id;
	private String name;
	private String description;
	
	private UserProject userProject;
	
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
	@JoinColumn(name="userProjectId")
	public UserProject getUserProject() {
		return userProject;
	}
	public void setUserProject(UserProject userProject) {
		this.userProject = userProject;
	}
}
