package ar.fi.uba.tempore.gwt.client;

import java.util.List;

import ar.fi.uba.tempore.dto.ProjectDTO;
import ar.fi.uba.temporeutils.listgrid.GenericGwtRpcDataSourceServiceAsync;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ProjectServicesClientAsync extends GenericGwtRpcDataSourceServiceAsync<ProjectDTO>{

	void getProjects(AsyncCallback<List<ProjectDTO>> callback);

	void save(ProjectDTO project, AsyncCallback<ProjectDTO> callback);

	void getProject(Integer id, AsyncCallback<ProjectDTO> callback);

}
