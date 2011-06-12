package ar.fi.uba.tempore.hibernate.dao;


import java.util.List;

import junit.framework.Assert;

import org.hibernate.ObjectNotFoundException;
import org.junit.Test;

import ar.fi.uba.tempore.hibernate.TestDAO;
import fi.uba.tempore.poc.entities.Privilege;
import fi.uba.tempore.poc.entities.Role;
import fi.uba.tempore.poc.entities.UserProject;

public class TestRoleDAO extends TestDAO{

	private RoleDAO pDAO = new RoleDAO();
	
	@Test
	public void testFindId() {
		Role actual = null;
		try {	
			actual = pDAO.findById(1);
			Assert.assertEquals("No se encontro al tipo de contacto", "Administrador"  , actual.getName());

			List<Privilege> list = actual.getPrivilegeList();
			Assert.assertEquals("El tamaño de la lista asociada a la entidad no es correcto", 3, list.size());			
			
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
			
			//Elimino la asociacion de los privilegios
			List<Privilege> privilegeList = r.getPrivilegeList();
			for (Privilege p : privilegeList) {
				p.getRoleList().remove(r);
				new PrivilegeDAO().makePersistent(p);
			}
			
			List<UserProject> userProjectList = r.getUserProjectList();
			for (UserProject u : userProjectList) {
				u.getRoleList().remove(r);
				new UserProjectDAO().makePersistent(u);
			}
			
			pDAO.delete(r);			
		}
		this.validResult("ROLE", "Role_Delete.xml");
		this.validResult("PRIVILEGEROLE", "Role_Delete.xml");
		this.validResult("ROLEUSERPROJECT", "Role_Delete.xml");
	}
}
