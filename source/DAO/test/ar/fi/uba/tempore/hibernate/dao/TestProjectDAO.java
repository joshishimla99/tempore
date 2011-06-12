package ar.fi.uba.tempore.hibernate.dao;


import java.text.ParseException;
import java.util.List;

import junit.framework.Assert;

import org.apache.log4j.lf5.util.DateFormatManager;
import org.hibernate.ObjectNotFoundException;
import org.junit.Test;

import ar.fi.uba.tempore.dao.AlertDAO;
import ar.fi.uba.tempore.dao.ClientDAO;
import ar.fi.uba.tempore.dao.ProjectDAO;
import ar.fi.uba.tempore.dao.ProjectStateDAO;
import ar.fi.uba.tempore.dao.TaskDAO;
import ar.fi.uba.tempore.dao.TaskUserDAO;
import ar.fi.uba.tempore.dao.UserProjectDAO;
import ar.fi.uba.tempore.entity.Alert;
import ar.fi.uba.tempore.entity.Client;
import ar.fi.uba.tempore.entity.Project;
import ar.fi.uba.tempore.entity.Task;
import ar.fi.uba.tempore.entity.TaskUser;
import ar.fi.uba.tempore.entity.UserProject;
import ar.fi.uba.tempore.hibernate.TestDAO;

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
		
		this.validResult("PROJECT", "Project_New.xml");
	}
	
	@Test
	public void testUpdate(){
		Project expected = pDAO.findById(1);
		expected.setName("Proyecto 3");
		expected.setBudget(12.23);
		expected.setDescription("Descripcion a");
		try {
			expected.setInitDate(new DateFormatManager("dd-MM-yyyy").parse("11-12-2008"));
			expected.setEndDate(new DateFormatManager("dd-MM-yyyy").parse("11-12-2008"));
		} catch (ParseException e) {
			e.printStackTrace();
		}		
		
		expected.setProjectState(new ProjectStateDAO().findById(1));
		
		pDAO.makePersistent(expected);
		
		this.validResult("PROJECT", "Project_Update.xml");
	}
	
	private Project getDemoProject(){
		Project t = new Project();
		t.setName("Proyecto 3");
		t.setBudget(12.23);
		t.setDescription("Descripcion a");
		try {
			t.setInitDate(new DateFormatManager("dd-MM-yyyy").parse("11-12-2008"));
			t.setEndDate(new DateFormatManager("dd-MM-yyyy").parse("11-12-2008"));
		} catch (ParseException e) {
			e.printStackTrace();
		}		
		
		t.setProjectState(new ProjectStateDAO().findById(1));
		return t;
	}
	
	@Test
	public void testDelete (){
		Project entity = new Project();
		entity.setName("Proyecto 1");
				
		List<Project> findByExample = pDAO.findByExample(entity);
		for (Project p : findByExample){			
			//remove client
			List<Client> clientList = p.getClientList();
			for (Client client : clientList) {
				client.getProjectList().remove(p);
				new ClientDAO().makePersistent(client);
			}
			
			//Elimino las tareas que tenia asociada
			List<Task> taskList = p.getTaskList();
			for (Task task : taskList) {
				List<TaskUser> taskUserList = task.getTaskUserList();
				//Elimino las horas cargadas
				for (TaskUser taskUser : taskUserList) {
					new TaskUserDAO().delete(taskUser);
				}
				new TaskDAO().delete(task);
			}
			
			//Elimino los usuarios asignados
			List<UserProject> userProjectList = p.getUserProjectList();
			for (UserProject userProject : userProjectList) {
				
				List<Alert> alertList = userProject.getAlertList();
				//Elimino las alertas del usuario
				for (Alert alert : alertList) {
					new AlertDAO().delete(alert);
				}
				
				new UserProjectDAO().delete(userProject);
			} 
			
			pDAO.delete(p);
		}
		this.validResult("PROJECT", "Project_Delete.xml");
		this.validResult("USERPROJECT", "Project_Delete.xml");
		this.validResult("PROJECTCLIENT", "Project_Delete.xml");
		this.validResult("TASK", "Project_Delete.xml");
	}
}
