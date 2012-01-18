package ar.fi.uba.tempore.gwt.client;

import ar.fi.uba.tempore.dto.UserProjectDTO;
import ar.fi.uba.temporeutils.listgrid.filter.GenericGwtRpcDataSourceServiceFilterIdAsync;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface UserProjectServicesClientAsync extends GenericGwtRpcDataSourceServiceFilterIdAsync<Integer, UserProjectDTO>{

	void changeOwner(UserProjectDTO data, AsyncCallback<Integer> asyncCallback);

}
