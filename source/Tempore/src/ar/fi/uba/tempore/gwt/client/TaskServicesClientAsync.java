package ar.fi.uba.tempore.gwt.client;

import java.util.List;

import ar.fi.uba.tempore.dto.TaskDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface TaskServicesClientAsync {

	void getChildTask(String id, AsyncCallback<List<TaskDTO>> callback);

	void addTask(TaskDTO task, AsyncCallback<Void> callback);

	void deleteTask(String id, AsyncCallback<Void> callback);

	void updateTask(TaskDTO task, AsyncCallback<Void> callback);

}
