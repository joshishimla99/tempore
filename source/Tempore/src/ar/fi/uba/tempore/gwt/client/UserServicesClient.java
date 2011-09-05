package ar.fi.uba.tempore.gwt.client;
import ar.fi.uba.tempore.dto.UserDTO;
import ar.fi.uba.temporeutils.listgrid.GenericGwtRpcDataSourceService;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("UserServices")
public interface UserServicesClient extends GenericGwtRpcDataSourceService<UserDTO> {

	//List<UserDTO> getUsers() throws IllegalArgumentException;
}





