package ar.fi.uba.tempore.hibernate.dao;


import java.util.List;

import junit.framework.Assert;

import org.hibernate.ObjectNotFoundException;
import org.junit.Test;

import ar.fi.uba.tempore.hibernate.TestDAO;
import fi.uba.tempore.poc.entities.Project;
import fi.uba.tempore.poc.entities.Role;
import fi.uba.tempore.poc.entities.Task;
import fi.uba.tempore.poc.entities.TaskUser;

public class TestTaskDAO extends TestDAO{

	private TaskDAO psDAO = new TaskDAO();
	
	@Test
	public void testFindId() {
		Task actual = null;
		try {	
			actual = psDAO.findById(1);
			Assert.assertEquals("No se encontro al tipo de contacto", "Analisis Riesgos"  , actual.getName());

			List<TaskUser> tuList = actual.getTaskUser();
			Assert.assertEquals("El tamaño de la lista asociada a la entidad no es correcto 1", 3, tuList.size());
			
		} catch (ObjectNotFoundException e){
			Assert.assertTrue("No se encontro la entidad", false);
		}
	}
	
	@Test
	public void testFindAll (){
		List<Task> findAll = psDAO.findAll();
		Assert.assertEquals("La cantidad del metodo FIND-ALL es incorrecta", 4, findAll.size());
	}
	
	@Test
	public void testMakePersistence (){
		Task newEntity = getDemoTask();						

		newEntity = psDAO.makePersistent(newEntity);		
		Assert.assertNotNull("No se ha podido crear la entidad", newEntity);
		
		List<Task> allEntities = psDAO.findAll();
		Assert.assertEquals("La cantidad de la entidad no es correcta", 5, allEntities.size());

		try {
			Task expected = getDemoTask();
			expected.setId(newEntity.getId());
			
			Task actual = psDAO.findById(newEntity.getId());
			Assert.assertEquals(expected.getName(), actual.getName());
			Assert.assertEquals(expected.getDescription(), actual.getDescription());
			Assert.assertEquals(expected.getTaskType().getName(), actual.getTaskType().getName());
			Assert.assertEquals(expected.getProject().getName(), actual.getProject().getName());
			
		} catch (ObjectNotFoundException e){
			Assert.assertTrue("No se encontro la entidad creada", false);
		}
	}
	
	@Test
	public void testUpdate(){
		Integer id = 1;
		String dataExpected = "updated name";
		
		Task expected = psDAO.findById(id);
		expected.setName(dataExpected);
		
		psDAO.makePersistent(expected);
		
		Task actual = psDAO.findById(id);
		Assert.assertEquals("Ocurrio un error al actualizar", dataExpected, actual.getName());
	}
	
	private Task getDemoTask(){
		Task t = new Task();
		t.setName("Tarea");
		t.setDescription("Descripcion de la tarea");
		t.setTaskType(new TaskTypeDAO().findById(1));
		t.setProject(new ProjectDAO().findById(1));				
		return t;
	}
	
	@Test
	public void testDelete (){
		Task entity = new Task();
		entity.setName("Analisis Riesgos");
				
		List<Task> findByExample = psDAO.findByExample(entity);
		for (Task ct : findByExample){			
		
			Integer id = ct.getId();
			psDAO.delete(ct);
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
