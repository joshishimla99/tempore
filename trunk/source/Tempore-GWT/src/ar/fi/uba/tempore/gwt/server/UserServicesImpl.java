package ar.fi.uba.tempore.gwt.server;

import java.util.List;

import ar.fi.uba.tempore.dto.UserDTO;
import ar.fi.uba.tempore.service.UserServices;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class UserServicesImpl extends RemoteServiceServlet implements ar.fi.uba.tempore.gwt.client.UserServicesClient {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6786157718346471647L;

	@Override
	public List<UserDTO> getUsers() {
			return new UserServices().allUsers();
	}

}
