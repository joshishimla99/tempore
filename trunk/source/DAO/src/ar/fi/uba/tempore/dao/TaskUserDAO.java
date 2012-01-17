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
	/**
	 * TODO revisar el synchronized por que no deberia ir
	 * Obtiene las horas cargadas por las tareas hijas y las subhijas.
	 * @param taskId Id de la tarea padre
	 * @return Acumulacion de las horas cargadas a todos sus herederos.
	 */
	public Long getTotalTimeByTask(Integer  taskId){
		Long result = 0L;
		log.info("Obtengo el tiempo total para la tarea " + taskId + " - " + this.getSession());
		
		log.info("Obteniendo las horas de las tarea con id = " + taskId);
		//Obtengo todas las horas cargadas a la tarea con id = taskId
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
		
		log.info("Acumulando las horas de las tarea con el id del Padre = " + taskId);
		//Obtengo todas las horas donde el PADRE de las tareas cargadas sea taskId (Horas de las tareas hijas)
		String hqln2 = "select tu from TaskUser tu inner join tu.task t where t.taskId = " + taskId;
		Query createQuery2 = this.getSession().createQuery(hqln2);
		@SuppressWarnings("unchecked")
		List<TaskUser> listN2 = createQuery2.list();
		for (TaskUser taskUser : listN2) {
			if (taskUser.getHourCount() != null){
				result += taskUser.getHourCount();
			}
		}
		
		
		//Obtendo el id de todas las tareas donde el pader es taskId (id de tareas hijas)
		String subQuery = "select t.id from Task as t where t.taskId=" + taskId;
		
		log.info("Acumulando las horas de las tarea nietas al taskId = " + taskId);
		//Obtengo las horas de todas las tareas donde el padre pertenesca a una de las hijas del taskId (Horas de las tareas nietas)
		String hqln3 = "select tu from TaskUser tu inner join tu.task t where t.taskId in ("+subQuery+") " ;
		Query createQuery3 = this.getSession().createQuery(hqln3);
		@SuppressWarnings("unchecked")
		List<TaskUser> listN3 = createQuery3.list();
		for (TaskUser taskUser : listN3) {
			if (taskUser.getHourCount() != null){
				result += taskUser.getHourCount();
			}
		}
		log.info("Total de horas acumuladas con sus subtareas para el id = " + taskId + ", es de " + result);
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
