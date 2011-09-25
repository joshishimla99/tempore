package ar.fi.uba.tempore.gwt.server;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.dozer.DozerBeanMapper;

import ar.fi.uba.tempore.dao.TaskDAO;
import ar.fi.uba.tempore.dto.TaskTimeDTO;
import ar.fi.uba.tempore.entity.Project;
import ar.fi.uba.tempore.entity.Task;
import ar.fi.uba.tempore.gwt.client.TaskTimeServicesClient;


import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class TaskTimeServicesImpl extends RemoteServiceServlet implements TaskTimeServicesClient {

	private static final long serialVersionUID = 276652775164047439L;
	private final Logger log = Logger.getLogger(this.getClass());	
	private final DozerBeanMapper mapper = new DozerBeanMapper();
	private final TaskDAO tuDAO = new TaskDAO(); 


	@Override
	public List<TaskTimeDTO> fetch(Integer id) {	
		log.info("FETCH - TaskTime");
		List<TaskTimeDTO> list = new ArrayList<TaskTimeDTO>();
		log.info("Id Projecto: " + id);
		
		Task exampleInstance = new Task();
		Project project = new Project();
		project.setId(id);
		exampleInstance.setProject(project);
		List<Task> findAll = tuDAO.findByExample(exampleInstance);
		for (Task c : findAll) {
			TaskTimeDTO cDTO = mapper.map(c, TaskTimeDTO.class);
			list.add(cDTO);
		}
		
		return list;
	}

	@Override
	public TaskTimeDTO add(TaskTimeDTO clientDTO) {		
		return update(clientDTO);
	}

	@Override
	public TaskTimeDTO update(TaskTimeDTO taskTimeDTO) {
		log.info("UPDATE - TaskTime");
		Task userTask = mapper.map(taskTimeDTO, Task.class);
		Task newUserTask = tuDAO.makePersistent(userTask );
		TaskTimeDTO newDTO = mapper.map(newUserTask, TaskTimeDTO.class); 
		return newDTO;
	}

	@Override
	public void remove(TaskTimeDTO TaskTimeDTO) {
		log.info("REMOVE - TaskTime");
		Task entity = mapper.map(TaskTimeDTO, Task.class);
		tuDAO.delete(entity);		
	}	
}



