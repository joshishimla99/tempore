package ar.fi.uba.tempore.gwt.client;
import java.util.List;

import ar.fi.uba.tempore.dto.ProjectDTO;
import ar.fi.uba.temporeutils.listgrid.GenericGwtRpcDataSourceService;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("ProjectServices")
public interface ProjectServicesClient extends GenericGwtRpcDataSourceService<ProjectDTO> {

	public static class Util{
		private static ProjectServicesClientAsync instance = null;
		public static ProjectServicesClientAsync getInstance(){
			if (instance == null){
				instance = GWT.create(ProjectServicesClient.class);
			}
			return instance;
		}
	}

	List<ProjectDTO> getProjects() throws IllegalArgumentException;
	
	ProjectDTO save(ProjectDTO project);
	
	ProjectDTO getProject(Integer id);
}





