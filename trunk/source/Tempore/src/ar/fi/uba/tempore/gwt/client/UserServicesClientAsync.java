package ar.fi.uba.tempore.gwt.client;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import ar.fi.uba.tempore.dto.UserDTO;
import ar.fi.uba.temporeutils.listgrid.GenericGwtRpcDataSourceServiceAsync;


public interface UserServicesClientAsync extends GenericGwtRpcDataSourceServiceAsync<UserDTO>{

	void validateUser(String userName, String password, AsyncCallback<UserDTO> callback);

	void getUserNotAssignedToProject(Integer projectId, AsyncCallback<List<UserDTO>> callback);

	void getUserLoggued(AsyncCallback<String> callback);

	void validateUserName(String userName, AsyncCallback<Boolean> callback);

	void recoveryUserPassword(String userName, String password, AsyncCallback<Void> callback);

	void generatePassword(String userName, AsyncCallback<String> callback);

}
