package ar.fi.uba.temporeutils.listgrid.filter;


import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;

/**
 * The client side stub for the RPC service.
 */
public interface GenericGwtRpcDataSourceServiceFilterId<F,D> extends RemoteService {
	
    List<D> fetch (F id);

    D add (D data);

    D update (D data);

    void remove (D data);
	
}
