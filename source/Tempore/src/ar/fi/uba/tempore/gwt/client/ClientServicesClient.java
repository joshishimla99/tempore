package ar.fi.uba.tempore.gwt.client;
import ar.fi.uba.tempore.dto.ClientDTO;
import ar.fi.uba.temporeutils.listgrid.GenericGwtRpcDataSourceService;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("ClientServices")
public interface ClientServicesClient extends GenericGwtRpcDataSourceService<ClientDTO>{
	/**
	 * Utility class for simplifying access to the instance of async service.
	 */
	public static class Util {
		private static ClientServicesClientAsync instance;
		public static ClientServicesClientAsync getInstance(){
			if (instance == null) {
				instance = GWT.create(ClientServicesClient.class);
			}
			return instance;
		}
	}	
}
