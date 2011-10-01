package ar.fi.uba.tempore.gwt.server;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.dozer.DozerBeanMapper;

import ar.fi.uba.tempore.dao.ProjectDAO;
import ar.fi.uba.tempore.dao.TaskDAO;
import ar.fi.uba.tempore.dao.TaskTypeDAO;
import ar.fi.uba.tempore.dto.TaskDTO;
import ar.fi.uba.tempore.dto.TaskTypeDTO;
import ar.fi.uba.tempore.entity.Project;
import ar.fi.uba.tempore.entity.Task;
import ar.fi.uba.tempore.entity.TaskType;
import ar.fi.uba.tempore.gwt.client.TaskServicesClient;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class TaskServicesImpl extends RemoteServiceServlet implements TaskServicesClient {

	private static final long serialVersionUID = -2875888868382111997L;
	private final TaskDAO taskDAO = new TaskDAO();
	private final TaskTypeDAO taskTypeDAO = new TaskTypeDAO();
	private final ProjectDAO projectDAO = new ProjectDAO();
	private final DozerBeanMapper mapper = new DozerBeanMapper();
	private final Logger log = Logger.getLogger(this.getClass());
	
	public List<TaskDTO> getChildTask(Integer idProject, Integer idTask){
		List<Task> taskList = new ArrayList<Task>();
		List<TaskDTO> taskDTOList = new ArrayList<TaskDTO>();
		if (idTask == null){
			Task exampleInstance = new Task();
			Project project = new Project();
			project.setId(idProject);
			exampleInstance.setProject(project);
			exampleInstance.setTaskId(null);
			taskList = taskDAO.findByExample(exampleInstance);
		} else {
			taskList = taskDAO.getChildTask(idTask);
		}
		for (Task task : taskList) {
			TaskDTO map = mapper.map(task, TaskDTO.class);
			map.setTaskTypeDTO(mapper.map(task.getTaskType(), TaskTypeDTO.class));
			taskDTOList.add(map);
			
		}
		
		return taskDTOList;
	}
	
	public TaskDTO addTask(TaskDTO taskDTO){
		return updateTask(taskDTO);
	}
	
	public void deleteTask(String id){
		
	}
	
	public TaskDTO updateTask(TaskDTO taskDTO){
		log.info("UPDATE - TASK");
		List<TaskType> taskList = new ArrayList<TaskType>();
		TaskType exampleInstance = new TaskType();
		exampleInstance.setName(taskDTO.getTaskTypeDTO().getName());
		taskList = taskTypeDAO.findByExample(exampleInstance);
		
		Project project = new Project();
		log.error("" + taskDTO.getProject().getId());
		project.setId(taskDTO.getProject().getId());
		
		Task a = mapper.map(taskDTO, Task.class);
		a.setTaskType(taskList.get(0));
		a.setProject(project);
		
		Task makePersistent = taskDAO.makePersistent(a);
		return mapper.map(makePersistent, TaskDTO.class);
	}

}
