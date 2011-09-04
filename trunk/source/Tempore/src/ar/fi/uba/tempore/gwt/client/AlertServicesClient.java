package ar.fi.uba.tempore.gwt.client;

import ar.fi.uba.tempore.dto.AlertDTO;
import ar.fi.uba.temporeutils.listgrid.GenericGwtRpcDataSourceService;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("AlertServicesClient")
public interface AlertServicesClient extends GenericGwtRpcDataSourceService<AlertDTO> {
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
}
