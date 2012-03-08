package ar.fi.uba.tempore.gwt.server;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.dozer.DozerBeanMapper;

import ar.fi.uba.tempore.dao.ProjectDAO;
import ar.fi.uba.tempore.dao.UserDAO;
import ar.fi.uba.tempore.dao.UserProjectDAO;
import ar.fi.uba.tempore.dto.ClientDTO;
import ar.fi.uba.tempore.dto.ProjectDTO;
import ar.fi.uba.tempore.dto.ProjectStateDTO;
import ar.fi.uba.tempore.dto.UserProjectDTO;
import ar.fi.uba.tempore.entity.Client;
import ar.fi.uba.tempore.entity.Project;
import ar.fi.uba.tempore.entity.ProjectState;
import ar.fi.uba.tempore.entity.User;
import ar.fi.uba.tempore.entity.UserProject;
import ar.fi.uba.tempore.gwt.client.ProjectServicesClient;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ProjectServicesImpl extends RemoteServiceServlet implements ProjectServicesClient {	
	private static final long serialVersionUID = -6786157718346471647L;
	
	private final Logger log = Logger.getLogger(this.getClass());
	private final DozerBeanMapper mapper = new DozerBeanMapper();

	@Override
	public ProjectDTO getProjectById(Integer id) {
		ProjectDAO pDAO = new ProjectDAO();
		Project findById = pDAO.findById(id);
		ProjectDTO dto = mapper.map(findById, ProjectDTO.class);		
		return dto;
	}

	@Override
	public List<ProjectDTO> fetch() {
		ProjectDAO pDAO = new ProjectDAO();
		log.info("FETCH ALL - Proyectos ");
		
		List<ProjectDTO> list = new ArrayList<ProjectDTO>();
		
		List<Project> projects = pDAO.findAll();
		for (Project p : projects) {
			ProjectDTO pDTO = mapper.map(p, ProjectDTO.class);
			pDTO.setProjectState(mapper.map(p.getProjectState(), ProjectStateDTO.class));
			pDTO.setClient(mapper.map(p.getClient(), ClientDTO.class));
			if (!p.getUserProjectList().isEmpty()){
				pDTO.setIsOwner(p.getUserProjectList().get(0).getOwner());
			} else {
				pDTO.setIsOwner(0);
			}
			list.add(pDTO);
		}
		
		return list;
	}
	
	
	@Override
	public List<ProjectDTO> fetch(Integer userId) {
		UserDAO uDAO = new UserDAO();
		User user = uDAO.findById(userId);
		
		List<ProjectDTO> list = new ArrayList<ProjectDTO>();
		if (user.getAdmin().equalsIgnoreCase("S")){
			list = fetch();
		} else {
			log.info("FETCH - Proyectos " + userId);
			ProjectDAO pDAO = new ProjectDAO();
		
			List<Project> projects = pDAO.getProjectsByUser(userId);
			 		
			for (Project p : projects) {
				ProjectDTO pDTO = mapper.map(p, ProjectDTO.class);
				pDTO.setProjectState(mapper.map(p.getProjectState(), ProjectStateDTO.class));
				pDTO.setClient(mapper.map(p.getClient(), ClientDTO.class));
				pDTO.setIsOwner(p.getUserProjectList().get(0).getOwner());
				list.add(pDTO);
			}
		}
		return list;
	}

	@Override
	public ProjectDTO add(ProjectDTO pDTO) {
		if (pDTO.getId() != null){
			//Solo tengo que actualizar
			return update(pDTO);
		}
		
		ProjectDAO pDAO = new ProjectDAO();
		UserProjectDAO upDAO = new UserProjectDAO();
		log.info("NEW - Proyectos");		

		
		//Guardo el proyecto
		Project p = mapper.map(pDTO, Project.class);
		p.setProjectState(new ProjectState(pDTO.getProjectState().getId()));
		
		Client c = new Client();
		c.setId(pDTO.getClient().getId());
		p.setClient(c);
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
		ProjectDAO pDAO = new ProjectDAO();
		log.info("UPDATE - Proyectos , state=" + projectDTO.getProjectState().getId());
		
		//Actualizo el proyecto
		Project p = mapper.map(projectDTO, Project.class);
		p.setProjectState(new ProjectState(projectDTO.getProjectState().getId()));
		Client c = new Client();
		c.setId(projectDTO.getClient().getId());
		p.setClient(c);
		Project pSaved = pDAO.makePersistent(p);
		
		//preparo la info para el retorno
		ProjectDTO pSavedDTO = mapper.map(pSaved, ProjectDTO.class);		
		pSavedDTO.setUserProjectList(projectDTO.getUserProjectList());
		return pSavedDTO;
	}

	@Override
	public void remove(ProjectDTO projectDTO) {
		ProjectDAO pDAO = new ProjectDAO();
		Project p = mapper.map(projectDTO, Project.class);
		pDAO.delete(p);
	}
}
