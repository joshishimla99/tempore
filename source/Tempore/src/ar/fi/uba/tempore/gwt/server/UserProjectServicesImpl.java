package ar.fi.uba.tempore.gwt.server;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.dozer.DozerBeanMapper;

import ar.fi.uba.tempore.dao.ProjectDAO;
import ar.fi.uba.tempore.dao.UserDAO;
import ar.fi.uba.tempore.dao.UserProjectDAO;
import ar.fi.uba.tempore.dto.UserDTO;
import ar.fi.uba.tempore.dto.UserProjectDTO;
import ar.fi.uba.tempore.entity.Project;
import ar.fi.uba.tempore.entity.User;
import ar.fi.uba.tempore.entity.UserProject;
import ar.fi.uba.tempore.gwt.client.UserProjectServicesClient;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class UserProjectServicesImpl extends RemoteServiceServlet implements UserProjectServicesClient {

	private static final long serialVersionUID = 3871015150494046391L;
	private final Logger log = Logger.getLogger(this.getClass());	
	private UserProjectDAO upDAO = new UserProjectDAO();
	private ProjectDAO pDAO = new ProjectDAO();
	private UserDAO uDAO = new UserDAO();
	private final DozerBeanMapper mapper = new DozerBeanMapper();
	
	@Override
	public List<UserProjectDTO> fetch(Integer projectId) {
		log.info("UserProject - FETCH id=" + projectId);
		List<UserProject> userAssignedToProject = upDAO.getUserAssignedToProject(projectId);

		List<UserProjectDTO> list = new ArrayList<UserProjectDTO>();
		for (UserProject userProject : userAssignedToProject) {
			UserProjectDTO map = mapper.map(userProject, UserProjectDTO.class);
			map.setUser(mapper.map(userProject.getUser(), UserDTO.class));
			list.add(map);
		}
		
		return list;
	}

	@Override
	public UserProjectDTO add(UserProjectDTO data) {
		log.info("UserProject - ADD DATA - " + data.getProject().getId() + ", " + data.getUser().getId());
		
		Project project = pDAO.findById(data.getProject().getId());
		User user = uDAO.findById(data.getUser().getId());
		
		UserProject up = new UserProject();
		up.setOwner(0);
		up.setProject(project);
		up.setUser(user);
		
		UserProject makePersistent = upDAO.makePersistent(up);
		UserProjectDTO dto = mapper.map(makePersistent, UserProjectDTO.class);
		return dto;
	}

	@Override
	public UserProjectDTO update(UserProjectDTO data) {
		log.info("UserProject - UPDATE DATA");
		//throw new UnsupportedOperationException("METODO SIN IMPLEMENTAR - UPDATE DATA");
		return null;
	}

	@Override
	public void remove(UserProjectDTO data) {
		log.info("UserProject - REMOVE DATA");
		UserProject up = upDAO.findById(data.getId());
		
		upDAO.delete(up);
	}
	
	
	/**
	 * Obtiene el listado de usauarios que no estan asignado al proyecto 
	 * @param projectId Id del proyecto a observar
	 * @return Lista de usuarios
	 */
	public List<UserDTO> getUserNotAssignedToProject(Integer projectId){
		log.info("GET USER NOT ASSIGNED TO PROJECT - id=" + projectId);
		List<User> userNotAssignedToProject = upDAO.getUserNotAssignedToProject(projectId);
		
		List<UserDTO> list = new ArrayList<UserDTO>();
		for (User user : userNotAssignedToProject) {
			UserDTO map = mapper.map(user, UserDTO.class);
			list.add(map);
		}
		return list;
	}
}
