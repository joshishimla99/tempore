package ar.fi.uba.tempore.gwt.client;
import ar.fi.uba.tempore.dto.ProjectDTO;
import ar.fi.uba.temporeutils.listgrid.filter.GenericGwtRpcDataSourceServiceFilterId;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("ProjectServices")
public interface ProjectServicesClient extends GenericGwtRpcDataSourceServiceFilterId<Integer, ProjectDTO> {

	public static class Util{
		private static ProjectServicesClientAsync instance = null;
		public static ProjectServicesClientAsync getInstance(){
			if (instance == null){
				instance = GWT.create(ProjectServicesClient.class);
			}
			return instance;
		}
	}
	
	ProjectDTO getProjectById(Integer id);
}





