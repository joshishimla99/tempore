package ar.fi.uba.tempore.gwt.client;

import java.util.List;

import ar.fi.uba.tempore.dto.ProjectStateDTO;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("ProjectStateServicesClient")
public interface ProjectStateServicesClient extends RemoteService {
	/**
	 * Utility class for simplifying access to the instance of async service.
	 */
	public static class Util {
		private static ProjectStateServicesClientAsync instance;
		public static ProjectStateServicesClientAsync getInstance(){
			if (instance == null) {
				instance = GWT.create(ProjectStateServicesClient.class);
			}
			return instance;
		}
	}
	
	public List<ProjectStateDTO> findAll ();
}
