package ar.fi.uba.tempore.gwt.client;

import java.util.List;

import ar.fi.uba.tempore.dto.UserDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface UserServicesClientAsync {

	void getUsers(AsyncCallback<List<UserDTO>> callback);

}
