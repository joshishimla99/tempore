package ar.fi.uba.tempore.dao;

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
}
