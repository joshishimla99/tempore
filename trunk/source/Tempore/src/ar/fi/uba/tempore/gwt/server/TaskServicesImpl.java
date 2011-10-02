package ar.fi.uba.tempore.gwt.server;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.dozer.DozerBeanMapper;

import ar.fi.uba.tempore.dao.TaskDAO;
import ar.fi.uba.tempore.dao.TaskTypeDAO;
import ar.fi.uba.tempore.dao.TaskUserDAO;
import ar.fi.uba.tempore.dto.TaskDTO;
import ar.fi.uba.tempore.dto.TaskTypeDTO;
import ar.fi.uba.tempore.entity.Project;
import ar.fi.uba.tempore.entity.Task;
import ar.fi.uba.tempore.entity.TaskType;
import ar.fi.uba.tempore.entity.TaskUser;
import ar.fi.uba.tempore.gwt.client.TaskServicesClient;
import ar.fi.uba.tempore.gwt.client.exception.TaskWithHoursChargedException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class TaskServicesImpl extends RemoteServiceServlet implements TaskServicesClient {

	private static final long serialVersionUID = -2875888868382111997L;
	private final TaskDAO taskDAO = new TaskDAO();
	private final TaskTypeDAO taskTypeDAO = new TaskTypeDAO();
	private final TaskUserDAO taskUserDAO = new TaskUserDAO();
	private final DozerBeanMapper mapper = new DozerBeanMapper();
	private final Logger log = Logger.getLogger(this.getClass());
	
	public List<TaskDTO> getChildTask(Integer idProject, Integer idTask){
		List<TaskDTO> taskDTOList = new ArrayList<TaskDTO>();
		
		List<Task> taskList = taskDAO.getChildTask(idProject, idTask); 
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
	
	public String deleteTask(Integer id, Integer idProject) throws TaskWithHoursChargedException{
		Task taskToDelete = taskDAO.findById(id);
		// SI LA TAREA NO TIENE TIEMPO CARGADO, ENTONCES VERIFICO SI SUS HIJAS LO TIENEN
		if (getTimeChargedToTask(taskToDelete.getId()) == 0) {   
			List<Task> taskList = taskDAO.getChildTask(idProject, id); 
			for (Task task : taskList) {
				if (getTimeChargedToTask(task.getId()) > 0){
					throw new TaskWithHoursChargedException(task.getName());
				}
			}
			// Si ni la tarea padre ni sus hijas tienen horas cargas ==> borro las hijas y la tarea padre
			for (Task task : taskList) {
				if (getTimeChargedToTask(task.getId()) > 0){
					taskDAO.delete(task);
				}
			}
			taskDAO.delete(taskToDelete);
		} else {
			throw new TaskWithHoursChargedException(taskToDelete.getName());
		}
		return taskToDelete.getName();
	}
	
	private int getTimeChargedToTask(Integer taskId){
		return taskUserDAO.hoursChargedByTask(taskId);
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
