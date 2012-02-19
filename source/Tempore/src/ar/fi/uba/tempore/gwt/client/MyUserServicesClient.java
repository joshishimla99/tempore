package ar.fi.uba.tempore.gwt.client;

import ar.fi.uba.tempore.dto.UserDTO;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("MyUserServicesClient")
public interface MyUserServicesClient extends RemoteService {
	/**
	 * Utility class for simplifying access to the instance of async service.
	 */
	public static class Util {
		private static MyUserServicesClientAsync instance;
		public static MyUserServicesClientAsync getInstance(){
			if (instance == null) {
				instance = GWT.create(MyUserServicesClient.class);
			}
			return instance;
		}
	}
	
	public UserDTO update(UserDTO userDTO);

}