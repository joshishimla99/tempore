package ar.fi.uba.tempore.gwt.server;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.dozer.DozerBeanMapper;

import ar.fi.uba.tempore.dao.ProjectDAO;
import ar.fi.uba.tempore.dto.ProjectDTO;
import ar.fi.uba.tempore.entity.Project;
import ar.fi.uba.tempore.gwt.client.ProjectServicesClient;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ProjectServicesImpl extends RemoteServiceServlet implements ProjectServicesClient {	
	private static final long serialVersionUID = -6786157718346471647L;
	private final Logger log = Logger.getLogger(this.getClass());
	
	private final ProjectDAO projectDAO = new ProjectDAO();
	private final DozerBeanMapper mapper = new DozerBeanMapper();
	
	@Override
	public List<ProjectDTO> getProjects() {
		log.debug("getProjects");
		List<ProjectDTO> list = new ArrayList<ProjectDTO>();
		
		List<Project> projects = projectDAO.findAll();
		for (Project p : projects) {
			ProjectDTO pDTO = mapper.map(p, ProjectDTO.class);
			list.add(pDTO);
		}
		
		return list;
	}

}
