package fi.uba.tempore.poc.entities;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name="CONTACT")
@PrimaryKeyJoinColumn(name="personId")
public class Contact extends Person {

	private static final long serialVersionUID = -4574263897746894680L;

	private String state;
	private ContactType contactType;
	private List<Client> clientList = new ArrayList<Client>();
	
	@Column (name="state")
	public String getState(){
		return state;
	}
	public void setState(String state){
		this.state = state;
	}
	
	@ManyToOne()
	@JoinColumn(name="contactTypeId")
	public ContactType getContactType() {
		return contactType;
	}
	public void setContactType(ContactType contactType) {
		this.contactType = contactType;
	}

	@ManyToMany (
			targetEntity=Client.class,
			mappedBy="contactList"
			)
	@LazyCollection(LazyCollectionOption.TRUE)
	public List<Client> getClientList() {
		return clientList;
	}
	public void setClientList(List<Client> clientList) {
		this.clientList = clientList;
	}	
}
