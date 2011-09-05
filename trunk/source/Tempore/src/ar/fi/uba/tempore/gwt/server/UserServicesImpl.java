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
	private final UserDAO uDAO = new UserDAO(); 

	@Override
	public List<UserDTO> fetch() {
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
		User user = mapper.map(userDTO, User.class);
		User newUser = uDAO.makePersistent(user);
		UserDTO newUserDto = mapper.map(newUser, UserDTO.class);
		return newUserDto;
	}

	@Override
	public void remove(UserDTO userDTO) {
		User user = mapper.map(userDTO, User.class);
		uDAO.delete(user);
	}
}
