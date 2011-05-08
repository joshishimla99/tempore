package ar.fi.uba.tempore.hibernate.dao;


import java.util.List;

import junit.framework.Assert;

import org.hibernate.ObjectNotFoundException;
import org.junit.Test;

import ar.fi.uba.tempore.hibernate.TestDAO;
import fi.uba.tempore.poc.entities.Task;
import fi.uba.tempore.poc.entities.TaskType;
import fi.uba.tempore.poc.entities.TaskUser;

public class TestTaskTypeDAO extends TestDAO{

	private TaskTypeDAO psDAO = new TaskTypeDAO();
	
	@Test
	public void testFindId() {
		TaskType actual = null;
		try {	
			actual = psDAO.findById(1);
			Assert.assertEquals("No se encontro al tipo de contacto", "Analisis"  , actual.getName());

			List<Task> list = actual.getTaskList();
			Assert.assertEquals("El tamaño de la lista asociada a la entidad no es correcto", 2, list.size());			
			
		} catch (ObjectNotFoundException e){
			Assert.assertTrue("No se encontro la entidad", false);
		}
	}
	
	@Test
	public void testFindAll (){
		List<TaskType> findAll = psDAO.findAll();
		Assert.assertEquals("La cantidad de contactos es incorrecta", 3, findAll.size());
	}
	
	@Test
	public void testMakePersistence (){
		TaskType newEntity = getDemoTaskType();						

		newEntity = psDAO.makePersistent(newEntity);		
		Assert.assertNotNull("No se ha podido crear la entidad", newEntity);
		
		List<TaskType> allEntities = psDAO.findAll();
		Assert.assertEquals("La cantidad de la entidad no es correcta", 4, allEntities.size());

		try {
			TaskType expected = getDemoTaskType();
			expected.setId(newEntity.getId());
			
			TaskType actual = psDAO.findById(newEntity.getId());
			Assert.assertEquals(expected.getName(), actual.getName());
			Assert.assertEquals(expected.getDescription(), actual.getDescription());
		} catch (ObjectNotFoundException e){
			Assert.assertTrue("No se encontro la entidad creada", false);
		}
	}
	
	@Test
	public void testUpdate(){
		TaskType expected = psDAO.findById(1);
		expected.setName("update");
		
		psDAO.makePersistent(expected);
		
		TaskType actual = psDAO.findById(1);
		Assert.assertEquals("Ocurrio un error al actualizar", "update", actual.getName());
	}
	
	private TaskType getDemoTaskType(){
		TaskType ps = new TaskType();
		ps.setName("QC");
		ps.setDescription("Test de la aplicacion");
		return ps;
	}
	
	@Test
	public void testDelete (){
		TaskType entity = new TaskType();
		entity.setName("Desarrollo");
				
		List<TaskType> findByExample = psDAO.findByExample(entity);
		for (TaskType tt : findByExample){			
		
			List<Task> taskList = tt.getTaskList();
			for (Task task : taskList) {
				List<TaskUser> taskUserList = task.getTaskUserList();
				for (TaskUser taskUser : taskUserList) {
					new TaskUserDAO().delete(taskUser);
				}
				new TaskDAO().delete(task);
			}
			
			Integer id = tt.getId();
			psDAO.delete(tt);
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
