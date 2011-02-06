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
import fi.uba.tempore.poc.entities.User;

public class TestUserDAO {

	private final Logger log = Logger.getLogger(this.getClass());
	private Transaction transaction;
	private UserDAO uDAO = null;
	private User u = null;
	
	@Before
	public void setUp() throws Exception {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		transaction = session.beginTransaction();

		uDAO = new UserDAO();				
	}

	@After
	public void tearDown() throws Exception {
		uDAO = null;
		transaction.commit();
	}

	@Test
	public void testFindAll (){
		List<User> findAll = uDAO.findAll();
		int i=0;
		for (User u : findAll){
			log.info("Usuario "+(i++)+": " + u.getName() + ", " + u.getLastName()+ " ," + u.getId());
		}
	}
	
	@Test
	public void testMakePersistence (){
		u = new User();
		u.setName("JTest");
		u.setLastName("JLastTest");
		u.setAddress("JDirTest");
		u.setCountry("JCountryTest");
		u.setPhone("1111111111");
		u.setState("JStateTest");		
		
		u = uDAO.makePersistent(u);
		log.info("Nuevo usuario : " + u.getId());
	}
	
	@Test
	public void testDelete (){
		u = new User();
		u.setName("JTest");
				
		
		List<User> findByExample = uDAO.findByExample(u);
		for (User u : findByExample){
			log.info("Eliminado usuario : " + u.getId());
			uDAO.delete(u);
		}
	}
}
