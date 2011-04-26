package ar.fi.uba.tempore.hibernate.dao;

import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import ar.fi.uba.tempore.hibernate.TestDAO;
import fi.uba.tempore.poc.entities.Client;
import fi.uba.tempore.poc.entities.Project;

public class TestClientDAO extends TestDAO{
	private ClientDAO cDAO = new ClientDAO();	
	
	
	@Test
	public void testFindById() {		
		Client actual = cDAO.findById(1);
		Assert.assertNotNull(actual);
		Assert.assertNotNull(actual.getId());
		Assert.assertEquals("No se encontro al cliente", "TCS Tata Consulting Services"  , actual.getName());
			
		List<Project> actualProjectList = actual.getProjectList();
		Assert.assertEquals("La cantidad de proyectos del clientes no es correcta", 2, actualProjectList.size());
	}

	@Test
	public void testMakePersistent() {
		Client c = new Client();
		c.setName("Nuevo Cliente");
		c = cDAO.makePersistent(c);

		Assert.assertNotNull("No se ha creado el Cliente", c);
		
		List<Client> allClients = cDAO.findAll();
		Assert.assertEquals("La cantidad de proyectos del clientes no es correcta", 3, allClients.size());
		
		//Vuelvo a crear un cliente con el mismo nombre
		c = cDAO.makePersistent(c);
		Assert.assertNull("Se permite la creacion de cliente con el mismo nombre", c);
	}

	@Test
	public void testDelete() {
		Client actual = new Client();
		actual.setName("Nuevo Cliente");
		List<Client> list = cDAO.findByExample(actual);
		for (Client cl : list){
			log.info("Borrando Cliente : " + cl.getId());
			cDAO.delete(cl);
		}
	}
}
