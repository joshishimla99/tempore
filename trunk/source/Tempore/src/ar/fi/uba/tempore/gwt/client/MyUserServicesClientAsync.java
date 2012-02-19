package ar.fi.uba.tempore.gwt.client;

import ar.fi.uba.tempore.dto.UserDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;


public interface MyUserServicesClientAsync {

	void update(UserDTO userDTO, AsyncCallback<UserDTO> callback);
	
}
