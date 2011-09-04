package ar.fi.uba.temporeutils.listgrid;


import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface GenericGwtRpcDataSourceServiceAsync<D> {

	void add(D data, AsyncCallback<D> callback);

	void fetch(AsyncCallback<List<D>> callback);

	void remove(D data, AsyncCallback<Void> callback);

	void update(D data, AsyncCallback<D> callback);

}
