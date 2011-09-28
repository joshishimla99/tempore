package ar.fi.uba.tempore.gwt.client;

import java.util.List;

import ar.fi.uba.tempore.dto.TaskDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface TaskServicesClientAsync {

	void getChildTask(Integer idProject, Integer idTask, AsyncCallback<List<TaskDTO>> callback);

	void addTask(TaskDTO task, AsyncCallback<TaskDTO> callback);

	void deleteTask(String id, AsyncCallback<Void> callback);

	void updateTask(TaskDTO task, AsyncCallback<TaskDTO> callback);

}
