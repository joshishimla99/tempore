package ar.fi.uba.tempore.dao;

import java.util.List;

import ar.fi.uba.tempore.dao.util.GenericHibernateDAO;
import ar.fi.uba.tempore.entity.Task;
import ar.fi.uba.tempore.entity.TaskUser;

public class TaskUserDAO extends GenericHibernateDAO<TaskUser, Integer> {
	
	public int hoursChargedByTask(Integer idTask){
		int chargedHour=0;
		Task task = new Task();
		task.setId(idTask);
		TaskUser taskUser = new TaskUser();
		taskUser.setTask(task);
		List<TaskUser> findByExample = this.findByExample(taskUser);
		if (!findByExample.isEmpty()){
			for (TaskUser taskUserResult : findByExample) {
				chargedHour += taskUserResult.getHourCount();
			}
		}
		return chargedHour;
	}

}
