package ar.fi.uba.tempore.gwt.client;

import java.util.List;

import ar.fi.uba.tempore.dto.UserDTO;
import ar.fi.uba.tempore.dto.UserProjectDTO;
import ar.fi.uba.temporeutils.listgrid.filter.GenericGwtRpcDataSourceServiceFilterId;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("UserProjectServicesClient")
public interface UserProjectServicesClient extends GenericGwtRpcDataSourceServiceFilterId<Integer, UserProjectDTO> {
	/**
	 * Utility class for simplifying access to the instance of async service.
	 */
	public static class Util {
		private static UserProjectServicesClientAsync instance;
		public static UserProjectServicesClientAsync getInstance(){
			if (instance == null) {
				instance = GWT.create(UserProjectServicesClient.class);
			}
			return instance;
		}
	}
	
	public List<UserDTO> getUserNotAssignedToProject(Integer projectId);
}
