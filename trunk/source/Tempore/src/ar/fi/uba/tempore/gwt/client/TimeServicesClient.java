package ar.fi.uba.tempore.gwt.client;
import ar.fi.uba.tempore.dto.TaskUserDTO;
import ar.fi.uba.temporeutils.listgrid.GenericGwtRpcDataSourceService;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("TimeServicesClient")
public interface TimeServicesClient extends GenericGwtRpcDataSourceService<TaskUserDTO>{
	/**
	 * Utility class for simplifying access to the instance of async service.
	 */
	public static class Util {
		private static TimeServicesClientAsync instance;
		public static TimeServicesClientAsync getInstance(){
			if (instance == null) {
				instance = GWT.create(TimeServicesClient.class);
			}
			return instance;
		}
	}

}
