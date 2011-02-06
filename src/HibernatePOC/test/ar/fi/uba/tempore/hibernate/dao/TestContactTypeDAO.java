package ar.fi.uba.tempore.hibernate.dao;


import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ar.fi.uba.tempore.hibernate.util.HibernateUtil;

import fi.uba.tempore.poc.entities.Client;
import fi.uba.tempore.poc.entities.ContactType;
import fi.uba.tempore.poc.entities.User;

public class TestContactTypeDAO {

	private final Logger log = Logger.getLogger(this.getClass());
	private Transaction transaction;
	private ContactTypeDAO ctDAO = null;
	private ContactType ct = null;
	
	@Before
	public void setUp() throws Exception {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		transaction = session.beginTransaction();

		ctDAO = new ContactTypeDAO();				
	}

	@After
	public void tearDown() throws Exception {
		ctDAO = null;
		transaction.commit();
	}

	@Test
	public void testFindAll (){
		List<ContactType> findAll = ctDAO.findAll();
		int i=0;
		for (ContactType u : findAll){
			log.info("Tipo de Contacto "+(i++)+": " + u.getName() + ", " + u.getDescription()+ " ," + u.getId());
		}
	}
	
	@Test
	public void testMakePersistence (){
		ct = new ContactType();
		ct.setName("JTest");
				
		
		ct = ctDAO.makePersistent(ct);
		log.info("Nuevo usuario : " + ct.getId());
	}
	
	@Test
	public void testDelete (){
		ct = new ContactType();
		ct.setName("JTest");
				
		
		List<ContactType> findByExample = ctDAO.findByExample(ct);
		for (ContactType u : findByExample){
			log.info("Eliminado usuario : " + u.getId());
			ctDAO.delete(u);
		}
	}
}
