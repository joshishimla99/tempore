package ar.fi.uba.tempore.hibernate.dao;


import java.util.List;

import junit.framework.Assert;

import org.hibernate.ObjectNotFoundException;
import org.junit.Test;

import ar.fi.uba.tempore.hibernate.TestDAO;
import fi.uba.tempore.poc.entities.Privilege;
import fi.uba.tempore.poc.entities.Role;

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
		Assert.assertNotNull("No se ha podido crear la entidad", newEntity);
		
		List<Role> allEntities = pDAO.findAll();
		Assert.assertEquals("La cantidad de la entidad no es correcta", 4, allEntities.size());

		try {
			Role expected = getDemoRole();
			expected.setId(newEntity.getId());
			
			Role actual = pDAO.findById(newEntity.getId());
			Assert.assertEquals(expected.getName(), actual.getName());
		} catch (ObjectNotFoundException e){
			Assert.assertTrue("No se encontro la entidad creada", false);
		}
	}
	
	@Test
	public void testUpdate(){
		Role expected = pDAO.findById(1);
		expected.setName("update");
		
		pDAO.makePersistent(expected);
		
		Role actual = pDAO.findById(1);
		Assert.assertEquals("Ocurrio un error al actualizar", "update", actual.getName());
	}
	
	private Role getDemoRole(){
		Role ps = new Role();
		ps.setName("Super Administrador");
		return ps;
	}
	
/*	@Test
	public void testDelete (){
		Role entity = new Role();
		entity.setName("Usuario");
				
		List<Role> findByExample = pDAO.findByExample(entity);
		for (Role ct : findByExample){			
		
			Integer id = ct.getId();
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
*/
}
