package ar.fi.uba.temporeutils.listgrid.filter;


import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;

/**
 * The client side stub for the RPC service.
 */
public interface GenericGwtRpcDataSourceServiceFilterId<D> extends RemoteService {
	
    List<D> fetch (Integer id);

    D add (D data);

    D update (D data);

    void remove (D data);
	
}
