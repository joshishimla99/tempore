package ar.fi.uba.temporeutils.listgrid;


import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;

/**
 * The client side stub for the RPC service.
 */
public interface GenericGwtRpcDataSourceService<D> extends RemoteService {
	
    List<D> fetch ();

    D add (D data);

    D update (D data);

    void remove (D data);
	
}
