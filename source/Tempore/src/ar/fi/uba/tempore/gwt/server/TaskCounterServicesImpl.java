package ar.fi.uba.tempore.gwt.server;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.dozer.DozerBeanMapper;

import ar.fi.uba.tempore.dao.ProjectDAO;
import ar.fi.uba.tempore.dao.TaskDAO;
import ar.fi.uba.tempore.dto.TaskTimeDTO;
import ar.fi.uba.tempore.entity.Project;
import ar.fi.uba.tempore.entity.Task;
import ar.fi.uba.tempore.gwt.client.TaskCounterServicesClient;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class TaskCounterServicesImpl extends RemoteServiceServlet implements TaskCounterServicesClient {

	private static final long serialVersionUID = -4658076506558299918L;
	private final Logger log = Logger.getLogger(this.getClass());	
	private final DozerBeanMapper mapper = new DozerBeanMapper();
 

	@Override
	public List<TaskTimeDTO> fetch(Integer userId) {
		TaskDAO tuDAO = new TaskDAO();
		ProjectDAO pDAO = new ProjectDAO();
		
		log.info("FETCH - TaskCounter ");
		List<TaskTimeDTO> list = new ArrayList<TaskTimeDTO>();
		
		
		List<Project> findAll2 = pDAO.getProjectsByUser(userId);
		for (Project p : findAll2) {
			TaskTimeDTO t = new TaskTimeDTO();
			t.setId(p.getId()*-1);
			t.setName(p.getName());
			t.setDescription(p.getDescription());
			t.setTaskId(null);
			t.setTaskTimeList(new ArrayList<TaskTimeDTO>());
			list.add(t);
		
			List<Task> findAll = tuDAO.getAllTasksByProject(p.getId());
			for (Task c : findAll) {
				
				TaskTimeDTO cDTO = mapper.map(c, TaskTimeDTO.class);
				
				if (cDTO.getTaskId()==null){
					cDTO.setTaskId(c.getProject().getId()*-1);
				}
				list.add(cDTO);
			}
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



