package ar.fi.uba.tempore.hibernate.dao;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import junit.framework.Assert;

import org.hibernate.ObjectNotFoundException;
import org.junit.Test;

import ar.fi.uba.tempore.dao.TaskDAO;
import ar.fi.uba.tempore.dao.TaskUserDAO;
import ar.fi.uba.tempore.dao.UserDAO;
import ar.fi.uba.tempore.entity.Task;
import ar.fi.uba.tempore.entity.TaskUser;
import ar.fi.uba.tempore.entity.User;
import ar.fi.uba.tempore.hibernate.TestDAO;

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
		this.validResult("TASKUSER", "TaskUser_New.xml");
	}
	
	@Test
	public void testUpdate(){
		TaskUser expected = tuDAO.findById(1);
		expected.setComment("comentario de la tarea");
		expected.setHourCount(9L);
		try {
			expected.setDate(new SimpleDateFormat("dd-MM-yyyy").parse("18-09-2011"));
		} catch (ParseException e) {			
			e.printStackTrace();
			Assert.fail();
		}
		Task task = new TaskDAO().findById(1);
		expected.setTask(task);
		User user = new UserDAO().findById(11);
		expected.setUser(user);
		
		tuDAO.makePersistent(expected);
		
		this.validResult("TASKUSER", "TaskUser_Update.xml");
	}
	
	private TaskUser getDemoTaskUser(){
		TaskUser u = new TaskUser();
		u.setComment("comentario de la tarea");
		u.setHourCount(9L);
		try {
			u.setDate(new SimpleDateFormat("dd-MM-yyyy").parse("18-09-2011"));
		} catch (ParseException e) {			
			e.printStackTrace();
			Assert.fail();
		}
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
			tuDAO.delete(ct);
		}
		this.validResult("TASKUSER", "TaskUser_Delete.xml");
	}
}
