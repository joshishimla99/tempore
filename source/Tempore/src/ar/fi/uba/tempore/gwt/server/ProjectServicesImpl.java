package ar.fi.uba.tempore.gwt.server;

import java.util.ArrayList;
import java.util.List;

import ar.fi.uba.tempore.dto.ProjectDTO;
import ar.fi.uba.tempore.gwt.client.ProjectServicesClient;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ProjectServicesImpl extends RemoteServiceServlet implements ProjectServicesClient {

	private static final long serialVersionUID = -6786157718346471647L;

	@Override
	public List<ProjectDTO> getProjects() {
		List<ProjectDTO> list = new ArrayList<ProjectDTO>();
		
		ProjectDTO p = new ProjectDTO();
		p.setId(new Integer(1));
		p.setName("Proyecto 1");
		list.add(p);
		
		p.setId(new Integer(2));
		p.setName("Proyecto 2");
		list.add(p);
		
		p.setId(new Integer(3));
		p.setName("Proyecto 3");
		list.add(p);
		
		p.setId(new Integer(4));
		p.setName("Proyecto 4");
		list.add(p);

		//TODO Buscar en BBDD
		
		return list;
	}

}
