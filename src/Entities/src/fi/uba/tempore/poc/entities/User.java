package fi.uba.tempore.poc.entities;


import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="USER")
@PrimaryKeyJoinColumn(name="personId")
public class User extends Person {

	private static final long serialVersionUID = -6663691212226586135L;
	
}
