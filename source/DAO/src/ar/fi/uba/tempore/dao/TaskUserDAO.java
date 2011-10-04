package ar.fi.uba.tempore.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;

import ar.fi.uba.tempore.dao.util.GenericHibernateDAO;
import ar.fi.uba.tempore.entity.Task;
import ar.fi.uba.tempore.entity.TaskUser;

public class TaskUserDAO extends GenericHibernateDAO<TaskUser, Integer> {

	private Logger log = Logger.getLogger(TaskUserDAO.class); 
	public long hoursChargedByTask(Integer idTask) {
		long chargedHour = 0;
//		Task task = new Task();
//		task.setId(idTask);
//		TaskUser taskUser = new TaskUser();
//		taskUser.setTask(task);
//		List<TaskUser> findByExample = this.findByExample(taskUser);
//		if (!findByExample.isEmpty()) {
//			for (TaskUser taskUserResult : findByExample) {
//				chargedHour += taskUserResult.getHourCount();
//			}
//		}
//
		String hql = "select sum(tu.hourCount) from TaskUser as tu inner join tu.task as t where t.id=" + idTask;

		Query createQuery = this.getSession().createQuery(hql);
		@SuppressWarnings("unchecked")
		List<Long> list = createQuery.list();
		if (!list.isEmpty()) {
			for (Long taskUserResult : list) {
				if(taskUserResult != null){
					log.error("entro en el servicio");
					chargedHour += taskUserResult.longValue();
				}
			}
		}
		return chargedHour;
	}

}
