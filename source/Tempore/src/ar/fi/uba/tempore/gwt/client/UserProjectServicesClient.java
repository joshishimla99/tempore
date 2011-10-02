package ar.fi.uba.tempore.gwt.client;

import ar.fi.uba.tempore.dto.UserProjectDTO;
import ar.fi.uba.temporeutils.listgrid.GenericGwtRpcDataSourceService;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("UserProjectServicesClient")
public interface UserProjectServicesClient extends GenericGwtRpcDataSourceService<UserProjectDTO> {
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
}
