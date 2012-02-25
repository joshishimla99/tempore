package ar.fi.uba.tempore.gwt.client;
import java.util.List;

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
	
	public UserDTO validateUser (String userName, String password);
	
	public String getUserLoggued();

	public List<UserDTO> getUserNotAssignedToProject(Integer projectId);
	
	public boolean validateUserName(String userName);
	
	public void recoveryUserPassword(String userName);

	public UserDTO updateNotAdmin(UserDTO userDTO);
//	public String generatePassword(String userName);
}





