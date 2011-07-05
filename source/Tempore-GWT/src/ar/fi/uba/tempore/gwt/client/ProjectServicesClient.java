package ar.fi.uba.tempore.gwt.client;
import java.util.List;

import ar.fi.uba.tempore.dto.ProjectDTO;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("project")
public interface ProjectServicesClient extends RemoteService {

	List<ProjectDTO> getProjects() throws IllegalArgumentException;
}




