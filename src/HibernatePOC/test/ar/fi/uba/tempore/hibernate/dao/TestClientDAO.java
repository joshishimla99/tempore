package ar.fi.uba.tempore.hibernate.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ar.fi.uba.tempore.hibernate.util.HibernateUtil;

import fi.uba.tempore.poc.entities.Client;
import fi.uba.tempore.poc.entities.Project;

public class TestClientDAO {
	private final Logger log = Logger.getLogger(this.getClass());
	private ClientDAO cDAO = null;
	private Client c = null;
	private Transaction transaction;
	
	@Before
	public void setUp() throws Exception {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		transaction = session.beginTransaction();

		cDAO = new ClientDAO();				
	}

	@After
	public void tearDown() throws Exception {
		cDAO = null;
		transaction.commit();
	}

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
