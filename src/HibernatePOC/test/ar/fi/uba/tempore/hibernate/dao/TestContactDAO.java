package ar.fi.uba.tempore.hibernate.dao;

import java.util.List;

import junit.framework.Assert;

import org.hibernate.ObjectNotFoundException;
import org.junit.Test;

import ar.fi.uba.tempore.hibernate.TestDAO;
import fi.uba.tempore.poc.entities.Client;
import fi.uba.tempore.poc.entities.Contact;
import fi.uba.tempore.poc.entities.ContactType;

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
		Assert.assertNotNull("No se ha podido crear la entidad", newEntity);
		
		List<Contact> allEntities = cDAO.findAll();
		Assert.assertEquals("La cantidad de la entidad no es correcta", 4, allEntities.size());

		try {
			Contact expected = getDemoContact();
			expected.setId(newEntity.getId());
			
			Contact actual = cDAO.findById(newEntity.getId());
			Assert.assertEquals(expected.getName(), actual.getName());
			Assert.assertEquals(expected.getPhone(), actual.getPhone());
			Assert.assertEquals(expected.getState(), actual.getState());
		} catch (ObjectNotFoundException e){
			Assert.assertTrue("No se encontro la entidad creada", false);
		}
	}
	
	@Test
	public void testUpdate(){
		Contact expected = cDAO.findById(1);
		expected.setName("Lucas");
		
		cDAO.makePersistent(expected);
		
		Contact actual = cDAO.findById(1);
		Assert.assertEquals("Ocurrio un error al actualizar", expected, actual);
	}
	

/*	@Test
	public void testDelete() {
		Contact c = new Contact();
		c.setName("Nicolás");
		
		List<Contact> contacts = cDAO.findByExample(c);
		Assert.assertEquals("No se encontro el CONTACTOS buscado para borrar", contacts.size(), 1);
		
		for (Contact cc : contacts){			
			Integer id = cc.getId();
			cDAO.delete(cc);
			
//			try {
//				cDAO.findById(id);
//				Assert.assertTrue("No se ha eliminado la entidad deseada", false);
//			} catch (ObjectNotFoundException e){
//				//No se encuentra la entidad
//				Assert.assertTrue(true);
//			}
			
		}
	}
*/
	private Contact getDemoContact (){
		Contact c = new Contact();
		c.setName("Nombre Demo");
		c.setLastName("Apellido Demo");
		c.setCountry("Argentina");
		c.setState("A");
		c.setAddress("Dirección Demo");
		c.setPhone("54 11 4266-6223");
		
		try {	
			ContactTypeDAO ctDAO = new ContactTypeDAO();
			ContactType contactType = ctDAO.findById(1);
			
			c.setContactType(contactType);
		} catch (ObjectNotFoundException e){
			Assert.assertTrue("No se encontro la el tipo de contacto para realizar la demo", false);
		}
		return c;
	}
}
