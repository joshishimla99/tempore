package ar.fi.uba.tempore.gwt.server;

import java.util.List;

import ar.fi.uba.tempore.dto.ProjectDTO;
import ar.fi.uba.tempore.service.ProjectServices;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ProjectServicesClientImpl extends RemoteServiceServlet implements ar.fi.uba.tempore.gwt.client.ProjectServicesClient {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6786157718346471647L;

	@Override
	public List<ProjectDTO> getProjects() {
			return new ProjectServices().allProject();
	}

}
