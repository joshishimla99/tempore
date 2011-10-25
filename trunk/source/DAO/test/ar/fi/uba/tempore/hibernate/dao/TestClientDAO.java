package ar.fi.uba.tempore.hibernate.dao;

import java.util.List;

import junit.framework.Assert;

import org.hibernate.ObjectNotFoundException;
import org.junit.Test;

import ar.fi.uba.tempore.dao.ClientDAO;
import ar.fi.uba.tempore.dao.UserDAO;
import ar.fi.uba.tempore.entity.Client;
import ar.fi.uba.tempore.entity.Project;
import ar.fi.uba.tempore.entity.User;
import ar.fi.uba.tempore.hibernate.TestDAO;

public class TestClientDAO extends TestDAO{
	private ClientDAO cDAO = new ClientDAO();	
	
	
	@Test
	public void testFindById() {
		Client actual = null;
		try {	
			actual = cDAO.findById(1);
			
			Assert.assertEquals("No se encontro al cliente", "TCS Tata Consulting Services"  , actual.getName());

			List<Project> actualProjectList = actual.getProjectList();
			Assert.assertEquals("La cantidad de proyectos del clientes no es correcta", 2, actualProjectList.size());
			
			List<User> actualContactList = actual.getUserList();
			Assert.assertEquals("La cantidad de usuarios del clientes no es correcta", 2, actualContactList.size());
			
		} catch (ObjectNotFoundException e){
			Assert.assertTrue("No se encontro la entidad", false);
		}
	}
	
	@Test
	public void testFindAll (){
		List<Client> findAll = cDAO.findAll();
		Assert.assertEquals("La cantidad del metodo FIND-ALL es incorrecta", 2, findAll.size());
	}

	@Test
	public void testMakePersistent() {
		Client clientExpected = getDemoClient(); 	
		cDAO.makePersistent(clientExpected);

		this.validResult("CLIENT", "Client_New.xml");
	}
	
	@Test
	public void testUpdate(){
		Client expected = cDAO.findById(1);

		expected.setName("updated name");
		expected.setAddress("Direccion del cliente");
		expected.setCountry("Pais del cliente");
		expected.setFiscalNumber("Fiscal Number del cliente");
		expected.setPhone("(+54-11) 4269-4564");
		expected.setZip("zip del cliente");
		expected.setState("Estado del cliente");
		
		cDAO.makePersistent(expected);
		
		this.validResult("CLIENT", "Client_Update.xml");
	}
	
	/**
	 * Devuelve una entidad para guardar y luego ser comparada con lo guardado
	 * @return Entidad demo
	 */
	private Client getDemoClient (){
		Client c = new Client();
		c.setName("Nombre del cliente");
		c.setAddress("Direccion del cliente");
		c.setCountry("Pais del cliente");
		c.setFiscalNumber("Fiscal Number del cliente");
		c.setPhone("(+54-11) 4269-4564");
		c.setZip("zip del cliente");
		c.setState("Estado del cliente");
		return c;
	}

	@Test
	public void testDelete() {
		Client actual = new Client();
		actual.setName("TCS Tata Consulting Services");
		
		List<Client> list = cDAO.findByExample(actual);				
		for (Client cl : list){
			
			List<User> userList = cl.getUserList();
			for (User user : userList) {
				user.getClientList().remove(cl);
				new UserDAO().makePersistent(user);
			}			
			
			cDAO.delete(cl);
			
		}	
		this.validResult("CLIENT", "Client_Delete.xml");
		this.validResult("USER", "Client_Delete.xml");
		this.validResult("CLIENTCONTACT", "Client_Delete.xml");
		this.validResult("PROJECT", "Client_Delete.xml");
		this.validResult("PROJECTCLIENT", "Client_Delete.xml");
		
	}
}
