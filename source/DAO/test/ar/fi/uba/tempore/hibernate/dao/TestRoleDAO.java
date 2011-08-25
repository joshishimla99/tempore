package ar.fi.uba.tempore.hibernate.dao;


import java.util.List;

import junit.framework.Assert;

import org.hibernate.ObjectNotFoundException;
import org.junit.Test;

import ar.fi.uba.tempore.dao.RoleDAO;
import ar.fi.uba.tempore.dao.UserProjectDAO;
import ar.fi.uba.tempore.entity.Role;
import ar.fi.uba.tempore.entity.UserProject;
import ar.fi.uba.tempore.hibernate.TestDAO;

public class TestRoleDAO extends TestDAO{

	private RoleDAO pDAO = new RoleDAO();
	
	@Test
	public void testFindId() {
		Role actual = null;
		try {	
			actual = pDAO.findById(1);
			Assert.assertEquals("No se encontro al tipo de contacto", "Administrador"  , actual.getName());
			
		} catch (ObjectNotFoundException e){
			Assert.assertTrue("No se encontro la entidad", false);
		}
	}
	
	@Test
	public void testFindAll (){
		List<Role> findAll = pDAO.findAll();
		Assert.assertEquals("La cantidad de contactos es incorrecta", 3, findAll.size());
	}
	
	@Test
	public void testMakePersistence (){
		Role newEntity = getDemoRole();						

		newEntity = pDAO.makePersistent(newEntity);		
		
		this.validResult("ROLE", "Role_New.xml");
	}
	
	@Test
	public void testUpdate(){
		Role expected = pDAO.findById(1);
		expected.setName("update");
		
		pDAO.makePersistent(expected);
		
		this.validResult("ROLE", "Role_Update.xml");
	}
	
	private Role getDemoRole(){
		Role ps = new Role();
		ps.setName("Super Administrador");
		return ps;
	}
	
	@Test
	public void testDelete (){
		Role entity = new Role();
		entity.setName("Usuario");
				
		List<Role> findByExample = pDAO.findByExample(entity);
		for (Role r : findByExample){			
			
			List<UserProject> userProjectList = r.getUserProjectList();
			for (UserProject u : userProjectList) {
				u.getRoleList().remove(r);
				new UserProjectDAO().makePersistent(u);
			}
			
			pDAO.delete(r);			
		}
		this.validResult("ROLE", "Role_Delete.xml");
		this.validResult("ROLEUSERPROJECT", "Role_Delete.xml");
	}
}
