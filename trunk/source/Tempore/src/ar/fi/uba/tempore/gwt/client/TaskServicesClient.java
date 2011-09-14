package ar.fi.uba.tempore.gwt.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("TaskServicesClient")
public interface TaskServicesClient extends RemoteService {
	/**
	 * Utility class for simplifying access to the instance of async service.
	 */
	public static class Util {
		private static TaskServicesClientAsync instance;
		public static TaskServicesClientAsync getInstance(){
			if (instance == null) {
				instance = GWT.create(TaskServicesClient.class);
			}
			return instance;
		}
	}
}
