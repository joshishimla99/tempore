package ar.fi.uba.tempore.gwt.client;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("ReportServices")
public interface ReportServicesClient {
	/**
	 * Utility class for simplifying access to the instance of async service.
	 */
	public static class Util {
		private static ReportServicesClientAsync instance;
		public static ReportServicesClientAsync getInstance(){
			if (instance == null) {
				instance = GWT.create(ReportServicesClient.class);
			}
			return instance;
		}
	}
	
	
}
