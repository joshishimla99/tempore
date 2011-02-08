package ar.fi.uba.tempore.hibernate.dao;


import java.util.List;

import org.junit.Test;

import ar.fi.uba.tempore.hibernate.TestDAO;

import fi.uba.tempore.poc.entities.ContactType;

public class TestContactTypeDAO extends TestDAO{

	private ContactTypeDAO ctDAO = new ContactTypeDAO();
	private ContactType ct = null;
	
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
