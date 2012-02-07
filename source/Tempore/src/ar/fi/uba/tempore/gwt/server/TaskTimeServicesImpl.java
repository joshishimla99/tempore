package ar.fi.uba.tempore.gwt.server;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.dozer.DozerBeanMapper;

import ar.fi.uba.tempore.dao.TaskDAO;
import ar.fi.uba.tempore.dto.TaskTimeDTO;
import ar.fi.uba.tempore.entity.Task;
import ar.fi.uba.tempore.gwt.client.TaskTimeServicesClient;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class TaskTimeServicesImpl extends RemoteServiceServlet implements TaskTimeServicesClient {

	private static final long serialVersionUID = 276652775164047439L;
	private final Logger log = Logger.getLogger(this.getClass());	
	private final DozerBeanMapper mapper = new DozerBeanMapper(); 


	@Override
	public List<TaskTimeDTO> fetch(Integer projectId) {	
		TaskDAO tuDAO = new TaskDAO();
		log.info("FETCH - TaskTime - ProjectId = " + projectId);
		List<TaskTimeDTO> list = new ArrayList<TaskTimeDTO>();
		
		List<Task> findAll = tuDAO.getAllTasksByProject(projectId);
		for (Task c : findAll) {
			
			
			TaskTimeDTO cDTO = mapper.map(c, TaskTimeDTO.class);
			list.add(cDTO);
		}
		
		return list;
	}

	@Override
	public TaskTimeDTO add(TaskTimeDTO clientDTO) {		
		throw new UnsupportedOperationException("No soportada...");
	}

	@Override
	public TaskTimeDTO update(TaskTimeDTO taskTimeDTO) {
		throw new UnsupportedOperationException("No soportada...");
	}

	@Override
	public void remove(TaskTimeDTO TaskTimeDTO) {
		throw new UnsupportedOperationException("No soportada...");		
	}	
}



