package ar.fi.uba.tempore.hibernate.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ar.fi.uba.tempore.hibernate.util.HibernateUtil;
import fi.uba.tempore.poc.entities.TaskState;

public class TestTaskStateDAO {
	private final Logger log = Logger.getLogger(TestTaskStateDAO.class);
	private TaskStateDAO tsDAO = null;

	private Transaction beginTransaction;
	
	@Before
	public void setUp() throws Exception {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		
		beginTransaction = session.beginTransaction();
		
		tsDAO = new TaskStateDAO();	
	}

	@After
	public void tearDown() throws Exception {
		beginTransaction.commit();
	}

	@Test
	public void testFindById() {
		try { 
			log.info("Buscar elemento con ID = 2");
			TaskState ent = tsDAO.findById(new Integer(2));
			if (ent.getId() != null){
				log.info("Elemento encontrado = " + ent.getId() + ", " + ent.getName() + ", " + ent.getDescription());
			} else {
				log.error("Elemento no encontrado");
			}
			
		} catch (Throwable ex) {
			ex.printStackTrace();
		}
	}
	
	@Test
	public void testFindAll() {
		List<TaskState> list = tsDAO.findAll();
		int i=0;
		log.info("Todos los elementos");
		for (TaskState ts : list){
			log.info((i++)+") " + ts.getId() + ", " + ts.getName() + ", " + ts.getDescription());
		}
	}

	@Test
	public void testSave() {
		try { 
			TaskState ts = new TaskState();
			ts.setDescription("Nuevo estato");
			ts.setName("Nuevo");
			
			log.info("Guardar un elemento");
			TaskState makePersistent = tsDAO.makePersistent(ts);
			log.info("Elemento nuevo insertado, ID: " + makePersistent.getId());			
			
		} catch (Throwable ex) {
			ex.printStackTrace();
		}
	}

	@Test
	public void testDelete(){
		TaskState tsFind = new TaskState();
		tsFind.setName("Nuevo");
		
		List<TaskState> tsList = tsDAO.findByExample(tsFind);
		int i= 0;
		for (TaskState ts : tsList){
			log.info((i++)+") Borrando id= " + ts.getId() + ", " + ts.getName() + ", " + ts.getDescription());
			tsDAO.delete(ts);
		}
	}
	
	
}
