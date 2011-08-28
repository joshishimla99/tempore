package ar.fi.uba.tempore.gwt.client.panel.configuration;

import java.util.List;
import ar.fi.uba.tempore.dto.ClientDTO;

public class ClientData {

	private static ClientRecord[] records;

	public static ClientRecord[] getRecords(List<ClientDTO> clientList) {
		if (records == null) {
			records = getNewRecords(clientList);
		}
		return records;
	}

	public static ClientRecord[] getNewRecords(List<ClientDTO> clientList) {
		ClientRecord[] r = new ClientRecord[10];
		for (int i = 0; i < clientList.size(); i++) {
			ClientDTO clientDTO = clientList.get(i);
			r[i] = new ClientRecord(clientDTO.getName(), clientDTO.getAddress(), clientDTO.getCountry(), 
									clientDTO.getState(), 
									clientDTO.getZip(), clientDTO.getFiscalNumber(),clientDTO.getPhone());
		}
		
		return r;
	}
}
