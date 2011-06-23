package ar.fi.uba.tempore.gwt.client;

import java.util.List;

import ar.fi.uba.tempore.dto.ProjectDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ProjectServicesClientAsync {

	void getProjects(AsyncCallback<List<ProjectDTO>> callback);

}
