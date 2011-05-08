package ar.fi.uba.tempore.hibernate.dao;


import java.util.List;

import junit.framework.Assert;

import org.hibernate.ObjectNotFoundException;
import org.junit.Test;

import ar.fi.uba.tempore.hibernate.TestDAO;
import fi.uba.tempore.poc.entities.Project;
import fi.uba.tempore.poc.entities.ProjectState;

public class TestProjectStateDAO extends TestDAO{

	private ProjectStateDAO psDAO = new ProjectStateDAO();
	
	@Test
	public void testFindId() {
		ProjectState actual = null;
		try {	
			actual = psDAO.findById(4);
			Assert.assertEquals("No se encontro al tipo de contacto", "Cerrado"  , actual.getName());

			List<Project> list = actual.getProjectList();
			Assert.assertEquals("El tamaño de la lista asociada a la entidad no es correcto", 0, list.size());			
			
		} catch (ObjectNotFoundException e){
			Assert.assertTrue("No se encontro la entidad", false);
		}
	}
	
	@Test
	public void testFindAll (){
		List<ProjectState> findAll = psDAO.findAll();
		Assert.assertEquals("La cantidad de contactos es incorrecta", 4, findAll.size());
	}
	
	@Test
	public void testMakePersistence (){
		ProjectState newEntity = getDemoProject();						

		newEntity = psDAO.makePersistent(newEntity);		
		Assert.assertNotNull("No se ha podido crear la entidad", newEntity);
		
		List<ProjectState> allEntities = psDAO.findAll();
		Assert.assertEquals("La cantidad de la entidad no es correcta", 5, allEntities.size());

		try {
			ProjectState expected = getDemoProject();
			expected.setId(newEntity.getId());
			
			ProjectState actual = psDAO.findById(newEntity.getId());
			Assert.assertEquals(expected.getName(), actual.getName());
			Assert.assertEquals(expected.getDescription(), actual.getDescription());
		} catch (ObjectNotFoundException e){
			Assert.assertTrue("No se encontro la entidad creada", false);
		}
	}
	
	@Test
	public void testUpdate(){
		ProjectState expected = psDAO.findById(1);
		expected.setName("QC");
		
		psDAO.makePersistent(expected);
		
		ProjectState actual = psDAO.findById(1);
		Assert.assertEquals("Ocurrio un error al actualizar", "QC", actual.getName());
	}
	
	private ProjectState getDemoProject(){
		ProjectState ps = new ProjectState();
		ps.setName("Vencido");
		ps.setDescription("Vencido y perdido");
		return ps;
	}
	
	@Test
	public void testDelete (){
		ProjectState entity = new ProjectState();
		entity.setName("Adquirido");
				
		List<ProjectState> findByExample = psDAO.findByExample(entity);
		for (ProjectState ct : findByExample){			
			
			//Elimino la referencia de los proyectos con ese estado
			List<Project> projectList = ct.getProjectList();
			for (Project project : projectList) {
				project.setProjectState(null);
				new ProjectDAO().makePersistent(project);
			}
		
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
