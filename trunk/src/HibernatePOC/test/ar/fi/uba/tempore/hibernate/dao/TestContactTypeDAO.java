package ar.fi.uba.tempore.hibernate.dao;


import java.util.List;

import junit.framework.Assert;

import org.hibernate.ObjectNotFoundException;
import org.junit.Test;

import ar.fi.uba.tempore.hibernate.TestDAO;

import fi.uba.tempore.poc.entities.Client;
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
		Assert.assertNotNull("No se ha podido crear la entidad", newEntity);
		
		List<ContactType> allEntities = ctDAO.findAll();
		Assert.assertEquals("La cantidad de la entidad no es correcta", 3, allEntities.size());

		try {
			ContactType expected = getDemoContactType();
			expected.setId(newEntity.getId());
			
			ContactType actual = ctDAO.findById(newEntity.getId());
			Assert.assertEquals(expected.getName(), actual.getName());
			Assert.assertEquals(expected.getDescription(), actual.getDescription());
		} catch (ObjectNotFoundException e){
			Assert.assertTrue("No se encontro la entidad creada", false);
		}
	}
	
	@Test
	public void testUpdate(){
		ContactType expected = ctDAO.findById(1);
		expected.setName("PL");
		
		ctDAO.makePersistent(expected);
		
		ContactType actual = ctDAO.findById(1);
		Assert.assertEquals("Ocurrio un error al actualizar", "PL", actual.getName());
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
			
			
			Integer id = ct.getId();
			ctDAO.delete(ct);
			try {
				ctDAO.findById(id);
				Assert.assertTrue("No se ha eliminado la entidad deseada", false);
			} catch (ObjectNotFoundException e){
				//No se encuentra la entidad
				Assert.assertTrue(true);
			}
		}
	}

}
