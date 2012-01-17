package ar.fi.uba.tempore.gwt.client;

import java.util.List;

import ar.fi.uba.tempore.dto.TaskDTO;
import ar.fi.uba.tempore.gwt.client.exception.TaskWithHoursChargedException;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface TaskServicesClientAsync {

	void getChildTask(Integer idProject, Integer idTask, AsyncCallback<List<TaskDTO>> callback);

	void addTask(TaskDTO task, AsyncCallback<TaskDTO> callback);

	void deleteTask(Integer id, Integer idProject, AsyncCallback<String> callback) throws TaskWithHoursChargedException;

	void updateTask(TaskDTO task, AsyncCallback<TaskDTO> callback);

	void getTimeChargedToTask(Integer id, AsyncCallback<Long> callback);

	void getTotalTimeChargedToChildsTask(Integer id, AsyncCallback<Long> callback);
	
	

}
