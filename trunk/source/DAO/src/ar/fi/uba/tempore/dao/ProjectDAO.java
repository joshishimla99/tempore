package ar.fi.uba.tempore.dao;

import java.util.List;

import org.hibernate.Query;

import ar.fi.uba.tempore.dao.util.GenericHibernateDAO;
import ar.fi.uba.tempore.entity.Project;
import ar.fi.uba.tempore.entity.ProjectState;

public class ProjectDAO extends GenericHibernateDAO<Project, Integer> {

	private ProjectStateDAO psDAO = new ProjectStateDAO();
	
	@Override
	public Project makePersistent(Project entity) {
		Integer stateId = entity.getProjectState().getId();
		if (stateId != null){
			ProjectState state = psDAO.findById(stateId);
			entity.setProjectState(state);
		}
		return super.makePersistent(entity);
	}
	
	public List<Project> getProjectsByUser (Integer userId){		
		String hql = "select distinct p " +
						"from Project as p " +
						"inner join fetch p.userProjectList as up " +
						"inner join fetch p.projectState as ps " +
						"where up.user.id = " + userId;
		
		Query createQuery = this.getSession().createQuery(hql);
		
		@SuppressWarnings("unchecked")
		List<Project> list = createQuery.list();
		
		return list;
	}
}
