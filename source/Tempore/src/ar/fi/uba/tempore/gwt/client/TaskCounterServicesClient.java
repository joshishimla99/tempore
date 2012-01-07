package ar.fi.uba.tempore.gwt.client;
import ar.fi.uba.tempore.dto.TaskTimeDTO;
import ar.fi.uba.temporeutils.listgrid.filter.GenericGwtRpcDataSourceServiceFilterId;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("TaskCounterServicesClient")
public interface TaskCounterServicesClient extends GenericGwtRpcDataSourceServiceFilterId<Integer, TaskTimeDTO>{
	/**
	 * Utility class for simplifying access to the instance of async service.
	 */
	public static class Util {
		private static TaskCounterServicesClientAsync instance;
		public static TaskCounterServicesClientAsync getInstance(){
			if (instance == null) {
				instance = GWT.create(TaskCounterServicesClient.class);
			}
			return instance;
		}
	}

}
