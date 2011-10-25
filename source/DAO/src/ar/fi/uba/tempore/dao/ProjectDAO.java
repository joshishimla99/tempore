package ar.fi.uba.tempore.dao;

import java.util.List;

import org.hibernate.Query;

import ar.fi.uba.tempore.dao.util.GenericHibernateDAO;
import ar.fi.uba.tempore.entity.Project;

public class ProjectDAO extends GenericHibernateDAO<Project, Integer> {
	
	public List<Project> getProjectsByUser (Integer userId){		
		String hql = "select distinct p " +
						"from Project as p " +
						"inner join fetch p.userProjectList as up " +
						"inner join fetch p.projectState as ps " +
						//"inner join fetch p.client as c " +
						"where up.user.id = " + userId;
		
		Query createQuery = this.getSession().createQuery(hql);
		
		@SuppressWarnings("unchecked")
		List<Project> list = createQuery.list();
		
		return list;
	}
}
