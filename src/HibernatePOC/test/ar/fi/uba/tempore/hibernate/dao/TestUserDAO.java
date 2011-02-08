package ar.fi.uba.tempore.hibernate.dao;


import java.util.List;

import org.junit.Test;

import ar.fi.uba.tempore.hibernate.TestDAO;

import fi.uba.tempore.poc.entities.User;

public class TestUserDAO extends TestDAO{
	private UserDAO uDAO = new UserDAO();
	private User u = null;
	
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
