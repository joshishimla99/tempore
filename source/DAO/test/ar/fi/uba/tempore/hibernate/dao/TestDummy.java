package ar.fi.uba.tempore.hibernate.dao;


import java.util.List;

import org.junit.Test;

import ar.fi.uba.tempore.dao.ProjectDAO;
import ar.fi.uba.tempore.dao.UserDAO;
import ar.fi.uba.tempore.dao.UserProjectDAO;
import ar.fi.uba.tempore.entity.Project;
import ar.fi.uba.tempore.entity.User;
import ar.fi.uba.tempore.entity.UserProject;
import ar.fi.uba.tempore.hibernate.TestDAO;

public class TestDummy extends TestDAO{

	private UserProjectDAO upDAO = new UserProjectDAO();
	private UserDAO uDAO = new UserDAO();
	private ProjectDAO pDAO = new ProjectDAO();
	
	@Test
	public void testGetUserNotAssignedToProject() {
		List<User> userNotAssignedToProject = uDAO.getUserNotAssignedToProject(1);
		for (User user : userNotAssignedToProject) {
			log.info("Usuario no asignado: " + user.getName()); 
		}
	}
	
	@Test
	public void testGetUserAssignedToProject() {
		List<UserProject> userAssignedToProject = upDAO.getUserAssignedToProject(1);
		for (UserProject user : userAssignedToProject) {
			log.info("Usuario asignado: " + user.getUser().getName()); 
		}
	}
	
	@Test
	public void testProjectOwner (){
		List<Project> projectsByUser = pDAO.getProjectsByUser(1);
		for (Project project : projectsByUser) {
			log.info("Proyecto Owner: " + project.getUserProjectList().get(0).getOwner());
		}
	}
		
}
