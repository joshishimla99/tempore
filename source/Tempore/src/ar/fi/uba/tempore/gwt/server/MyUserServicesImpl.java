package ar.fi.uba.tempore.gwt.server;

import org.apache.log4j.Logger;
import org.dozer.DozerBeanMapper;

import ar.fi.uba.tempore.dao.UserDAO;
import ar.fi.uba.tempore.dto.UserDTO;
import ar.fi.uba.tempore.entity.User;
import ar.fi.uba.tempore.gwt.client.MyUserServicesClient;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class MyUserServicesImpl extends RemoteServiceServlet implements MyUserServicesClient {

	private static final long serialVersionUID = 4157803440651897594L;
	private final Logger log = Logger.getLogger(this.getClass());
	private final DozerBeanMapper mapper = new DozerBeanMapper();
	
	@Override
	public UserDTO update(UserDTO userDTO) {
		log.info("UPDATE USER - " + userDTO.getUserName());
		UserDAO uDAO = new UserDAO();
		//seteo admin en No
		userDTO.setAdmin("N");
		User user = mapper.map(userDTO, User.class);
		User makePersistent = uDAO.makePersistent(user);
		UserDTO result = mapper.map(makePersistent, UserDTO.class);
		
		return result;
	}
}
