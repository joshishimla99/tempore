package ar.fi.uba.tempore.hibernate.dao;

import static org.junit.Assert.fail;

import org.apache.log4j.Logger;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ar.fi.uba.tempore.hibernate.util.HibernateUtil;
import fi.uba.tempore.poc.entities.Project;
import fi.uba.tempore.poc.entities.Task;

public class TestProjectDAO {

	private final Logger log = Logger.getLogger(this.getClass());
	private ProjectDAO pDAO = new ProjectDAO();
	private Project p = null;
	private Transaction transaction;

	
	@Before
	public void setUp() throws Exception {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		transaction = session.beginTransaction();
	}

	@After
	public void tearDown() throws Exception {
		transaction.commit();
	}

	@Test
	public void testFindById() {
		p = pDAO.findById(1);
		log.info("Objeto encontrado: " + p.getName() + ", " + p.getProjectState().getName());
		log.info("Tareas al proyecto: ");
		int i = 0;
		for (Task t : p.getTaskList()){
			log.info((i++)+") " + t.getName());
		}
	}

	@Test
	public void testFindAll() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testMakePersistent() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testDelete() {
		fail("Not yet implemented"); // TODO
	}

}
