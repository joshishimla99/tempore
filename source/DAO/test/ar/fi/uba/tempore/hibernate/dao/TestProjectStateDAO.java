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
		this.validResult("PROJECTSTATE", "ProjectState_New.xml");
	}
	
	@Test
	public void testUpdate(){
		ProjectState expected = psDAO.findById(1);
		expected.setName("Vencido");
		expected.setDescription("Vencido y perdido");
		
		psDAO.makePersistent(expected);
		
		this.validResult("PROJECTSTATE", "ProjectState_Update.xml");
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
		
			psDAO.delete(ct);
		}
		this.validResult("PROJECTSTATE", "ProjectState_Delete.xml");
	}
}
