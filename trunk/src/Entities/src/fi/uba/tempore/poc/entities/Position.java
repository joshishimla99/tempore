package fi.uba.tempore.poc.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name="POSITION")
public class Position implements Serializable {

	private static final long serialVersionUID = -9016795581745868699L;
	private Integer id;
	private String name;
	private String description;
	
	private List<UserProject> userProjectList = new ArrayList();
	
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
	
	@OneToMany(
			targetEntity=UserProject.class, 
			mappedBy="position"
	)
	@LazyCollection(LazyCollectionOption.TRUE)
	public List<UserProject> getUserProjectList() {
		return userProjectList;
	}
	public void setUserProjectList(List<UserProject> userProjectList) {
		this.userProjectList = userProjectList;
	}
}
