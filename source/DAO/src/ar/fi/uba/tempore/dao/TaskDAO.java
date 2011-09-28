package ar.fi.uba.tempore.dao;

import java.util.ArrayList;
import java.util.List;

import ar.fi.uba.tempore.dao.util.GenericHibernateDAO;
import ar.fi.uba.tempore.entity.Task;

public class TaskDAO extends GenericHibernateDAO<Task, Integer> {

	public List<Task> getChildTask(Integer idTask) {
		// TODO Auto-generated method stub
		List<Task> taskList= new ArrayList<Task>();
		return taskList;
	}
	
}
