package ar.fi.uba.tempore.hibernate.dao;


import java.util.List;

import junit.framework.Assert;

import org.hibernate.ObjectNotFoundException;
import org.junit.Test;

import ar.fi.uba.tempore.dao.ProjectDAO;
import ar.fi.uba.tempore.dao.TaskDAO;
import ar.fi.uba.tempore.dao.TaskTypeDAO;
import ar.fi.uba.tempore.dao.TaskUserDAO;
import ar.fi.uba.tempore.entity.Task;
import ar.fi.uba.tempore.entity.TaskUser;
import ar.fi.uba.tempore.hibernate.TestDAO;

public class TestTaskDAO extends TestDAO{

	private TaskDAO tDAO = new TaskDAO();
	
	@Test
	public void testFindId() {
		Task actual = null;
		try {	
			actual = tDAO.findById(1);
			Assert.assertEquals("No se encontro al tipo de contacto", "Analisis Riesgos"  , actual.getName());

			List<TaskUser> tuList = actual.getTaskUserList();
			Assert.assertEquals("El tamaño de la lista asociada a la entidad no es correcto 1", 3, tuList.size());
			
		} catch (ObjectNotFoundException e){
			Assert.assertTrue("No se encontro la entidad", false);
		}
	}
	
	@Test
	public void testFindAll (){
		List<Task> findAll = tDAO.findAll();
		Assert.assertEquals("La cantidad del metodo FIND-ALL es incorrecta", 4, findAll.size());
	}
	
	@Test
	public void testMakePersistence (){
		Task newEntity = getDemoTask();						

		newEntity = tDAO.makePersistent(newEntity);		
		this.validResult("TASK", "Task_New.xml");
	}
	
	@Test
	public void testUpdate(){		
		Task expected = tDAO.findById(1);
		expected.setName("Tarea");
		expected.setDescription("Descripcion de la tarea");
		expected.setTaskType(new TaskTypeDAO().findById(2));
		expected.setProject(new ProjectDAO().findById(2));	
		
		tDAO.makePersistent(expected);
		
		this.validResult("TASK", "Task_Update.xml");
	}
	
	private Task getDemoTask(){
		Task t = new Task();
		t.setName("Tarea");
		t.setDescription("Descripcion de la tarea");
		t.setTaskType(new TaskTypeDAO().findById(2));
		t.setProject(new ProjectDAO().findById(2));				
		return t;
	}
	
	@Test
	public void testDelete (){
		Task entity = new Task();
		entity.setName("Analisis Riesgos");
				
		List<Task> findByExample = tDAO.findByExample(entity);
		for (Task t : findByExample){			

			List<TaskUser> taskUserList = t.getTaskUserList();			
			for (TaskUser tu : taskUserList) {
				new TaskUserDAO().delete(tu);
			}
			tDAO.delete(t);
		}
		this.validResult("TASK", "Task_Delete.xml");
		this.validResult("TASKUSER", "Task_Delete.xml");
	}
	
	@Test
	public void testTaskByProject (){
		List<Task> allTasksByProject = tDAO.getAllTasksByProject(1);
		for (Task task : allTasksByProject) {
			log.info(task.getName());
		}
	}
	
	@Test
	public void testChildTask (){
		List<Task> childTasks = tDAO.getChildTask(1, 2);
		for (Task task : childTasks) {
			log.info(task.getName());
		}
	}
}
