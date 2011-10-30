package ar.fi.uba.tempore.gwt.server;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.dozer.DozerBeanMapper;

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
		log.info("### " + userAssignedToProject.size());
		return list;
	}

	@Override
	public UserProjectDTO add(UserProjectDTO data) {
		log.info("UserProject - ADD DATA - " + data.getProject().getId() + ", " + data.getUser().getId());

		//TODO validar si usuario que lo esta realizando es owner para poder realizar esta operacion
		
		UserProject up = mapper.map(data, UserProject.class);
		UserProject makePersistent = upDAO.makePersistent(up);
		UserProjectDTO dto = mapper.map(makePersistent, UserProjectDTO.class);
		dto.setUser(mapper.map(makePersistent.getUser(), UserDTO.class));
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
	
	
	@Override
	public void changeOwner(UserProjectDTO dto) {
		log.info("CHANGE OWNER");
		
		log.info("CLEAR OWNER - proyecto: " + dto.getProject().getId());
		upDAO.removeActualOwner(dto.getProject().getId());
		
		UserProject up = mapper.map(dto, UserProject.class);
		up.setOwner(1);
		User u = mapper.map(dto.getUser(), User.class);
		Project p = mapper.map(dto.getProject(), Project.class);
		log.info("SET OWNER - Usuario: " + u.getId() + ", Project: " + p.getId() + ", UP id: " + up.getId());
		up.setUser(u);
		up.setProject(p);
		upDAO.makePersistent(up);
	}
}
