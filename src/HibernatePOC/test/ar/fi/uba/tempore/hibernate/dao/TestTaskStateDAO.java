package ar.fi.uba.tempore.hibernate.dao;

import static org.junit.Assert.*;

import java.io.Serializable;
import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;
import org.hibernate.CacheMode;
import org.hibernate.Criteria;
import org.hibernate.EntityMode;
import org.hibernate.Filter;
import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.LobHelper;
import org.hibernate.LockMode;
import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.ReplicationMode;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.TypeHelper;
import org.hibernate.UnknownProfileException;
import org.hibernate.Session.LockRequest;
import org.hibernate.cfg.Configuration;
import org.hibernate.classic.Session;
import org.hibernate.jdbc.Work;
import org.hibernate.stat.SessionStatistics;
import org.hibernate.type.Type;
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
		HibernateUtil.getSessionFactory().close();
	}

//	@Test
//	public void testFindById() {
//		try { 
//			log.info("Buscar elemento con ID = 2");
//			TaskState ent = tsDAO.findById(new Integer(2));
//			if (ent != null){
//				log.info("Elemento encontrado = " + ent.getId() + ", " + ent.getName() + ", " + ent.getDescription());
//			} else {
//				log.error("Elemento no encontrado");
//			}
//			
//		} catch (Throwable ex) {
//			ex.printStackTrace();
//		}
//	}

//	@Test
//	public void testSave() {
//		try { 
//			TaskState ts = new TaskState();
//			ts.setDescription("Nueva Descripcion");
//			ts.setName("Nuevo");
//			
//			log.info("Guardar un elemento");
//			TaskState makePersistent = tsDAO.makePersistent(ts);
//			log.info("Elemento nuevo insertado, ID: " + makePersistent.getId());
//			
//
//			
//		} catch (Throwable ex) {
//			ex.printStackTrace();
//		}
//	}

	@Test
	public void testFindAll() {
		try { 
			List<TaskState> list = tsDAO.findAll();
			int i=0;
			log.info("Todos los elementos");
			for (TaskState ts : list){
				log.info((i++)+") " + ts.getId() + ", " + ts.getName() + ", " + ts.getDescription());
			}
			

			
		} catch (Throwable ex) {
			ex.printStackTrace();
		}
	}
}
