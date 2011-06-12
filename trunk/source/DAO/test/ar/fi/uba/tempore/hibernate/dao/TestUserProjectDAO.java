package ar.fi.uba.tempore.hibernate.dao;


import java.util.List;

import junit.framework.Assert;

import org.hibernate.ObjectNotFoundException;
import org.junit.Test;

import ar.fi.uba.tempore.dao.AlertDAO;
import ar.fi.uba.tempore.dao.ProjectDAO;
import ar.fi.uba.tempore.dao.RoleDAO;
import ar.fi.uba.tempore.dao.UserDAO;
import ar.fi.uba.tempore.dao.UserProjectDAO;
import ar.fi.uba.tempore.entity.Alert;
import ar.fi.uba.tempore.entity.Role;
import ar.fi.uba.tempore.entity.User;
import ar.fi.uba.tempore.entity.UserProject;
import ar.fi.uba.tempore.hibernate.TestDAO;

public class TestUserProjectDAO extends TestDAO{

	private UserProjectDAO tuDAO = new UserProjectDAO();
	
	@Test
	public void testFindId() {
		UserProject actual = null;
		try {	
			actual = tuDAO.findById(1);
			Assert.assertEquals("No se encontro al tipo de contacto", "Rana", actual.getUser().getName());
			Assert.assertEquals("No se encontro al tipo de contacto", "Proyecto 1", actual.getProject().getName());
			
		} catch (ObjectNotFoundException e){
			Assert.assertTrue("No se encontro la entidad", false);
		}
	}
	
	@Test
	public void testFindAll (){
		List<UserProject> findAll = tuDAO.findAll();
		Assert.assertEquals("La cantidad del metodo FIND-ALL es incorrecta", 4, findAll.size());
	}
	
	@Test
	public void testMakePersistence (){
		UserProject newEntity = getDemoUserProject();						

		newEntity = tuDAO.makePersistent(newEntity);		
		this.validResult("USERPROJECT", "UserProject_New.xml");
	}
	
	@Test
	public void testUpdate(){
		UserProject expected = tuDAO.findById(1);
		expected.setUser(new UserDAO().findById(13));
		
		tuDAO.makePersistent(expected);
		this.validResult("USERPROJECT", "UserProject_Update.xml");
	}
	
	private UserProject getDemoUserProject(){
		UserProject u = new UserProject();		
		u.setProject(new ProjectDAO().findById(1));
		u.setUser(new UserDAO().findById(13));
		return u;
	}
	
	@Test
	public void testDelete (){
		User user = new UserDAO().findById(11);
		
		List<UserProject> findByExample = user.getUserProjectList();
		Assert.assertEquals(1, findByExample.size());
		for (UserProject ct : findByExample){
			
			List<Alert> alertList = ct.getAlertList();
			for (Alert alert : alertList) {
				new AlertDAO().delete(alert);
			}
			
			List<Role> roleList = ct.getRoleList();
			for (Role role : roleList) {
				List<UserProject> userProjectList = role.getUserProjectList();
				userProjectList.remove(ct);
				new RoleDAO().makePersistent(role);
			}
			
			
			tuDAO.delete(ct);		
		}
		this.validResult("USERPROJECT", "UserProject_Delete.xml");
		this.validResult("ROLEUSERPROJECT", "UserProject_Delete.xml");
		this.validResult("ALERT", "UserProject_Delete.xml");
	}
}
