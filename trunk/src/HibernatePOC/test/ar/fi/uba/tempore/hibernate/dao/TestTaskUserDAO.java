package ar.fi.uba.tempore.hibernate.dao;


import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.hibernate.ObjectNotFoundException;
import org.junit.Test;

import ar.fi.uba.tempore.hibernate.TestDAO;
import fi.uba.tempore.poc.entities.Task;
import fi.uba.tempore.poc.entities.TaskUser;
import fi.uba.tempore.poc.entities.User;

public class TestTaskUserDAO extends TestDAO{

	private TaskUserDAO tuDAO = new TaskUserDAO();
	
	@Test
	public void testFindId() {
		TaskUser actual = null;
		try {	
			actual = tuDAO.findById(1);
			Assert.assertEquals("No se encontro al tipo de contacto", new Integer(2), actual.getHourCount());
		} catch (ObjectNotFoundException e){
			Assert.assertTrue("No se encontro la entidad", false);
		}
	}
	
	@Test
	public void testFindAll (){
		List<TaskUser> findAll = tuDAO.findAll();
		Assert.assertEquals("La cantidad del metodo FIND-ALL es incorrecta", 7, findAll.size());
	}
	
	@Test
	public void testMakePersistence (){
		TaskUser newEntity = getDemoTaskUser();						

		newEntity = tuDAO.makePersistent(newEntity);		
		Assert.assertNotNull("No se ha podido crear la entidad", newEntity);
		
		List<TaskUser> allEntities = tuDAO.findAll();
		Assert.assertEquals("La cantidad de la entidad no es correcta", 8, allEntities.size());

		try {
			TaskUser expected = getDemoTaskUser();
			expected.setId(newEntity.getId());
			
			TaskUser actual = tuDAO.findById(newEntity.getId());
			Assert.assertEquals("No concuerda la fecha", expected.getDate().toString(), actual.getDate().toString());
			Assert.assertEquals("Hora", expected.getHourCount(), actual.getHourCount());
			Assert.assertEquals(expected.getTask().getId(), actual.getTask().getId());
			Assert.assertEquals(expected.getUser().getId(), actual.getUser().getId());			
			
		} catch (ObjectNotFoundException e){
			Assert.assertTrue("No se encontro la entidad creada", false);
		}
	}
	
	@Test
	public void testUpdate(){
		Integer id = 1;
		Integer dataExpected = 10;
		
		TaskUser expected = tuDAO.findById(id);
		expected.setHourCount(dataExpected);
		
		tuDAO.makePersistent(expected);
		
		TaskUser actual = tuDAO.findById(id);
		Assert.assertEquals("Ocurrio un error al actualizar", dataExpected, actual.getHourCount());
	}
	
	private TaskUser getDemoTaskUser(){
		TaskUser u = new TaskUser();
		u.setHourCount(9);
		u.setDate(new Date());
		u.setComment("comentario de la tarea");
		
		
		Task task = new TaskDAO().findById(1);
		u.setTask(task);
		User user = new UserDAO().findById(11);
		u.setUser(user);
		return u;
	}
	
	@Test
	public void testDelete (){
		Task task = new TaskDAO().findById(1);
		
		List<TaskUser> taskUser = task.getTaskUserList();
		Assert.assertEquals(3, taskUser.size());
		for (TaskUser ct : taskUser){			
		
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
		List<TaskUser> findAll = tuDAO.findAll();
		Assert.assertEquals("Se han borrado todas las instancias deseadas" , 4, findAll.size());
	}
}
