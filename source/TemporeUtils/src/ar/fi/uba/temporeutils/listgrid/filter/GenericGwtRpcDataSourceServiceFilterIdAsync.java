package ar.fi.uba.temporeutils.listgrid.filter;


import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface GenericGwtRpcDataSourceServiceFilterIdAsync<F,D> {

	void fetch(F id, AsyncCallback<List<D>> callback);

	void add(D data, AsyncCallback<D> callback);

	void remove(D data, AsyncCallback<Void> callback);

	void update(D data, AsyncCallback<D> callback);

}
