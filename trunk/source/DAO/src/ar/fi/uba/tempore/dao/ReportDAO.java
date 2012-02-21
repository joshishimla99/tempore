package ar.fi.uba.tempore.dao;


import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.classic.Session;

import ar.fi.uba.tempore.dao.util.HibernateUtil;
import ar.fi.uba.tempore.entity.reports.ProjectsTimes;
import ar.fi.uba.tempore.entity.reports.TaskTypesTimes;
import ar.fi.uba.tempore.entity.reports.TasksTimes;
import ar.fi.uba.tempore.entity.reports.TasksUsersTimes;
import ar.fi.uba.tempore.entity.reports.UsersTimes;

public class ReportDAO {

	private Session session;
	

	public ReportDAO() {

	}
	
	private Session getSession() {
		if (session == null || !session.isOpen()) {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
		}
		return session;
	}

	public void flush() {
		getSession().flush();
	}

	public void clear() {
		getSession().clear();
	}
	
	/**
	 * Cantidad de horas cargadas a cada uno de los proyectos.
	 * @param ini fecha desde
	 * @param end fecha hasta
	 * @return Listado de proyectos con las horas cargadas en el intervalo.
	 */
	@SuppressWarnings("unchecked")
	public List<ProjectsTimes> getProjectsTimes (Date ini, Date end){	
		List<ProjectsTimes> list = null;
		
		String hql = "select new ar.fi.uba.tempore.entity.reports.ProjectsTimes(p.name as name, sum(tu.hourCount) as total)" +
				"from Project as p " +
				"inner join p.taskList as t " +
				"left outer join t.taskUserList as tu " +
				"with (tu.date >= :iniDate " +
				"and tu.date <= :endDate) " +
				"group by p " +
				"order by total desc ";
		
		Query query = this.getSession().createQuery(hql);
		query = query.setDate("iniDate", ini).setDate("endDate", end);
		list = query.list();
		
		return list;
	}

	/**
	 * Cantidad de horas cargadas por cada usuario
	 * @param ini fecha de inicio 
	 * @param end fecha de fin
	 * @return Listado de usuarios con el contador de horas en el intervalo.
	 */
	@SuppressWarnings("unchecked")
	public List<UsersTimes> getUsersTimes (Integer projectId, Date ini, Date end){	
		List<UsersTimes> list = null;
		
		String hql = "select new ar.fi.uba.tempore.entity.reports.UsersTimes(u.name as name, u.lastName as lastName, u.userName as user , sum(tu.hourCount) as total)" +
				" from User as u " +
				" left outer join u.taskUserList as tu " +
				" with (tu.date >= :iniDate and tu.date <= :endDate) " +
				" inner join tu.task as t where t.project.id = :projectId" +
				" group by u " +
				" order by total desc";
		
		Query query = this.getSession().createQuery(hql);
		query = query.setDate("iniDate", ini).setDate("endDate", end).setInteger("projectId", projectId);
		list = query.list();
		
		return list;
	}
	
	/**
	 * Obtiene el tiempo cargado a cada tarea primaria en un intervalo de tiempo
	 * @param from fecha Desde 
	 * @param to fecha hasta
	 * @return Listado de tareas con sus horas cargadas
	 */
	@SuppressWarnings("unchecked")
	public List<TasksTimes> getPrimaryTaskTimes (Integer projectId ,Date from, Date to){	
		List<TasksTimes> list = null;
		
		String hql = "select new ar.fi.uba.tempore.entity.reports.TasksTimes(t.name as name, sum(tu.hourCount) as total)" +
				"from Project as p " +
				"inner join p.taskList as t " +
				"left outer join t.taskUserList as tu " +
				"with (tu.date >= :iniDate and tu.date <= :endDate) " + 
				"where p.id = :projectId and t.taskId is null " +
				"group by t " +
				"order by total desc";
		
		Query query = this.getSession().createQuery(hql);
		query = query.setInteger("projectId", projectId).setDate("iniDate", from).setDate("endDate", to);
		list = query.list();
		
		return list;
	}
	
	//TODO Reporte incompleto
	@SuppressWarnings("unchecked")
	public List<TasksUsersTimes> getPrimaryTaskTimesXUser (Date ini, Date end){	
		List<TasksUsersTimes> list = null;
		
		String hql = "select new ar.fi.uba.tempore.entity.reports.TasksUsersTimes(t.name as taskName, u.name as userName, u.lastName as userLastName, sum(tu.hourCount) as total)" +
				"from Task as t " +
				"inner join t.taskUserList as tu " +
				"with (tu.date >= :iniDate " +
				"and tu.date <= :endDate) " +
				"right outer join tu.user as u " +
				"where t.taskId is null " +
				"group by t, u " +
				"order by total desc";
		
		Query query = this.getSession().createQuery(hql);
		query = query.setDate("iniDate", ini).setDate("endDate", end);
		list = query.list();
		
		return list;
	}
	
	
	/**
	 * % de ocupacion del un Usuario en proyectos, dependiendo las horas cargadas a todos los mismos. Siempre en un intervalo de tiempo
	 * @param ini fecha desde
	 * @param end fecha hasta
	 * @return Listado horas cargadas por proyecto, dentro de un usuario.
	 */
	@SuppressWarnings("unchecked")
	public List<ProjectsTimes> getUserActivity (Integer userId, Date ini, Date end){	
		List<ProjectsTimes> list = null;
		
		String hql = "select new ar.fi.uba.tempore.entity.reports.ProjectsTimes(p.name as projectName, sum(tu.hourCount) as total)" +
				"from User as u " +
				"inner join u.taskUserList as tu " +
				"with (tu.date >= :iniDate " +
				"and tu.date <= :endDate) " +
				"inner join tu.task as t " +
				"inner join t.project as p " +
				"where u.id = :userId " + 
				"group by p " +
				"order by total desc";
		
		Query query = this.getSession().createQuery(hql);
		query = query.setInteger("userId", userId);
		query = query.setDate("iniDate", ini).setDate("endDate", end);
		list = query.list();
		
		return list;
	}

	/**
	 * Query para buscar las horas cargadas por un usuario en la semana. Con el objetivo de que sepa que dia le falta cargar
	 * @param userId Id del usuario
	 * @param weekDate un dia de la semana
	 * @return Mapa con el numero de dia de la semana y las horas cargadas en este
	 */
	@SuppressWarnings("unchecked")
	public List<TaskTypesTimes> getUserTimeByWeek (Integer userId, Date weekDate){
		
		Calendar c  = Calendar.getInstance();
		c.setTime(weekDate);
		c.add(Calendar.DAY_OF_YEAR, -c.get(Calendar.DAY_OF_WEEK)+1);
		Date iniDate = c.getTime();
		c.add(Calendar.DAY_OF_YEAR, 6);
		Date endDate = c.getTime();
		
		List<TaskTypesTimes> list = null;
		String hql = "select new ar.fi.uba.tempore.entity.reports.TaskTypesTimes(tu.date, sum(tu.hourCount) as total) " +
				" from TaskUser as tu " +
				" where (tu.date >= :iniDate " +
				" and tu.date <= :endDate) " +
				" and tu.user.id = :userId " + 
				" group by tu.date " +
				" order by tu.date";
		
		
		Query query = this.getSession().createQuery(hql);
		query = query.setInteger("userId", userId);
		query = query.setDate("iniDate", iniDate).setDate("endDate", endDate);
		list = query.list();
		
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<TaskTypesTimes> getProjectTaskTypeByTime (Integer projectId){	
		List<TaskTypesTimes> result = null;
		String hql = "select new ar.fi.uba.tempore.entity.reports.TaskTypesTimes(tt.name, tu.date, sum(tu.hourCount) as total) " +
				" from Task as t " +
				" inner join t.taskType as tt " +
				" inner join t.taskUserList as tu " +
				" where t.project.id = :projectId " + 
				" group by tu.date, tt.name " +
				" order by tu.date, tt.name";
		
		Query query = this.getSession().createQuery(hql);
		query = query.setInteger("projectId", projectId);
		result = query.list();
				
		return result;
	}

	
	
//	public static void main(String[] args) {
//		Transaction transaction = HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
//		Calendar day = Calendar.getInstance();
//		day.set(2012, 1, 20,0,0,0);
//		
//		ReportDAO reportDAO = new ReportDAO();
//		Map<Integer,TaskTypesTimes> map = reportDAO.getUserTimeByWeek(1, day.getTime());
//		Set<Integer> keySet = map.keySet();
//		for (Integer date : keySet) {
//			System.out.println(date +", " + map.get(date));
//		}
//		
//		
//		transaction.commit();
//	}
}
