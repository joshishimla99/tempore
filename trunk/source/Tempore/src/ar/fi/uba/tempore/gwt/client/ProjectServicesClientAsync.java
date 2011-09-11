package ar.fi.uba.tempore.gwt.client;

import ar.fi.uba.tempore.dto.ProjectDTO;
import ar.fi.uba.temporeutils.listgrid.GenericGwtRpcDataSourceServiceAsync;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ProjectServicesClientAsync extends GenericGwtRpcDataSourceServiceAsync<ProjectDTO>{

	void getProjectById(Integer id, AsyncCallback<ProjectDTO> callback);

}
