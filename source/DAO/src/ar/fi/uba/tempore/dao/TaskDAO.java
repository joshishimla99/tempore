package ar.fi.uba.tempore.dao;

import java.util.List;

import org.hibernate.Query;

import ar.fi.uba.tempore.dao.util.GenericHibernateDAO;
import ar.fi.uba.tempore.entity.Task;

public class TaskDAO extends GenericHibernateDAO<Task, Integer> {

	/**
	 * Trae todas las tareas sin distincion del nivel de la misma
	 * @param projectId Id del propyecto que se esta buscando
	 * @return Lista de todas las tareas del proyecto
	 */
	public List<Task> getAllTasksByProject (Integer projectId){
		String hql = "select distinct t" +
				" from Task as t" +
				" inner join t.project as p" +	
				" inner join fetch t.taskType as tt" +
				" where " +
				" p.id = " + projectId;

		Query createQuery = this.getSession().createQuery(hql);
		@SuppressWarnings("unchecked")
		List<Task> list = createQuery.list();
		return list;
	}

	/**
	 * Obtiene las tareas por niveles de un proyecto dado. Si se le pasa el taskId en null trae las del primer nivel 
	 * @param projectId No puede ser nulo
	 * @param parentTaskId Null o un Id existente del padre
	 * @return Lista de tareas del mismo nivel
	 */
	public List<Task> getChildTask(Integer projectId, Integer parentTaskId) {

		String hql = "select distinct t" +
				" from Task as t" +
				" inner join t.project as p" +	
				" inner join fetch t.taskType as tt" +
				" where " + 
				" p.id = " + projectId + 
				" and t.taskId = " + parentTaskId;

		Query createQuery = this.getSession().createQuery(hql);
		@SuppressWarnings("unchecked")
		List<Task> list = createQuery.list();
		return list;
	}	
}
