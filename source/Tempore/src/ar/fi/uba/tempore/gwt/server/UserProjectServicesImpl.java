package ar.fi.uba.tempore.gwt.server;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.dozer.DozerBeanMapper;

import ar.fi.uba.tempore.dao.AlertDAO;
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
	private final DozerBeanMapper mapper = new DozerBeanMapper();
	
	@Override
	public List<UserProjectDTO> fetch(Integer projectId) {
		UserProjectDAO upDAO = new UserProjectDAO();
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
		UserProjectDAO upDAO = new UserProjectDAO();
		
		log.info("UserProject - ADD DATA - " + data.getProject().getId() + ", " + data.getUser().getId());
		
		//Elimino id poque viene mal de la vista
		data.setId(null);

		//TODO validar si usuario que lo esta realizando es owner para poder realizar esta operacion
		
		UserProject up = mapper.map(data, UserProject.class);
		UserProject assignedUser = upDAO.makePersistent(up);
		UserProjectDTO dto = mapper.map(assignedUser, UserProjectDTO.class);

		UserDAO uDAO = new UserDAO();
		User user = uDAO.findById(data.getUser().getId());
		dto.setUser(mapper.map(user, UserDTO.class));
		return dto;
	}

	@Override
	public UserProjectDTO update(UserProjectDTO data) {
		log.info("UserProject - UPDATE DATA");
		//throw new UnsupportedOperationException("METODO SIN IMPLEMENTAR - UPDATE DATA");
		return null;
	}

	@Override
	public void remove(UserProjectDTO userProject) {
		log.info("UserProject - REMOVE DATA");

		if (userProject.getOwner()!=null && userProject.getOwner()==1){
			log.info("Usuario due�o, no desasignar");
			throw new NullPointerException("El usuario due�o no puede ser desasignado del proyecto");
		} else {
			//TODO Validar que el usuario que realiza la operacion es el owner del proyecto
			AlertDAO aDAO = new AlertDAO();
			aDAO.deleteAllUserAlert(userProject.getId());
	
			UserProjectDAO upDAO = new UserProjectDAO();
			UserProject up = upDAO.findById(userProject.getId());
			
			upDAO.delete(up);
		}
	}
	
	
	@Override
	public Integer changeOwner(UserProjectDTO dto) {
		UserProjectDAO upDAO = new UserProjectDAO();
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
		
		return p.getId();
	}
}
