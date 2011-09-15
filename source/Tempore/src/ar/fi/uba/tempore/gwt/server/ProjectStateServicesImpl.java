package ar.fi.uba.tempore.gwt.server;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.dozer.DozerBeanMapper;

import ar.fi.uba.tempore.dao.ProjectStateDAO;
import ar.fi.uba.tempore.dto.ProjectStateDTO;
import ar.fi.uba.tempore.entity.ProjectState;
import ar.fi.uba.tempore.gwt.client.ProjectStateServicesClient;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ProjectStateServicesImpl extends RemoteServiceServlet implements ProjectStateServicesClient {


	private final Logger log = Logger.getLogger(this.getClass());
	private static final long serialVersionUID = 1319210883924396388L;
	
	private final ProjectStateDAO psDAO = new ProjectStateDAO();
	private final DozerBeanMapper mapper = new DozerBeanMapper();
	
	@Override
	public List<ProjectStateDTO> findAll() {
		log.info("FIND ALL - ProjectState");
		List<ProjectStateDTO> list = new ArrayList<ProjectStateDTO>();
		
		List<ProjectState> states = psDAO.findAll();
		for (ProjectState projectState : states) {
			ProjectStateDTO dto = mapper.map(projectState, ProjectStateDTO.class);
			list.add(dto);
		}		
		return list;
	}
}
