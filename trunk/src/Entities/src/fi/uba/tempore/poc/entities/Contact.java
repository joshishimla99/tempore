package fi.uba.tempore.poc.entities;


import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="CONTACT")
@PrimaryKeyJoinColumn(name="personId")
public class Contact extends Person {

	private static final long serialVersionUID = -4574263897746894680L;

	private ContactType contactType;
	
	@ManyToOne()
	@JoinColumn(name="contactTypeId")
	public ContactType getContactType() {
		return contactType;
	}
	public void setContactType(ContactType contactType) {
		this.contactType = contactType;
	}	
}
