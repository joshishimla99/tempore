package ar.fi.uba.tempore.hibernate.dao;


import java.util.List;

import junit.framework.Assert;

import org.hibernate.ObjectNotFoundException;
import org.junit.Test;

import ar.fi.uba.tempore.hibernate.TestDAO;
import fi.uba.tempore.poc.entities.Role;
import fi.uba.tempore.poc.entities.Privilege;

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
		Assert.assertNotNull("No se ha podido crear la entidad", newEntity);
		
		List<Privilege> allEntities = pDAO.findAll();
		Assert.assertEquals("La cantidad de la entidad no es correcta", 4, allEntities.size());

		try {
			Privilege expected = getDemoPrivilege();
			expected.setId(newEntity.getId());
			
			Privilege actual = pDAO.findById(newEntity.getId());
			Assert.assertEquals(expected.getName(), actual.getName());
		} catch (ObjectNotFoundException e){
			Assert.assertTrue("No se encontro la entidad creada", false);
		}
	}
	
	@Test
	public void testUpdate(){
		Privilege expected = pDAO.findById(1);
		expected.setName("update");
		
		pDAO.makePersistent(expected);
		
		Privilege actual = pDAO.findById(1);
		Assert.assertEquals("Ocurrio un error al actualizar", "update", actual.getName());
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
			Integer id = ct.getId();

			//Elimino la referencia del privilegio en el rol
			List<Role> roleList = ct.getRoleList();
			for (Role role : roleList) {
				List<Privilege> privilegeList = role.getPrivilegeList();
				privilegeList.remove(ct);				
				new RoleDAO().makePersistent(role);
			}
			
			pDAO.delete(ct);
			try {
				pDAO.findById(id);
				Assert.assertTrue("No se ha eliminado la entidad deseada", false);
			} catch (ObjectNotFoundException e){
				//No se encuentra la entidad
				Assert.assertTrue(true);
			}
		}
	}
}
