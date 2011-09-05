package ar.fi.uba.tempore.gwt.server;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.dozer.DozerBeanMapper;

import ar.fi.uba.tempore.dao.ClientDAO;
import ar.fi.uba.tempore.dto.ClientDTO;
import ar.fi.uba.tempore.entity.Client;
import ar.fi.uba.tempore.gwt.client.ClientServicesClient;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ClientServicesImpl extends RemoteServiceServlet implements ClientServicesClient {
	
	private static final long serialVersionUID = -2409690393681645739L;

	private final Logger log = Logger.getLogger(this.getClass());	
	private final DozerBeanMapper mapper = new DozerBeanMapper();
	private final ClientDAO cDAO = new ClientDAO(); 


	@Override
	public List<ClientDTO> fetch() {	
		log.info("FETCH - Clientes");
		List<ClientDTO> list = new ArrayList<ClientDTO>();
		
		List<Client> findAll = cDAO.findAll();
		for (Client c : findAll) {
			ClientDTO cDTO = mapper.map(c, ClientDTO.class);
			list.add(cDTO);
		}
		
		return list;
	}

	@Override
	public ClientDTO add(ClientDTO clientDTO) {		
		return update(clientDTO);
	}

	@Override
	public ClientDTO update(ClientDTO clientDTO) {
		log.info("UPDATE - Cliente");
		Client client = mapper.map(clientDTO, Client.class);
		Client newClient = cDAO.makePersistent(client );
		ClientDTO newDTO = mapper.map(newClient, ClientDTO.class); 
		return newDTO;
	}

	@Override
	public void remove(ClientDTO clientDTO) {
		log.info("REMOVE - Cliente");
		Client entity = mapper.map(clientDTO, Client.class);
		cDAO.delete(entity);		
	}	
}

