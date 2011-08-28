package ar.fi.uba.tempore.gwt.client;

import java.util.List;
import ar.fi.uba.tempore.dto.ClientDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ClientServicesClientAsync {
	
	void getClients(AsyncCallback<List<ClientDTO>> callback);
}
