package ar.fi.uba.tempore.hibernate.dao;

import java.util.List;

import org.junit.Test;

import ar.fi.uba.tempore.hibernate.TestDAO;

import fi.uba.tempore.poc.entities.Project;
import fi.uba.tempore.poc.entities.Task;
import fi.uba.tempore.poc.entities.TaskState;
import fi.uba.tempore.poc.entities.TaskType;

public class TestTaskDAO extends TestDAO{

	private TaskDAO tDAO = new TaskDAO();
	private Task t = null;

	@Test
	public void testMakePersistent() {
		t = new Task();
		t.setName("TareaTest");
		t.setDescription("Tarea para realizar Test");
		TaskState ts = new TaskStateDAO().findById(1);
		t.setTaskState(ts);		
		TaskType tt = new TaskTypeDAO().findById(1);
		t.setTaskType(tt);
		Project p = new ProjectDAO().findById(1);
		t.setProject(p);

		Task makePersistent = tDAO.makePersistent(t);
		log.info("Elemento Guardado: " + makePersistent.getId());
	}

	@Test
	public void testFindById() {
		Task findById = tDAO.findById(1);
		log.info("Elemento encontrado: " + findById.getName() + ", " + findById.getDescription());
	}

	@Test
	public void testFindAll() {
		List<Task> list = tDAO.findAll();
		int i = 0;
		log.info("Todos los elementos");
		for (Task ts : list) {
			log.info((i++) + ") " + ts.getId() + ", " + ts.getName() + ", " + ts.getDescription());
		}
	}

	@Test
	public void testFindByExampleAndDelete() {
		Task t = new Task();
		t.setName("TareaTest");
		
		List<Task> tsList = tDAO.findByExample(t);
		int i= 0;
		for (Task tt : tsList){
			log.info((i++)+") Borrando id= " + tt.getId() + ", " + tt.getName() + ", " + tt.getDescription());
			tDAO.delete(tt);
		}
	}

}
