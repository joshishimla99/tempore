package ar.fi.uba.tempore.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;

import ar.fi.uba.tempore.dao.util.GenericHibernateDAO;
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
//		String hql = "select sum(tu.hourCount) from TaskUser as tu inner join tu.task as t where t.id=" + idTask;
//
//		Query createQuery = this.getSession().createQuery(hql);
//		@SuppressWarnings("unchecked")
//		List<Long> list = createQuery.list();
//		if (!list.isEmpty()) {
//			for (Long taskUserResult : list) {
//				if(taskUserResult != null){
//					log.error("entro en el servicio");
//					chargedHour += taskUserResult.longValue();
//				}
//			}
//		}
		return chargedHour;
	}
	
	public Long getTotalTimeByTask(Integer  taskId){
		Long result = 0L;
		log.error("Obtengo el tiempo total para la tarea " + taskId);
		String hqln1 = "select tu from TaskUser tu where tu.task.id = " + taskId;		
				
		Query createQuery1 = this.getSession().createQuery(hqln1);
		@SuppressWarnings("unchecked")
		List<TaskUser> listN1 = createQuery1.list();
		for (TaskUser taskUser : listN1) {
			if (taskUser == null){
				log.error("taskUser es null");
			}
			if (taskUser.getHourCount() != null){
				result += taskUser.getHourCount();
			}
			
		}
		
		String hqln2 = "select tu from TaskUser tu inner join tu.task t where t.taskId = " + taskId;
		Query createQuery2 = this.getSession().createQuery(hqln2);
		@SuppressWarnings("unchecked")
		List<TaskUser> listN2 = createQuery2.list();
		for (TaskUser taskUser : listN2) {
			if (taskUser.getHourCount() != null){
				result += taskUser.getHourCount();
			}
		}
		String subQuery = "select t.id from Task as t where t.taskId=" + taskId;
		
		String hqln3 = "select tu from TaskUser tu inner join tu.task t where t.taskId in ("+subQuery+") " ;
		Query createQuery3 = this.getSession().createQuery(hqln3);
		@SuppressWarnings("unchecked")
		List<TaskUser> listN3 = createQuery3.list();
		for (TaskUser taskUser : listN3) {
			if (taskUser.getHourCount() != null){
				result += taskUser.getHourCount();
			}
		}
		
		return result;
	}

	//TODO Cambiar por el filtro correcto
	@SuppressWarnings("unchecked")
	public List<TaskUser> findByDate(Integer userId, Date dateFilter) {
		log.info("HorasDelUsuario - FETCH - " + dateFilter);
		List<TaskUser> list = null;
		
		if (dateFilter != null) {
//			dateFilter.setHours(0);
//			dateFilter.setMinutes(0);
//			dateFilter.setSeconds(0);
			
			String hql = 	"from TaskUser ut " +
							"where ut.date = :dateFilter " +
							"and " +
							"ut.user.id = :userId";
		
			Query query = this.getSession().createQuery(hql);
			query = query.setDate("dateFilter", dateFilter).setInteger("userId", userId);
			list = query.list();
		} else {
			list = new ArrayList<TaskUser>();
		}
		
		return list;
	}

}
