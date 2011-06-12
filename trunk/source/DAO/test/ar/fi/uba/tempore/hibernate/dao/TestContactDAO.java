package ar.fi.uba.tempore.hibernate.dao;

import java.util.List;

import junit.framework.Assert;

import org.hibernate.ObjectNotFoundException;
import org.junit.Test;

import ar.fi.uba.tempore.dao.ClientDAO;
import ar.fi.uba.tempore.dao.ContactDAO;
import ar.fi.uba.tempore.dao.ContactTypeDAO;
import ar.fi.uba.tempore.entity.Client;
import ar.fi.uba.tempore.entity.Contact;
import ar.fi.uba.tempore.entity.ContactType;
import ar.fi.uba.tempore.hibernate.TestDAO;

public class TestContactDAO extends TestDAO{
	private ContactDAO cDAO = new ContactDAO();
	
	@Test
	public void testFindId() {
		Contact actual = null;
		try {	
			actual = cDAO.findById(1);
			Assert.assertEquals("No se encontro al contacto", "Nicolás"  , actual.getName());

			ContactType actualContactType = actual.getContactType();
			Assert.assertEquals("El tipo de contacto no es correcto","PM", actualContactType.getName());
			
			List<Client> actualClientList = actual.getClientList();
			Assert.assertEquals("El contacto pertenece a una cantidad de clientes erronea", 1, actualClientList.size());
			Assert.assertEquals("El contacto no pertenece a ese cliente", "TCS Tata Consulting Services", actualClientList.get(0).getName());
			
		} catch (ObjectNotFoundException e){
			Assert.assertTrue("No se encontro la entidad", false);
		}
	}
	
	@Test
	public void testFindAll() {
		List<Contact> contacts = cDAO.findAll();
		Assert.assertEquals("La cantidad de contactos es incorrecta", 3, contacts.size());
	}

	@Test
	public void testMakePersistence (){
		Contact newEntity = getDemoContact();						
		newEntity = cDAO.makePersistent(newEntity);		
		
		this.validResult("CONTACT", "Contact_New.xml");
	}
	
	@Test
	public void testUpdate(){
		Contact expected = cDAO.findById(1);
		expected.setName("Nombre Demo");
		expected.setLastName("Apellido Demo");
		expected.setCountry("Argentina");
		expected.setState("A");
		expected.setAddress("Dirección Demo");
		expected.setPhone("54 11 4266-6223");
		expected.setUserName("admin");
		expected.setPassword("admin");
		expected.setEmail("mail@mail.com");
		
		expected.setContactType(new ContactTypeDAO().findById(2));

		
		cDAO.makePersistent(expected);
		
		this.validResult("CONTACT", "Contact_Update.xml");
	}
	

	@Test
	public void testDelete() {
		Contact c = new Contact();
		c.setName("Nicolás");
		
		List<Contact> contacts = cDAO.findByExample(c);
		
		for (Contact cc : contacts){			
			//Elimino la referencia de clientes
			List<Client> clientList = cc.getClientList();
			for (Client cl : clientList){
				List<Contact> contactList = cl.getContactList();
				contactList.remove(cc);
				new ClientDAO().makePersistent(cl);
			}
			
			cDAO.delete(cc);			
		}
		
		this.validResult("CONTACT", "Contact_Delete.xml");
		this.validResult("USER", "Contact_Delete.xml");
		this.validResult("PERSON", "Contact_Delete.xml");
	}

	private Contact getDemoContact (){
		Contact c = new Contact();
		c.setName("Nombre Demo");
		c.setLastName("Apellido Demo");
		c.setCountry("Argentina");
		c.setState("A");
		c.setAddress("Dirección Demo");
		c.setPhone("54 11 4266-6223");
		c.setUserName("admin");
		c.setPassword("admin");
		c.setEmail("mail@mail.com");
		
		
		c.setContactType(new ContactTypeDAO().findById(1));
		return c;
	}
}
