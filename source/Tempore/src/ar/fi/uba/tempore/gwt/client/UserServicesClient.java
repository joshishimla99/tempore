package ar.fi.uba.tempore.gwt.client;
import ar.fi.uba.tempore.dto.UserDTO;
import ar.fi.uba.temporeutils.listgrid.GenericGwtRpcDataSourceService;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("UserServices")
public interface UserServicesClient extends GenericGwtRpcDataSourceService<UserDTO> {
	
	public static class Util{
		private static UserServicesClientAsync instance;
		public static UserServicesClientAsync getInstance(){
			if (instance == null){
				instance = GWT.create(UserServicesClient.class);
			}
			return instance;
		}
	}
}




