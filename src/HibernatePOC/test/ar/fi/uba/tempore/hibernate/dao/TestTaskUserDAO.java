package ar.fi.uba.tempore.hibernate.dao;

import java.util.Date;
import java.util.List;

import org.junit.Test;

import ar.fi.uba.tempore.hibernate.TestDAO;
import fi.uba.tempore.poc.entities.Task;
import fi.uba.tempore.poc.entities.TaskUser;
import fi.uba.tempore.poc.entities.User;

public class TestTaskUserDAO extends TestDAO{
	private TaskUserDAO tuDAO = new TaskUserDAO();
	private TaskUser tu = null;
		
	@Test
	public void testFindById() {		
		tu = tuDAO.findById(1);
		log.info("TaskUser " + tu.getId() + ", " + tu.getTask().getName());		
	}

	@Test
	public void testMakePersistent() {
		tu = new TaskUser();
		tu.setHourCount(27817);
		tu.setDateHour(new Date());
		
		User u = new UserDAO().findById(1);
		Task t = new TaskDAO().findById(1);
		
		tu.setUser(u);
		tu.setTask(t);
		
		tu = tuDAO.makePersistent(tu);
		log.info("TaskUser Gueardado: ID = " + tu.getId());
	}

	@Test
	public void testDelete() {
		tu = new TaskUser();
		tu.setHourCount(27817);
		List<TaskUser> list = tuDAO.findByExample(tu);
		for (TaskUser cl : list){
			log.info("Borrando TaskUser : " + cl.getId());
			tuDAO.delete(cl);
		}
	}
}
