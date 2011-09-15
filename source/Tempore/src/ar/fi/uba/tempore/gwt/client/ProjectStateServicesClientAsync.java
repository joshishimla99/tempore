package ar.fi.uba.tempore.gwt.client;

import java.util.List;

import ar.fi.uba.tempore.dto.ProjectStateDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ProjectStateServicesClientAsync {
	void findAll(AsyncCallback<List<ProjectStateDTO>> callback);
}
