package ar.fi.uba.tempore.gwt.server;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.dozer.DozerBeanMapper;

import ar.fi.uba.tempore.dao.AlertDAO;
import ar.fi.uba.tempore.dto.AlertDTO;
import ar.fi.uba.tempore.entity.Alert;
import ar.fi.uba.tempore.gwt.client.AlertServicesClient;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class AlertServicesImpl extends RemoteServiceServlet implements AlertServicesClient {
	private static final long serialVersionUID = 7476117264486326360L;
	private final Logger log = Logger.getLogger(this.getClass());

	private final AlertDAO aDAO = new AlertDAO();
	private final DozerBeanMapper mapper = new DozerBeanMapper();
	
	@Override
	public List<AlertDTO> getAlerts() {
		log.info("getAlerts()");
		List<AlertDTO> list = new ArrayList<AlertDTO>();
		List<Alert> findAll = aDAO.findAll();
		for (Alert a : findAll) {
			AlertDTO aDTO = mapper.map(a, AlertDTO.class);
			list.add(aDTO);
		}
		
		return list;
	}
	
	@Override
	public AlertDTO updateSaveAlert (AlertDTO alertDTO){
		Alert a = mapper.map(alertDTO, Alert.class);
		Alert makePersistent = aDAO.makePersistent(a);
		return mapper.map(makePersistent, AlertDTO.class);
	}
}