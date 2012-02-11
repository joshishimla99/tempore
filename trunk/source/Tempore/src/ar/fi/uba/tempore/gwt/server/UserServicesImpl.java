package ar.fi.uba.tempore.gwt.server;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.dozer.DozerBeanMapper;

import ar.fi.uba.tempore.dao.UserDAO;
import ar.fi.uba.tempore.dto.UserDTO;
import ar.fi.uba.tempore.entity.User;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class UserServicesImpl extends RemoteServiceServlet implements ar.fi.uba.tempore.gwt.client.UserServicesClient {
	
	private final Logger log = Logger.getLogger(this.getClass());
	
	private static final long serialVersionUID = -6786157718346471647L;
	private final DozerBeanMapper mapper = new DozerBeanMapper();

	
	public UserDTO validateUser (String userName, String password){
		UserDTO dto = null;

		UserDAO uDAO = new UserDAO(); 
		log.info("Users - VALIDATE USER");

		User user = uDAO.validateUser(userName, password);
		if (user != null && user.getId() != null) {
			dto = mapper.map(user, UserDTO.class);
			this.getThreadLocalRequest().setAttribute("username", userName);
		}
		
		return dto;
	}
	
	@Override
	public String getUserLoggued(){
		return (String) this.getThreadLocalRequest().getAttribute("username");
	}
	
	
	
	@Override
	public List<UserDTO> fetch() {
		UserDAO uDAO = new UserDAO(); 
		log.info("FETCH - Users");
		List<UserDTO> userList = new ArrayList<UserDTO>();
		
		List<User> users = uDAO.findAll();
		for (User u : users) {
			UserDTO uDTO = mapper.map(u, UserDTO.class);
			userList.add(uDTO);
		}
		return userList;
	}

	@Override
	public UserDTO add(UserDTO userDTO) {
		return update(userDTO);
	}

	@Override
	public UserDTO update(UserDTO userDTO) {
		UserDAO uDAO = new UserDAO(); 
		log.info("UPDATE - User");
		User user = mapper.map(userDTO, User.class);
		User newUser = uDAO.makePersistent(user);
		UserDTO newUserDto = mapper.map(newUser, UserDTO.class);
		return newUserDto;
	}

	@Override
	public void remove(UserDTO userDTO) {
		UserDAO uDAO = new UserDAO(); 
		log.info("REMOVE - User");
		User user = mapper.map(userDTO, User.class);
		uDAO.delete(user);
	}
	
	
	/**
	 * Obtiene el listado de usauarios que no estan asignado al proyecto 
	 * @param projectId Id del proyecto a observar
	 * @return Lista de usuarios
	 */
	public List<UserDTO> getUserNotAssignedToProject(Integer projectId){
		UserDAO uDAO = new UserDAO(); 
		log.info("GET USER NOT ASSIGNED TO PROJECT - id=" + projectId);
		List<User> userNotAssignedToProject = uDAO.getUserNotAssignedToProject(projectId);
		
		List<UserDTO> list = new ArrayList<UserDTO>();
		for (User user : userNotAssignedToProject) {
			UserDTO map = mapper.map(user, UserDTO.class);
			list.add(map);
		}
		return list;
	}
}
