package ar.fi.uba.tempore.hibernate.dao;


import java.util.List;

import junit.framework.Assert;

import org.hibernate.ObjectNotFoundException;
import org.junit.Test;

import ar.fi.uba.tempore.hibernate.TestDAO;
import fi.uba.tempore.poc.entities.Role;
import fi.uba.tempore.poc.entities.TaskUser;
import fi.uba.tempore.poc.entities.User;
import fi.uba.tempore.poc.entities.UserProject;

public class TestUserDAO extends TestDAO{

	private UserDAO psDAO = new UserDAO();
	
	@Test
	public void testFindId() {
		User actual = null;
		try {	
			actual = psDAO.findById(11);
			Assert.assertEquals("No se encontro al tipo de contacto", "Rana"  , actual.getName());

			List<TaskUser> tuList = actual.getTaskUserList();
			Assert.assertEquals("El tamaño de la lista asociada a la entidad no es correcto 2", 3, tuList.size());
			List<UserProject> pList = actual.getUserProjectList();
			Assert.assertEquals("El tamaño de la lista asociada a la entidad no es correcto 3", 1, pList.size());
			
		} catch (ObjectNotFoundException e){
			Assert.assertTrue("No se encontro la entidad", false);
		}
	}
	
	@Test
	public void testFindAll (){
		List<User> findAll = psDAO.findAll();
		Assert.assertEquals("La cantidad del metodo FIND-ALL es incorrecta", 3, findAll.size());
	}
	
	@Test
	public void testMakePersistence (){
		User newEntity = getDemoUser();						

		newEntity = psDAO.makePersistent(newEntity);		
		Assert.assertNotNull("No se ha podido crear la entidad", newEntity);
		
		List<User> allEntities = psDAO.findAll();
		Assert.assertEquals("La cantidad de la entidad no es correcta", 4, allEntities.size());

		try {
			User expected = getDemoUser();
			expected.setId(newEntity.getId());
			
			User actual = psDAO.findById(newEntity.getId());
			Assert.assertEquals(expected.getName(), actual.getName());
			Assert.assertEquals(expected.getLastName(), actual.getLastName());
			Assert.assertEquals(expected.getCountry(), actual.getCountry());
			Assert.assertEquals(expected.getPhone(), actual.getPhone());
			Assert.assertEquals(expected.getState(), actual.getState());
			
		} catch (ObjectNotFoundException e){
			Assert.assertTrue("No se encontro la entidad creada", false);
		}
	}
	
	@Test
	public void testUpdate(){
		Integer id = 11;
		String dataExpected = "updated name";
		
		User expected = psDAO.findById(id);
		expected.setName(dataExpected);
		
		psDAO.makePersistent(expected);
		
		User actual = psDAO.findById(id);
		Assert.assertEquals("Ocurrio un error al actualizar", dataExpected, actual.getName());
	}
	
	private User getDemoUser(){
		User u = new User();
		u.setName("JTest");
		u.setLastName("JLastTest");
		u.setAddress("JDirTest");
		u.setCountry("JCountryTest");
		u.setPhone("1111111111");
		u.setState("A");		
		return u;
	}
	
	@Test
	public void testDelete (){
		User entity = new User();
		entity.setName("Rana");
				
		List<User> findByExample = psDAO.findByExample(entity);
		for (User u : findByExample){					
			
			//Elimino las horas cargadas
			List<TaskUser> taskUserList = u.getTaskUserList();
			for (TaskUser taskUser : taskUserList) {
				new TaskUserDAO().delete(taskUser);
			}
			
			//Elimino la asginacion a los proyectos
			List<UserProject> userProjectList = u.getUserProjectList();
			for (UserProject up : userProjectList) {
				new UserProjectDAO().delete(up);
			}
			
			Integer id = u.getId();
			psDAO.delete(u);
			try {
				psDAO.findById(id);
				Assert.assertTrue("No se ha eliminado la entidad deseada", false);
			} catch (ObjectNotFoundException e){
				//No se encuentra la entidad
				Assert.assertTrue(true);
			}
		}
	}
}
