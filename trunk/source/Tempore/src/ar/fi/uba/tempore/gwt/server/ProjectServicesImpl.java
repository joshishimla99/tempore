package ar.fi.uba.tempore.gwt.server;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.dozer.DozerBeanMapper;

import ar.fi.uba.tempore.dao.ProjectDAO;
import ar.fi.uba.tempore.dao.ProjectStateDAO;
import ar.fi.uba.tempore.dao.UserProjectDAO;
import ar.fi.uba.tempore.dto.ProjectDTO;
import ar.fi.uba.tempore.dto.ProjectStateDTO;
import ar.fi.uba.tempore.dto.UserProjectDTO;
import ar.fi.uba.tempore.entity.Project;
import ar.fi.uba.tempore.entity.ProjectState;
import ar.fi.uba.tempore.entity.User;
import ar.fi.uba.tempore.entity.UserProject;
import ar.fi.uba.tempore.gwt.client.ProjectServicesClient;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ProjectServicesImpl extends RemoteServiceServlet implements ProjectServicesClient {	
	private static final long serialVersionUID = -6786157718346471647L;
	private final Logger log = Logger.getLogger(this.getClass());
	
	private final ProjectDAO pDAO = new ProjectDAO();
	private final ProjectStateDAO psDAO = new ProjectStateDAO();
	private final UserProjectDAO upDAO = new UserProjectDAO();
	private final DozerBeanMapper mapper = new DozerBeanMapper();
		

	/**
	 * Comentario para juan
	 */
	@Override
	public ProjectDTO getProjectById(Integer id) {
		Project findById = pDAO.findById(id);
		ProjectDTO dto = mapper.map(findById, ProjectDTO.class);		
		return dto;
	}

	@Override
	public List<ProjectDTO> fetch(Integer userId) {
		log.info("FETCH - Proyectos ");
		List<ProjectDTO> list = new ArrayList<ProjectDTO>();
		
		List<Project> projects = pDAO.getProjectsByUser(userId);		
		for (Project p : projects) {
			ProjectDTO pDTO = mapper.map(p, ProjectDTO.class);
			pDTO.setProjectState(mapper.map(p.getProjectState(), ProjectStateDTO.class));
			pDTO.setIsOwner(p.getUserProjectList().get(0).getOwner());
			list.add(pDTO);
		}
		
		return list;
	}

	@Override
	public ProjectDTO add(ProjectDTO pDTO) {
		log.info("NEW - Proyectos");

		//TODO Validar informacion
		
		//Guardo el proyecto
		Project p = mapper.map(pDTO, Project.class);
		p.setProjectState(new ProjectState(pDTO.getProjectState().getId()));
		Project pSaved = pDAO.makePersistent(p);

		//Guardo el usuario creador del proyecto
		UserProject up = new UserProject();
		up.setUser(new User(pDTO.getUserProjectList().get(0).getUser().getId()));
		up.setProject(pSaved);		
		up.setOwner(1);
		UserProject newUp = upDAO.makePersistent(up);
		

		//preparo la info para el retorno
		ProjectDTO pSavedDTO = mapper.map(pSaved, ProjectDTO.class);
		UserProjectDTO upDTO = mapper.map(newUp, UserProjectDTO.class);		
		List<UserProjectDTO> upList = new ArrayList<UserProjectDTO>();
		upList.add(upDTO);
		pSavedDTO.setUserProjectList(upList);
		
		return pSavedDTO;
	}

	@Override
	public ProjectDTO update(ProjectDTO projectDTO) {
		log.info("UPDATE - Proyectos");
		Project p = mapper.map(projectDTO, Project.class);
		
		//estado del Proyecto
		ProjectState state = psDAO.findById(projectDTO.getProjectState().getId());
		p.setProjectState(state);
		
		//TODO clientes del proyecto
		
		Project pSaved = pDAO.makePersistent(p);
		ProjectDTO pSavedDTO = mapper.map(pSaved, ProjectDTO.class);
		return pSavedDTO;
	}

	@Override
	public void remove(ProjectDTO data) {
		// TODO Auto-generated method stub
	}
}
