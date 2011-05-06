package ar.fi.uba.tempore.hibernate.dao;


import java.util.List;

import junit.framework.Assert;

import org.hibernate.ObjectNotFoundException;
import org.junit.Test;

import ar.fi.uba.tempore.hibernate.TestDAO;
import fi.uba.tempore.poc.entities.User;
import fi.uba.tempore.poc.entities.UserProject;

public class TestUserProjectDAO extends TestDAO{

	private UserProjectDAO tuDAO = new UserProjectDAO();
	
	@Test
	public void testFindId() {
		UserProject actual = null;
		try {	
			actual = tuDAO.findById(1);
			Assert.assertEquals("No se encontro al tipo de contacto", "Rana", actual.getUser().getName());
			Assert.assertEquals("No se encontro al tipo de contacto", "Proyecto 1", actual.getProject().getName());
			
		} catch (ObjectNotFoundException e){
			Assert.assertTrue("No se encontro la entidad", false);
		}
	}
	
	@Test
	public void testFindAll (){
		List<UserProject> findAll = tuDAO.findAll();
		Assert.assertEquals("La cantidad del metodo FIND-ALL es incorrecta", 4, findAll.size());
	}
	
	@Test
	public void testMakePersistence (){
		UserProject newEntity = getDemoUserProject();						

		newEntity = tuDAO.makePersistent(newEntity);		
		Assert.assertNotNull("No se ha podido crear la entidad", newEntity);
		
		List<UserProject> allEntities = tuDAO.findAll();
		Assert.assertEquals("La cantidad de la entidad no es correcta", 5, allEntities.size());

		try {
			UserProject expected = getDemoUserProject();
			expected.setId(newEntity.getId());
			
			UserProject actual = tuDAO.findById(newEntity.getId());
			Assert.assertEquals(expected.getPosition().getName(), actual.getPosition().getName());
			Assert.assertEquals(expected.getUser().getName(), actual.getUser().getName());
			Assert.assertEquals(expected.getProject().getName(), actual.getProject().getName());
			
		} catch (ObjectNotFoundException e){
			Assert.assertTrue("No se encontro la entidad creada", false);
		}
	}
	
	@Test
	public void testUpdate(){
		Integer id = 1;

		UserProject expected = tuDAO.findById(id);
		expected.setUser(new UserDAO().findById(13));
		
		tuDAO.makePersistent(expected);
		
		UserProject actual = tuDAO.findById(id);
		Assert.assertEquals("Ocurrio un error al actualizar", "Bill", actual.getUser().getName());
	}
	
	private UserProject getDemoUserProject(){
		UserProject u = new UserProject();
		u.setPosition(new PositionDAO().findById(1));
		u.setProject(new ProjectDAO().findById(1));
		u.setUser(new UserDAO().findById(13));
		return u;
	}
	
/*	@Test
	public void testDelete (){
		User user = new UserDAO().findById(11);
		
		List<UserProject> findByExample = user.getUserProjectList();
		Assert.assertEquals(1, findByExample.size());
		for (UserProject ct : findByExample){			
		
			Integer id = ct.getId();
			tuDAO.delete(ct);
			try {
				tuDAO.findById(id);
				Assert.assertTrue("No se ha eliminado la entidad deseada", false);
			} catch (ObjectNotFoundException e){
				//No se encuentra la entidad
				Assert.assertTrue(true);
			}
		}
		List<UserProject> findAll = tuDAO.findAll();
		Assert.assertEquals("Se han borrado todas las instancias deseadas" , 3, findAll.size());
	}
*/
}
