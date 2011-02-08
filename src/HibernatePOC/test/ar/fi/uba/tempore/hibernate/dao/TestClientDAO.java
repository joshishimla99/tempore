package ar.fi.uba.tempore.hibernate.dao;

import java.util.List;

import org.junit.Test;

import ar.fi.uba.tempore.hibernate.TestDAO;

import fi.uba.tempore.poc.entities.Client;
import fi.uba.tempore.poc.entities.Project;

public class TestClientDAO extends TestDAO{
	private ClientDAO cDAO = new ClientDAO();
	private Client c = null;
		
	@Test
	public void testFindById() {		
		c = cDAO.findById(1);
		log.info("Cliente " + c.getName());
		List<Project> projectList = c.getProjectList();
		for (Project p : projectList){
			log.info("\t" + p.getName());
		}
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
