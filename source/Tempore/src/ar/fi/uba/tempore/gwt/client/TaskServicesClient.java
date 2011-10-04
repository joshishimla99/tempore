package ar.fi.uba.tempore.gwt.client;

import java.util.List;

import ar.fi.uba.tempore.dto.TaskDTO;
import ar.fi.uba.tempore.gwt.client.exception.TaskWithHoursChargedException;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("TaskServicesClient")
public interface TaskServicesClient extends RemoteService {
	/**
	 * Utility class for simplifying access to the instance of async service.
	 */
	public static class Util {
		private static TaskServicesClientAsync instance;

		public static TaskServicesClientAsync getInstance() {
			if (instance == null) {
				instance = GWT.create(TaskServicesClient.class);
			}
			return instance;
		}
	}

	public List<TaskDTO> getChildTask(Integer idProject, Integer idTask);

	public TaskDTO addTask(TaskDTO task);

	// Devuelve el nombre de la tarea eliminada
	public String deleteTask(Integer id, Integer idProject) throws TaskWithHoursChargedException;

	public TaskDTO updateTask(TaskDTO task);
	
	public long getTimeChargedToTask(Integer id);
}
