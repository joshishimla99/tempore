package ar.fi.uba.tempore.gwt.client;

import java.util.List;

import ar.fi.uba.tempore.dto.TaskTypeDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface TaskTypeServicesClientAsync {

	void fetch(AsyncCallback<List<TaskTypeDTO>> callback);
}
