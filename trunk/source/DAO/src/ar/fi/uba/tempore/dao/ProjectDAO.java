package ar.fi.uba.tempore.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;

import ar.fi.uba.tempore.dao.util.GenericHibernateDAO;
import ar.fi.uba.tempore.entity.Project;

public class ProjectDAO extends GenericHibernateDAO<Project, Integer> {
	
	@SuppressWarnings("unchecked")
	public List<Project> getProjectsByUser (Integer userId){	
		List<Project> list = null;
		
		if (userId != null){
			String hql = "select distinct p" +
							" from Project as p" +
							" inner join fetch p.userProjectList as up" +
							" inner join fetch p.projectState as ps" +
							" inner join fetch p.client as c" +
							" where up.user.id = " + userId + 
							" order by p.name";
			
			
			Query createQuery = this.getSession().createQuery(hql);
			list = createQuery.list();
		} else {
			list = new ArrayList<Project>();
		}
		
		return list;
	}
}
