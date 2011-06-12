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
@Table(name="CONTACTTYPE")
public class ContactType implements Serializable {

	private static final long serialVersionUID = 6681492389130627073L;
	private Integer id;
	private String name;
	private String description;
	
	private List<Contact> contactList = new ArrayList<Contact>();
	
	@Id
	@GeneratedValue
	@Column(name="id")
	public Integer getId(){
		return id;
	}
	public void setId(Integer id){
		this.id = id;
	}
	
	 @Column(name="name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name="description")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@OneToMany(
			targetEntity=Contact.class, 
			mappedBy="contactType"
	)
	@LazyCollection(LazyCollectionOption.TRUE)
	public List<Contact> getContactList() {
		return contactList;
	}
	public void setContactList(List<Contact> contactList) {
		this.contactList = contactList;
	}
	
}
