package ar.fi.uba.tempore.hibernate.dao;


import java.util.List;

import junit.framework.Assert;

import org.hibernate.ObjectNotFoundException;
import org.junit.Test;

import ar.fi.uba.tempore.dao.TaskDAO;
import ar.fi.uba.tempore.dao.TempCounterDAO;
import ar.fi.uba.tempore.entity.TempCounter;
import ar.fi.uba.tempore.hibernate.TestDAO;

public class TestTempCounterDAO extends TestDAO{

	private TempCounterDAO tcDAO = new TempCounterDAO();
	
	@Test
	public void testFindId() {
		TempCounter actual = null;
		try {	
			actual = tcDAO.findById(1);
			Assert.assertEquals("No se encontro al tipo de contacto", new Integer(1)  , actual.getUserId());
			
		} catch (ObjectNotFoundException e){
			Assert.assertTrue("No se encontro la entidad", false);
		}
	}
	
	@Test
	public void testFindAll (){
		List<TempCounter> findAll = tcDAO.findAll();
		Assert.assertEquals("La cantidad del metodo FIND-ALL es incorrecta", 1, findAll.size());
	}
	
	@Test
	public void testMakePersistence (){
		TempCounter newEntity = getDemoTemCounter();						

		newEntity = tcDAO.makePersistent(newEntity);		
		this.validResult("TEMPCOUNTER", "TempCounter_New.xml");
	}
	
	@Test
	public void testUpdate(){		
		TempCounter expected = tcDAO.findById(1);
		expected.setTimeAcumulated(50000L);
		expected.setControl(2);
		tcDAO.makePersistent(expected);
		
		this.validResult("TEMPCOUNTER", "TempCounter_Update.xml");
	}
	
	private TempCounter getDemoTemCounter(){
		TempCounter t = new TempCounter();
		t.setUserId(2);
		t.setTask(new TaskDAO().findById(2));
		t.setControl(0);
		t.setTimeIni(1234567890L);
		t.setTimeAcumulated(54321L);
		return t;
	}
	
	@Test
	public void testDelete (){
		TempCounter t = new TempCounter();
		t.setUserId(1);
		tcDAO.delete(t);
	
		this.validResult("TEMPCOUNTER", "TempCounter_Delete.xml");
	}
	
}
