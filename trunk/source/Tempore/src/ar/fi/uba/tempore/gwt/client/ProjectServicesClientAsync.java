package ar.fi.uba.tempore.gwt.client;

import java.util.List;

import ar.fi.uba.tempore.dto.ProjectDTO;
import ar.fi.uba.temporeutils.listgrid.filter.GenericGwtRpcDataSourceServiceFilterIdAsync;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ProjectServicesClientAsync extends GenericGwtRpcDataSourceServiceFilterIdAsync<Integer, ProjectDTO>{

	void getProjectById(Integer id, AsyncCallback<ProjectDTO> callback);

	void fetch(AsyncCallback<List<ProjectDTO>> callback);

}
