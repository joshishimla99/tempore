package ar.fi.uba.tempore.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;

import ar.fi.uba.tempore.dao.util.GenericHibernateDAO;
import ar.fi.uba.tempore.entity.UserProject;

public class UserProjectDAO extends GenericHibernateDAO<UserProject, Integer> {

	/**
	 * Usuarios asignados al proyecto
	 * @param projectId Id del proyecto
	 * @return Listado de usuarios
	 */
	@SuppressWarnings("unchecked")
	public List<UserProject> getUserAssignedToProject(Integer projectId){		
		List<UserProject> list = null;
		if (projectId != null){
			String hql = "select up" +
					" from UserProject up " +
					" inner join fetch up.user u " +
					" where up.project.id = " + projectId;

			Query createQuery = this.getSession().createQuery(hql);
			list = createQuery.list();
		} else {
			list = new ArrayList<UserProject>();
		}
		return list;		
	}

	/**
	 * Remueve el owner que tenga el userio del proyecto, lo pasa a un usuario normal
	 * @param projectId: Id del proyecto que se quiere cambiar el owner
	 */
	public void removeActualOwner(Integer projectId) {
		String hql = "update UserProject up " +
						"set up.owner = 0 " +
						"where up.project.id = " + projectId;
		Query query = this.getSession().createQuery(hql);
        int rowCount = query.executeUpdate();	        
	}
}
