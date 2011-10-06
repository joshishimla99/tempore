package ar.fi.uba.tempore.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;

import ar.fi.uba.tempore.dao.util.GenericHibernateDAO;
import ar.fi.uba.tempore.entity.User;
import ar.fi.uba.tempore.entity.UserProject;

public class UserProjectDAO extends GenericHibernateDAO<UserProject, Integer> {

	/**
	 * Usuarios NO asignados al proyecto
	 * @param projectId Id del proyecto
	 * @return Listado de usuarios
	 */
	@SuppressWarnings("unchecked")
	public List<User> getUserNotAssignedToProject (Integer projectId){
		List<User> list = null;
		if (projectId != null){
			String hql = "select u" +
					" from User u" +
					" where u.id not in (select up.user.id " +
					" from UserProject up " +
					" where up.project.id = " + projectId + 
					")";

			Query createQuery = this.getSession().createQuery(hql);
			list = createQuery.list();
		} else {
			list = new ArrayList<User>();
		}
		return list;		
	}

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
}
