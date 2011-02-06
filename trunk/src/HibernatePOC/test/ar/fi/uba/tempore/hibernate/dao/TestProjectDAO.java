package ar.fi.uba.tempore.hibernate.dao;

import static org.junit.Assert.fail;

import java.util.ArrayList;
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
import fi.uba.tempore.poc.entities.ProjectState;
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

	//@Test
	public void testFindById() {
		p = pDAO.findById(1);
		log.info("Objeto encontrado: " + p.getName() + ", " + p.getProjectState().getName());
		log.info("Tareas al proyecto: ");
		int i = 0;
		for (Task t : p.getTaskList()){
			log.info((i++)+") " + t.getName());
		}
		
		log.info("Clientes del proyecto: ");
		int j = 0;
		for (Client t : p.getClientList()){
			log.info(" \t"+(j++)+") " + t.getName());
		}
	}

	//@Test
	public void testFindAll() {		
		List<Project> findAll = pDAO.findAll();
		int i=0;
		for (Project pr : findAll){
			log.info((i++) + ") "  + pr.getId() + ", " + pr.getName() + ", " + pr.getProjectState());
		}
	}

	@Test
	public void testMakePersistent() {
		p = new Project();
		p.setId(null);
		p.setName("Proyecto Test");		
		
		ProjectStateDAO  psDAO = new ProjectStateDAO();		
		ProjectState ps = psDAO.findById(1);		
		p.setProjectState(ps);
		p.setTaskList(null);

		ClientDAO cDAO = new ClientDAO();
		Client cli =  cDAO.findById(1);
		p.addClient(cli);
		
		p = pDAO.makePersistent(p);
		log.info("Nuevo Proyecto Creado: ID = " + p.getId());		

		cli.addProject(p);
		cDAO.makePersistent(cli);		
		log.info("Se actualiza cliente con proyecto: ID = " + cli.getId());
	}

	@Test
	public void testDelete() {
		log.info("****** DELETE *********");
		p = new Project();
		p.setName("Proyecto Test");
		
		List<Project> findByExample = pDAO.findByExample(p);		
		for (Project pr : findByExample){
			log.info("Borrando proyecto ID = " + pr.getId() + " ("+pr.getName()+")");
			pDAO.delete(pr);
		}
		log.info("**********************");
	}

}
