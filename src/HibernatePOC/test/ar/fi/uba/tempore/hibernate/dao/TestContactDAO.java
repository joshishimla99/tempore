package ar.fi.uba.tempore.hibernate.dao;

import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;

import ar.fi.uba.tempore.hibernate.TestDAO;

import fi.uba.tempore.poc.entities.Client;
import fi.uba.tempore.poc.entities.Contact;
import fi.uba.tempore.poc.entities.ContactType;

public class TestContactDAO extends TestDAO{
	private ContactDAO cDAO = new ContactDAO();
	
	@Test
	public void testFindAll() {
		List<Contact> contacts = cDAO.findAll();
		for (Contact c : contacts){
			log.info("Contacto: " + c.getName() + ", "+ c.getLastName() +", "+ c.getId());
			ContactType ct = c.getContactType();
			log.info("\t Tipo de contacto: " + ct.getName());
			
			List<Client> clients = c.getClientList();
			for (Client cl : clients){
				log.info("\t\tCliente del contacto: " + cl.getName());
			}
		}
	}

	@Test
	public void testMakePersistent() {
		Contact c = new Contact();
		c.setName("JTestName");
		c.setLastName("JTestLastName");
		c.setAddress("JTestAddress");
		c.setPhone("JTest1213");
		
		ContactTypeDAO contactTypeDAO = new ContactTypeDAO();
		ContactType contactType = contactTypeDAO.findById(1);
		c.setContactType(contactType );
		
		c = cDAO.makePersistent(c);
		log.info("Nuevo Contacto: " + c.getId());
	}

	@Test
	public void testDelete() {
		Contact c = new Contact();
		c.setName("JTestName");
		
		
		List<Contact> contacts = cDAO.findByExample(c);
		for (Contact cc : contacts){
			log.info("Eliminar Contacto de ID = " + cc.getId());
			cDAO.delete(cc);
		}
	}

}
