package ar.fi.uba.tempore.gwt.client;

import java.util.List;

import ar.fi.uba.tempore.dto.AlertDTO;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("AlertServicesClient")
public interface AlertServicesClient extends RemoteService {
	/**
	 * Utility class for simplifying access to the instance of async service.
	 */
	public static class Util {
		private static AlertServicesClientAsync instance;
		public static AlertServicesClientAsync getInstance(){
			if (instance == null) {
				instance = GWT.create(AlertServicesClient.class);
			}
			return instance;
		}
	}
	
	public List<AlertDTO> getAlerts();
	public AlertDTO updateSaveAlert (AlertDTO alertDTO);
}
