package ar.fi.uba.tempore.hibernate.dao;


import java.util.List;

import junit.framework.Assert;

import org.hibernate.ObjectNotFoundException;
import org.junit.Test;

import ar.fi.uba.tempore.hibernate.TestDAO;
import fi.uba.tempore.poc.entities.Contact;
import fi.uba.tempore.poc.entities.ContactType;

public class TestContactTypeDAO extends TestDAO{

	private ContactTypeDAO ctDAO = new ContactTypeDAO();
	
	@Test
	public void testFindId() {
		ContactType actual = null;
		try {	
			actual = ctDAO.findById(1);
			Assert.assertEquals("No se encontro al tipo de contacto", "PM"  , actual.getName());

			List<Contact> contacts = actual.getContactList();
			Assert.assertEquals("El tipo de contacto no es correcto", 2, contacts.size());			
			
		} catch (ObjectNotFoundException e){
			Assert.assertTrue("No se encontro la entidad", false);
		}
	}
	
	@Test
	public void testFindAll (){
		List<ContactType> findAll = ctDAO.findAll();
		Assert.assertEquals("La cantidad de contactos es incorrecta", 2, findAll.size());
	}
	
	@Test
	public void testMakePersistence (){
		ContactType newEntity = getDemoContactType();						

		newEntity = ctDAO.makePersistent(newEntity);		
		
		this.validResult("CONTACTTYPE", "ContactType_New.xml");
	}
	
	@Test
	public void testUpdate(){
		ContactType expected = ctDAO.findById(1);
		expected.setName("JTest");
		expected.setDescription("adsfasd asdf asdfa");
		
		ctDAO.makePersistent(expected);
		
		this.validResult("CONTACTTYPE", "ContactType_Update.xml");		
	}
	
	private ContactType getDemoContactType(){
		ContactType ct = new ContactType();
		ct.setName("JTest");
		ct.setDescription("adsfasd asdf asdfa");
		return ct;
	}
	
	@Test
	public void testDelete (){
		ContactType entity = new ContactType();
		entity.setName("PM");
				
		
		List<ContactType> findByExample = ctDAO.findByExample(entity);
		for (ContactType ct : findByExample){			
			
			//Elimino la referencia de todos los contactos que tengan ese tipo
			List<Contact> contactList = ct.getContactList();
			for (Contact c : contactList){
				c.setContactType(null);
				new ContactDAO().makePersistent(c);
			}
			ctDAO.delete(ct);
		}
		this.validResult("CONTACTTYPE", "ContactType_Delete.xml");
	}

}
