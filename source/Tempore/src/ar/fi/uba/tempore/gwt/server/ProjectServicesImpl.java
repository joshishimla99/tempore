package ar.fi.uba.tempore.gwt.server;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.dozer.DozerBeanMapper;

import ar.fi.uba.tempore.dao.ProjectDAO;
import ar.fi.uba.tempore.dao.ProjectStateDAO;
import ar.fi.uba.tempore.dao.UserDAO;
import ar.fi.uba.tempore.dto.ProjectDTO;
import ar.fi.uba.tempore.dto.ProjectStateDTO;
import ar.fi.uba.tempore.entity.Project;
import ar.fi.uba.tempore.entity.ProjectState;
import ar.fi.uba.tempore.entity.User;
import ar.fi.uba.tempore.entity.UserProject;
import ar.fi.uba.tempore.gwt.client.ProjectServicesClient;
import ar.fi.uba.tempore.gwt.client.login.SessionUser;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ProjectServicesImpl extends RemoteServiceServlet implements ProjectServicesClient {	
	private static final long serialVersionUID = -6786157718346471647L;
	private final Logger log = Logger.getLogger(this.getClass());
	
	private final ProjectDAO projectDAO = new ProjectDAO();
	private final ProjectStateDAO projectStateDAO = new ProjectStateDAO();
	private final UserDAO userDAO = new UserDAO();
	private final DozerBeanMapper mapper = new DozerBeanMapper();
		

	@Override
	public ProjectDTO getProjectById(Integer id) {
		Project findById = projectDAO.findById(id);
		ProjectDTO dto = mapper.map(findById, ProjectDTO.class);
		return dto;
	}

	@Override
	public List<ProjectDTO> fetch(Integer userId) {
		log.info("FETCH - Proyectos ");
		List<ProjectDTO> list = new ArrayList<ProjectDTO>();
		
		List<Project> projects = projectDAO.getProjectsByUser(userId);		
		for (Project p : projects) {
			ProjectDTO pDTO = mapper.map(p, ProjectDTO.class);
			pDTO.setProjectState(mapper.map(p.getProjectState(), ProjectStateDTO.class));
			list.add(pDTO);
		}
		
		return list;
	}

	@Override
	public ProjectDTO add(ProjectDTO projectDTO) {
		log.info("NEW - Proyectos");
		Project p = mapper.map(projectDTO, Project.class);
		
		//Agrego el usuario creador
		List<UserProject> userProjectList = new ArrayList<UserProject>();
		User user = userDAO.findById(SessionUser.getInstance().getUser().getId());				
		UserProject owner = new UserProject();
		owner.setUser(user);
		owner.setOwner(1);
		userProjectList.add(owner);
		p.setUserProjectList(userProjectList);
		
		//estado del Proyecto
		ProjectState state = projectStateDAO.findById(projectDTO.getProjectState().getId());
		p.setProjectState(state);
		
		//TODO clientes del proyecto
		
		//Guardo el proyecto
		Project pSaved = projectDAO.makePersistent(p);
		ProjectDTO pSavedDTO = mapper.map(pSaved, ProjectDTO.class);
		
		return pSavedDTO;
	}

	@Override
	public ProjectDTO update(ProjectDTO projectDTO) {
		log.info("UPDATE - Proyectos");
		Project p = mapper.map(projectDTO, Project.class);
		
		//estado del Proyecto
		ProjectState state = projectStateDAO.findById(projectDTO.getProjectState().getId());
		p.setProjectState(state);
		
		//TODO clientes del proyecto
		
		Project pSaved = projectDAO.makePersistent(p);
		ProjectDTO pSavedDTO = mapper.map(pSaved, ProjectDTO.class);
		return pSavedDTO;
	}

	@Override
	public void remove(ProjectDTO data) {
		// TODO Auto-generated method stub
	}
}
