package ar.fi.uba.tempore.hibernate.dao;


import java.util.List;

import junit.framework.Assert;

import org.hibernate.ObjectNotFoundException;
import org.junit.Test;

import ar.fi.uba.tempore.hibernate.TestDAO;
import fi.uba.tempore.poc.entities.Alert;
import fi.uba.tempore.poc.entities.Role;
import fi.uba.tempore.poc.entities.TaskUser;
import fi.uba.tempore.poc.entities.User;
import fi.uba.tempore.poc.entities.UserProject;

public class TestUserDAO extends TestDAO{

	private UserDAO psDAO = new UserDAO();
	
	@Test
	public void testFindId() {
		User actual = null;
		try {	
			actual = psDAO.findById(11);
			Assert.assertEquals("No se encontro al tipo de contacto", "Rana"  , actual.getName());

			List<TaskUser> tuList = actual.getTaskUserList();
			Assert.assertEquals("El tamaño de la lista asociada a la entidad no es correcto 2", 3, tuList.size());
			List<UserProject> pList = actual.getUserProjectList();
			Assert.assertEquals("El tamaño de la lista asociada a la entidad no es correcto 3", 1, pList.size());
			
		} catch (ObjectNotFoundException e){
			Assert.assertTrue("No se encontro la entidad", false);
		}
	}
	
	@Test
	public void testFindAll (){
		List<User> findAll = psDAO.findAll();
		Assert.assertEquals("La cantidad del metodo FIND-ALL es incorrecta", 6, findAll.size());
	}
	
	@Test
	public void testMakePersistence (){
		User newEntity = getDemoUser();						

		newEntity = psDAO.makePersistent(newEntity);		
		this.validResult("USER", "User_New.xml");
		this.validResult("PERSON", "User_New.xml");
	}
	
	@Test
	public void testUpdate(){
		User expected = psDAO.findById(11);
		expected.setName("JTest");
		expected.setLastName("JLastTest");
		expected.setAddress("JDirTest");
		expected.setCountry("JCountryTest");
		expected.setPhone("1111111111");
		expected.setEmail("mail@mail.com");
		expected.setState("A");
		expected.setZip("123");
		expected.setUserName("username");
		expected.setPassword("pass");
		
		psDAO.makePersistent(expected);
		this.validResult("USER", "User_Update.xml");
		this.validResult("PERSON", "User_Update.xml");
	}
	
	private User getDemoUser(){
		User u = new User();
		u.setName("JTest");
		u.setLastName("JLastTest");
		u.setAddress("JDirTest");
		u.setCountry("JCountryTest");
		u.setPhone("1111111111");
		u.setState("A");
		u.setZip("123");
		u.setEmail("mail@mail.com");
		u.setUserName("username");
		u.setPassword("pass");
		return u;
	}
	
	@Test
	public void testDelete (){
		User entity = new User();
		entity.setName("Rana");
				
		List<User> findByExample = psDAO.findByExample(entity);
		for (User u : findByExample){					
			
			//Elimino las horas cargadas
			List<TaskUser> taskUserList = u.getTaskUserList();
			for (TaskUser taskUser : taskUserList) {
				new TaskUserDAO().delete(taskUser);
			}
			
			//Elimino la asginacion a los proyectos
			List<UserProject> userProjectList = u.getUserProjectList();
			for (UserProject up : userProjectList) {
				List<Alert> alertList = up.getAlertList();
				for (Alert alert : alertList) {
					new AlertDAO().delete(alert);
				}
				
				List<Role> roleList = up.getRoleList();
				for (Role role : roleList) {
					List<UserProject> userProjectList2 = role.getUserProjectList();
					userProjectList2.remove(up);
					new RoleDAO().makePersistent(role);
				}
				
				new UserProjectDAO().delete(up);
			}
			psDAO.delete(u);
		}
		this.validResult("USER", "User_Delete.xml");
		this.validResult("PERSON", "User_Delete.xml");
		this.validResult("USERPROJECT", "User_Delete.xml");
		this.validResult("TASKUSER", "User_Delete.xml");
	}
}
