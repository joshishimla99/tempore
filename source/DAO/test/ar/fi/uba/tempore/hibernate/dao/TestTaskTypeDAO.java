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

		this.validResult("TASKTYPE", "TaskType_New.xml");		
	}
	
	@Test
	public void testUpdate(){
		TaskType expected = psDAO.findById(1);
		expected.setName("update");
		expected.setDescription("descripción");
		
		psDAO.makePersistent(expected);
		
		this.validResult("TASKTYPE", "TaskType_Update.xml");
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
			
			psDAO.delete(tt);			
		}
		
		this.validResult("TASKTYPE", "TaskType_Delete.xml");			
	}
}
