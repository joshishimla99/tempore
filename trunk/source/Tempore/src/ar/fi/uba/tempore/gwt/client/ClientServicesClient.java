package ar.fi.uba.tempore.gwt.client;
import java.util.List;

import ar.fi.uba.tempore.dto.ClientDTO;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("ClientServices")
public interface ClientServicesClient extends RemoteService{
	
	List<ClientDTO> getClients() throws IllegalArgumentException;
}
