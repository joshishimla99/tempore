package ar.fi.uba.tempore.hibernate.dao;

import java.util.List;

import junit.framework.Assert;

import org.hibernate.ObjectNotFoundException;
import org.junit.Test;

import ar.fi.uba.tempore.hibernate.TestDAO;
import fi.uba.tempore.poc.entities.Client;
import fi.uba.tempore.poc.entities.Contact;
import fi.uba.tempore.poc.entities.Project;
import fi.uba.tempore.poc.entities.Task;

public class TestClientDAO extends TestDAO{
	private ClientDAO cDAO = new ClientDAO();	
	
	
	@Test
	public void testFindById() {
		Client actual = null;
		try {	
			actual = cDAO.findById(1);
			Assert.assertEquals("No se encontro al cliente", "TCS Tata Consulting Services"  , actual.getName());

			List<Project> actualProjectList = actual.getProjectList();
			Assert.assertEquals("La cantidad de proyectos del clientes no es correcta", 2, actualProjectList.size());
			
			List<Contact> actualContactList = actual.getContactList();
			Assert.assertEquals("La cantidad de contactos del clientes no es correcta", 2, actualContactList.size());
			
		} catch (ObjectNotFoundException e){
			Assert.assertTrue("No se encontro la entidad", false);
		}
	}
	
	@Test
	public void testFindAll (){
		List<Client> findAll = cDAO.findAll();
		Assert.assertEquals("La cantidad del metodo FIND-ALL es incorrecta", 2, findAll.size());
	}

	@Test
	public void testMakePersistent() {
		Client clientExpected = getDemoClient(); 
		
		Client newClient = cDAO.makePersistent(clientExpected);
		Assert.assertNotNull("No se ha podido crear la entidad", newClient);
		
		
		List<Client> allClients = cDAO.findAll();
		Assert.assertEquals("La cantidad de proyectos de la entidad no es correcta", 3, allClients.size());

		try {
			clientExpected.setId(newClient.getId());
			Client clientFound = cDAO.findById(clientExpected.getId());
			Assert.assertEquals("No se crea la entidad correctamente", clientExpected, clientFound);
		} catch (ObjectNotFoundException e){
			Assert.assertTrue("No se encontro la entidad creada", false);
		}
		
	}
	
	@Test
	public void testUpdate(){
		Integer id = 1;
		String dataExpected = "updated name";
		
		Client expected = cDAO.findById(id);
		expected.setName(dataExpected);
		
		cDAO.makePersistent(expected);
		
		Client actual = cDAO.findById(id);
		Assert.assertEquals("Ocurrio un error al actualizar", dataExpected, actual.getName());
	}
	
	/**
	 * Devuelve una entidad para guardar y luego ser comparada con lo guardado
	 * @return Entidad demo
	 */
	private Client getDemoClient (){
		Client c = new Client();
		c.setName("Nombre del cliente");
		c.setAddress("Direccion del cliente");
		c.setCountry("Pais del cliente");
		c.setFiscalNumber("Fiscal Number del cliente");
		c.setPhone("(+54-11) 4269-4564");
		c.setZip("zip del cliente");
		c.setState("Estado del cliente");
		return c;
	}

/*	@Test
	public void testDelete() {
		Client actual = new Client();
		actual.setName("TCS Tata Consulting Services");
		
		List<Client> list = cDAO.findByExample(actual);		
		Assert.assertEquals("No se encontro el CLIENTE buscado para borrar", list.size(), 1);
		
		for (Client cl : list){						
			cDAO.delete(cl);
			
			try {
				cDAO.findById(cl.getId());
				Assert.assertTrue("No se ha eliminado la entidad deseada", false);
			} catch (ObjectNotFoundException e){
				//No se encuentra la entidad
				Assert.assertTrue(true);
			}
		}		
	}
*/
}
