package ar.fi.uba.tempore.gwt.server;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import ar.fi.uba.tempore.dto.ClientDTO;

public class ClientServicesImpl extends RemoteServiceServlet implements ar.fi.uba.tempore.gwt.client.ClientServicesClient {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final Logger log = Logger.getLogger(this.getClass());

	@Override
	public List<ClientDTO> getClients() {
		log.debug("Service getClients()");
		
		List<ClientDTO> ClientList = new ArrayList<ClientDTO>();
		
		ClientDTO Client = new ClientDTO();
		Client.setName("Interno");
		Client.setAddress("Eduardo Madero 900 - 21");
		Client.setCountry("Argentina");
		Client.setState("Bs As");
		Client.setZip("1000");
		Client.setFiscalNumber("111111");
		Client.setPhone("111111");
		ClientList.add(Client);
		
		ClientDTO Client1 = new ClientDTO();
		Client1.setName("Interno");
		Client1.setAddress("Eduardo Madero 900 - 21");
		Client1.setCountry("Argentina");
		Client1.setState("Bs As");
		Client1.setZip("1000");
		Client1.setFiscalNumber("111111");
		Client1.setPhone("111111");
		ClientList.add(Client1);		
		return ClientList;
	}

}

