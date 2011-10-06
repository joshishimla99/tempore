package ar.fi.uba.tempore.gwt.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import ar.fi.uba.tempore.dto.UserDTO;
import ar.fi.uba.tempore.dto.UserProjectDTO;
import ar.fi.uba.temporeutils.listgrid.filter.GenericGwtRpcDataSourceServiceFilterIdAsync;

public interface UserProjectServicesClientAsync extends GenericGwtRpcDataSourceServiceFilterIdAsync<Integer, UserProjectDTO>{

	void getUserNotAssignedToProject(Integer projectId,AsyncCallback<List<UserDTO>> callback);

}
