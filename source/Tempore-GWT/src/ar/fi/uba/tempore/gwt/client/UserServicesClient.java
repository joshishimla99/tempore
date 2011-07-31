package ar.fi.uba.tempore.gwt.client;
import java.util.List;

import ar.fi.uba.tempore.dto.UserDTO;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("user")
public interface UserServicesClient extends RemoteService {

	List<UserDTO> getUsers() throws IllegalArgumentException;
}





