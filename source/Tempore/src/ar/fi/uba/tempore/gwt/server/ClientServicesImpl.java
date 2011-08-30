package ar.fi.uba.tempore.gwt.server;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.dozer.DozerBeanMapper;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import ar.fi.uba.tempore.dao.ClientDAO;
import ar.fi.uba.tempore.dto.ClientDTO;
import ar.fi.uba.tempore.entity.Client;

public class ClientServicesImpl extends RemoteServiceServlet implements ar.fi.uba.tempore.gwt.client.ClientServicesClient {
	
	private static final long serialVersionUID = -2409690393681645739L;

	private final Logger log = Logger.getLogger(this.getClass());	
	private final DozerBeanMapper mapper = new DozerBeanMapper();
	private final ClientDAO cDAO = new ClientDAO(); 

	@Override
	public List<ClientDTO> getClients() {
		log.info("getClients()");
		
		List<ClientDTO> list = new ArrayList<ClientDTO>();
		
		List<Client> findAll = cDAO.findAll();
		for (Client c : findAll) {
			ClientDTO cDTO = mapper.map(c, ClientDTO.class);
			list.add(cDTO);
		}
		
		return list;
	}
}

