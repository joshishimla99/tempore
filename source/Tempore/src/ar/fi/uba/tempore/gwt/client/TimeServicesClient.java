package ar.fi.uba.tempore.gwt.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("TimeServicesClient")
public interface TimeServicesClient extends RemoteService {
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
