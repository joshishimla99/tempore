package ar.fi.uba.tempore.hibernate.dao;


import java.util.List;

import junit.framework.Assert;

import org.hibernate.ObjectNotFoundException;
import org.junit.Test;

import ar.fi.uba.tempore.hibernate.TestDAO;

import fi.uba.tempore.poc.entities.Client;
import fi.uba.tempore.poc.entities.Contact;
import fi.uba.tempore.poc.entities.Position;
import fi.uba.tempore.poc.entities.UserProject;

public class TestPositionDAO extends TestDAO{

	private PositionDAO pDAO = new PositionDAO();
	
	@Test
	public void testFindId() {
		Position actual = null;
		try {	
			actual = pDAO.findById(1);
			Assert.assertEquals("No se encontro al tipo de contacto", "Analista"  , actual.getName());

			List<UserProject> list = actual.getUserProjectList();
			Assert.assertEquals("El tamaño de la lista asociada a la entidad no es correcto", 2, list.size());			
			
		} catch (ObjectNotFoundException e){
			Assert.assertTrue("No se encontro la entidad", false);
		}
	}
	
	@Test
	public void testFindAll (){
		List<Position> findAll = pDAO.findAll();
		Assert.assertEquals("La cantidad de contactos es incorrecta", 3, findAll.size());
	}
	
	@Test
	public void testMakePersistence (){
		Position newEntity = getDemoPosition();						

		newEntity = pDAO.makePersistent(newEntity);		
		Assert.assertNotNull("No se ha podido crear la entidad", newEntity);
		
		List<Position> allEntities = pDAO.findAll();
		Assert.assertEquals("La cantidad de la entidad no es correcta", 4, allEntities.size());

		try {
			Position expected = getDemoPosition();
			expected.setId(newEntity.getId());
			
			Position actual = pDAO.findById(newEntity.getId());
			Assert.assertEquals(expected.getName(), actual.getName());
			Assert.assertEquals(expected.getDescription(), actual.getDescription());
		} catch (ObjectNotFoundException e){
			Assert.assertTrue("No se encontro la entidad creada", false);
		}
	}
	
	@Test
	public void testUpdate(){
		Position expected = pDAO.findById(1);
		expected.setName("QC");
		
		pDAO.makePersistent(expected);
		
		Position actual = pDAO.findById(1);
		Assert.assertEquals("Ocurrio un error al actualizar", "QC", actual.getName());
	}
	
	private Position getDemoPosition(){
		Position ct = new Position();
		ct.setName("Sponsor");
		ct.setDescription("Sponsor del proyecto");
		return ct;
	}
	
	@Test
	public void testDelete (){
		Position entity = new Position();
		entity.setName("PM");
				
		List<Position> findByExample = pDAO.findByExample(entity);
		for (Position ct : findByExample){			
			List<UserProject> userProjectList = ct.getUserProjectList();
			for (UserProject userProject : userProjectList) {
				userProject.setPosition(null);
				new UserProjectDAO().makePersistent(userProject);
			}
			
			Integer id = ct.getId();
			pDAO.delete(ct);
			try {
				pDAO.findById(id);
				Assert.assertTrue("No se ha eliminado la entidad deseada", false);
			} catch (ObjectNotFoundException e){
				//No se encuentra la entidad
				Assert.assertTrue(true);
			}
		}
	}

}
