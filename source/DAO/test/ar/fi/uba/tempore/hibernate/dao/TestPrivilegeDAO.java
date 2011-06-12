package ar.fi.uba.tempore.hibernate.dao;


import java.util.List;

import junit.framework.Assert;

import org.hibernate.ObjectNotFoundException;
import org.junit.Test;

import ar.fi.uba.tempore.dao.PrivilegeDAO;
import ar.fi.uba.tempore.dao.RoleDAO;
import ar.fi.uba.tempore.entity.Privilege;
import ar.fi.uba.tempore.entity.Role;
import ar.fi.uba.tempore.hibernate.TestDAO;

public class TestPrivilegeDAO extends TestDAO{

	private PrivilegeDAO pDAO = new PrivilegeDAO();
	
	@Test
	public void testFindId() {
		Privilege actual = null;
		try {	
			actual = pDAO.findById(1);
			Assert.assertEquals("No se encontro al tipo de contacto", "Administrar Usuarios"  , actual.getName());

			List<Role> list = actual.getRoleList();
			Assert.assertEquals("El tamaño de la lista asociada a la entidad no es correcto", 1, list.size());			
			
		} catch (ObjectNotFoundException e){
			Assert.assertTrue("No se encontro la entidad", false);
		}
	}
	
	@Test
	public void testFindAll (){
		List<Privilege> findAll = pDAO.findAll();
		Assert.assertEquals("La cantidad de contactos es incorrecta", 3, findAll.size());
	}
	
	@Test
	public void testMakePersistence (){
		Privilege newEntity = getDemoPrivilege();						

		newEntity = pDAO.makePersistent(newEntity);		
		
		this.validResult("PRIVILEGE", "Privilege_New.xml");
		this.validResult("PRIVILEGEROLE", "Privilege_New.xml");
	}
	
	@Test
	public void testUpdate(){
		Privilege expected = pDAO.findById(1);
		expected.setName("update");
		
		pDAO.makePersistent(expected);
		
		this.validResult("PRIVILEGE", "Privilege_Update.xml");
		this.validResult("PRIVILEGEROLE", "Privilege_New.xml");
	}
	
	private Privilege getDemoPrivilege(){
		Privilege ps = new Privilege();
		ps.setName("Eliminar Tarea de 1 nivel");
		return ps;
	}
	
	@Test
	public void testDelete (){
		Privilege entity = new Privilege();
		entity.setName("Mdoficar Horas");
				
		List<Privilege> findByExample = pDAO.findByExample(entity);
		for (Privilege ct : findByExample){			
			//Elimino la referencia del privilegio en el rol
			List<Role> roleList = ct.getRoleList();
			for (Role role : roleList) {
				List<Privilege> privilegeList = role.getPrivilegeList();
				privilegeList.remove(ct);				
				new RoleDAO().makePersistent(role);
			}
			
			pDAO.delete(ct);
		}
		this.validResult("PRIVILEGE", "Privilege_Delete.xml");
		this.validResult("PRIVILEGEROLE", "Privilege_Delete.xml");
		this.validResult("ROLE", "Privilege_Delete.xml");
	}
}
