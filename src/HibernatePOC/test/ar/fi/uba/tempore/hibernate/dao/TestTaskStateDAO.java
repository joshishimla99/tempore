package ar.fi.uba.tempore.hibernate.dao;

import java.util.List;

import org.junit.Test;

import ar.fi.uba.tempore.hibernate.TestDAO;

import fi.uba.tempore.poc.entities.TaskState;

public class TestTaskStateDAO extends TestDAO{
	private TaskStateDAO tsDAO = new TaskStateDAO();	
	
	@Test
	public void testFindById() {
		try { 
			log.info("Buscar elemento con ID = 2");
			TaskState ent = tsDAO.findById(new Integer(2));
			if (ent.getId() != null){
				log.info("Elemento encontrado = " + ent.getId() + ", " + ent.getName() + ", " + ent.getDescription());
			} else {
				log.error("Elemento no encontrado");
			}
			
		} catch (Throwable ex) {
			ex.printStackTrace();
		}
	}
	
	@Test
	public void testFindAll() {
		List<TaskState> list = tsDAO.findAll();
		int i=0;
		log.info("Todos los elementos");
		for (TaskState ts : list){
			log.info((i++)+") " + ts.getId() + ", " + ts.getName() + ", " + ts.getDescription());
		}
	}

	@Test
	public void testSave() {
		try { 
			TaskState ts = new TaskState();
			ts.setDescription("Nuevo estato");
			ts.setName("Nuevo");
			
			log.info("Guardar un elemento");
			TaskState makePersistent = tsDAO.makePersistent(ts);
			log.info("Elemento nuevo insertado, ID: " + makePersistent.getId());			
			
		} catch (Throwable ex) {
			ex.printStackTrace();
		}
	}

	@Test
	public void testDelete(){
		TaskState tsFind = new TaskState();
		tsFind.setName("Nuevo");
		
		List<TaskState> tsList = tsDAO.findByExample(tsFind);
		int i= 0;
		for (TaskState ts : tsList){
			log.info((i++)+") Borrando id= " + ts.getId() + ", " + ts.getName() + ", " + ts.getDescription());
			tsDAO.delete(ts);
		}
	}
	
	
}
