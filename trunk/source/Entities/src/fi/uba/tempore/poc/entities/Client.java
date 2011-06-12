package fi.uba.tempore.poc.entities;

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
@Table(name="CLIENT")
public class Client implements Serializable {

	private static final long serialVersionUID = 1638528124445097339L;
	private Integer id;
	private String name;
	private String address;
	private String country;
	private String state;
	private String zip;
	private String fiscalNumber;
	private String phone;
	
	
	
	private List<Project> projectList = new ArrayList<Project>();
	private List<Contact> contactList = new ArrayList<Contact>();
	
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
	
	@ManyToMany(
			targetEntity=Project.class,
			mappedBy="clientList"
			)			
	@LazyCollection(LazyCollectionOption.TRUE)	
	public List<Project> getProjectList() {
		return projectList;
	}
	public void setProjectList(List<Project> projectList) {
		this.projectList = projectList;
	}
	public void addProject(Project project) {
		this.getProjectList().add(project);
	}

	@ManyToMany(
			targetEntity=Contact.class
	)
	@JoinTable(
			name="CLIENTCONTACT",
			joinColumns=@JoinColumn(name="clientId"),
			inverseJoinColumns=@JoinColumn(name="contactId")
			)
	@LazyCollection(LazyCollectionOption.TRUE)
	public List<Contact> getContactList() {
		return contactList;
	}	
	public void setContactList(List<Contact> contactList) {
		this.contactList = contactList;
	}

	@Column(name="address")
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name="country")
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}

	@Column(name="state")
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}

	@Column(name="zip")
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}

	@Column(name="fiscalNumber")
	public String getFiscalNumber() {
		return fiscalNumber;
	}
	public void setFiscalNumber(String fiscalNumber) {
		this.fiscalNumber = fiscalNumber;
	}

	@Column(name="phone")
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
}
