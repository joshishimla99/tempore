package ar.fi.uba.tempore.gwt.client;
import ar.fi.uba.tempore.dto.TaskTimeDTO;
import ar.fi.uba.temporeutils.listgrid.filter.GenericGwtRpcDataSourceServiceFilterId;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("TaskTimeServicesClient")
public interface TaskTimeServicesClient extends GenericGwtRpcDataSourceServiceFilterId<Integer, TaskTimeDTO>{
	/**
	 * Utility class for simplifying access to the instance of async service.
	 */
	public static class Util {
		private static TaskTimeServicesClientAsync instance;
		public static TaskTimeServicesClientAsync getInstance(){
			if (instance == null) {
				instance = GWT.create(TaskTimeServicesClient.class);
			}
			return instance;
		}
	}

}
