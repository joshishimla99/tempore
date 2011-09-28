package ar.fi.uba.tempore.gwt.client;

import java.util.List;

import ar.fi.uba.tempore.dto.TaskTypeDTO;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("TaskTypeServicesClient")
public interface TaskTypeServicesClient extends RemoteService {
	/**
	 * Utility class for simplifying access to the instance of async service.
	 */
	public static class Util {
		private static TaskTypeServicesClientAsync instance = null;

		public static TaskTypeServicesClientAsync getInstance() {
			if (instance == null) {
				instance = GWT.create(TaskTypeServicesClient.class);
			}
			return instance;
		}
	}
	
	public List<TaskTypeDTO> fetch();
}

