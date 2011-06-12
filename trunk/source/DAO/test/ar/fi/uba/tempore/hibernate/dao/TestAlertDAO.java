package ar.fi.uba.tempore.hibernate.dao;


import java.util.List;

import junit.framework.Assert;

import org.hibernate.ObjectNotFoundException;
import org.junit.Test;

import ar.fi.uba.tempore.dao.AlertDAO;
import ar.fi.uba.tempore.dao.UserProjectDAO;
import ar.fi.uba.tempore.entity.Alert;
import ar.fi.uba.tempore.entity.UserProject;
import ar.fi.uba.tempore.hibernate.TestDAO;

public class TestAlertDAO extends TestDAO{

	private AlertDAO aDAO = new AlertDAO();
	
	@Test
	public void testFindId() {
		Alert actual = null;
		try {	
			actual = aDAO.findById(1);
			Assert.assertEquals("No se encontro al tipo de contacto", "Analista"  , actual.getName());

		} catch (ObjectNotFoundException e){
			Assert.assertTrue("No se encontro la entidad", false);
		}
	}
	
	@Test
	public void testFindAll (){
		List<Alert> findAll = aDAO.findAll();
		Assert.assertEquals("La cantidad de contactos es incorrecta", 3, findAll.size());
	}
	
	@Test
	public void testMakePersistence (){
		Alert newEntity = getDemoAlert();						
		newEntity = aDAO.makePersistent(newEntity);		
		
		this.validResult("ALERT", "Alert_New.xml");		
	}
	
	@Test
	public void testUpdate(){
		Alert expected = aDAO.findById(1);
		expected.setName("QC");
		expected.setDescription("Actualizacion de descripcion");
		UserProject findById = new UserProjectDAO().findById(2);
		expected.setUserProject(findById);
		
		aDAO.makePersistent(expected);
		
		this.validResult("ALERT", "Alert_Update.xml");
	}
	
	private Alert getDemoAlert(){
		Alert ct = new Alert();
		ct.setName("Alerta Nueva");
		ct.setDescription("Descripcion de la alerta!!!!");
		
		UserProject findById = new UserProjectDAO().findById(1);
		ct.setUserProject(findById);
		return ct;
	}
	
	@Test
	public void testDelete (){
		Alert entity = new Alert();
		entity.setName("PM");
				
		List<Alert> findByExample = aDAO.findByExample(entity);
		for (Alert ct : findByExample){
			aDAO.delete(ct);
		}
		this.validResult("ALERT", "Alert_Delete.xml");
	}

}
