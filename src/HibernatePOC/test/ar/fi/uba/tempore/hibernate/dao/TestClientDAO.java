package ar.fi.uba.tempore.hibernate.dao;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import ar.fi.uba.tempore.hibernate.TestDAO;

import fi.uba.tempore.poc.entities.Client;
import fi.uba.tempore.poc.entities.Project;

public class TestClientDAO extends TestDAO{
	private ClientDAO cDAO = new ClientDAO();
	private Client c = null;
	
	@Before
	public void setUp() throws Exception {	
		super.setUp();
	}
	
	@Test
	public void testFindById() {		
		Client actual = cDAO.findById(1);
		Assert.assertNotNull(actual);
		Assert.assertNotNull(actual.getId());
		Assert.assertEquals("No se encontro al cliente", "TCS Tata Consulting Services"  , actual.getName());
			
		List<Project> actualProjectList = c.getProjectList();
		Assert.assertEquals("La cantidad de proyectos del clientes no es correcta", 2, actualProjectList.size());
	}

	@Test
	public void testMakePersistent() {
		c = new Client();
		c.setName("Nuevo Cliente");
		c = cDAO.makePersistent(c);
		log.info("Cliente Gueardado: ID = " + c.getId());
	}

	@Test
	public void testDelete() {
		c = new Client();
		c.setName("Nuevo Cliente");
		List<Client> list = cDAO.findByExample(c);
		for (Client cl : list){
			log.info("Borrando Cliente : " + cl.getId());
			cDAO.delete(cl);
		}
	}
}
