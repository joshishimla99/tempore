package ar.fi.uba.tempore.hibernate.dao;


import java.util.List;

import junit.framework.Assert;

import org.hibernate.ObjectNotFoundException;
import org.junit.Test;

import ar.fi.uba.tempore.hibernate.TestDAO;
import fi.uba.tempore.poc.entities.Project;
import fi.uba.tempore.poc.entities.Task;
import fi.uba.tempore.poc.entities.UserProject;

public class TestProjectDAO extends TestDAO{

	private ProjectDAO pDAO = new ProjectDAO();
	
	@Test
	public void testFindId() {
		Project actual = null;
		try {	
			actual = pDAO.findById(1);
			Assert.assertEquals("No se encontro al tipo de contacto", "Proyecto 1"  , actual.getName());

			List<UserProject> tuList = actual.getUserProjectList();
			Assert.assertEquals("El tamaño de la lista asociada a la entidad no es correcto 1", 2, tuList.size());
			List<Task> taskList = actual.getTaskList();
			Assert.assertEquals("El tamaño de la lista asociada a la entidad no es correcto 2", 3, taskList.size());
			
		} catch (ObjectNotFoundException e){
			Assert.assertTrue("No se encontro la entidad", false);
		}
	}
	
	@Test
	public void testFindAll (){
		List<Project> findAll = pDAO.findAll();
		Assert.assertEquals("La cantidad del metodo FIND-ALL es incorrecta", 2, findAll.size());
	}
	
	@Test
	public void testMakePersistence (){
		Project newEntity = getDemoProject();						

		newEntity = pDAO.makePersistent(newEntity);		
		Assert.assertNotNull("No se ha podido crear la entidad", newEntity);
		
		List<Project> allEntities = pDAO.findAll();
		Assert.assertEquals("La cantidad de la entidad no es correcta", 3, allEntities.size());

		try {
			Project expected = getDemoProject();
			expected.setId(newEntity.getId());
			
			Project actual = pDAO.findById(newEntity.getId());
			Assert.assertEquals(expected.getName(), actual.getName());
		} catch (ObjectNotFoundException e){
			Assert.assertTrue("No se encontro la entidad creada", false);
		}
	}
	
	@Test
	public void testUpdate(){
		Integer id = 1;
		String dataExpected = "updated name";
		
		Project expected = pDAO.findById(id);
		expected.setName(dataExpected);
		
		pDAO.makePersistent(expected);
		
		Project actual = pDAO.findById(id);
		Assert.assertEquals("Ocurrio un error al actualizar", dataExpected, actual.getName());
	}
	
	private Project getDemoProject(){
		Project t = new Project();
		t.setName("Proyecto 3");
		t.setProjectState(new ProjectStateDAO().findById(1));
		return t;
	}
	
	@Test
	public void testDelete (){
		Project entity = new Project();
		entity.setName("Proyecto 1");
				
		List<Project> findByExample = pDAO.findByExample(entity);
		for (Project ct : findByExample){			
		
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
