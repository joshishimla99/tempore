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
		// REGLA DE NEGOCIO -> SI LA TAREA NO TIENE TIEMPO CARGADO, ENTONCES VERIFICO SI SUS HIJAS LO TIENEN
		if (getTimeCharged(taskToDelete.getId()) == 0) {   
			List<Task> taskList = taskDAO.getChildTask(idProject, id); 
			for (Task task : taskList) {
				if (getTimeCharged(task.getId()) > 0){
					throw new TaskWithHoursChargedException(task.getName());
				}
				List<Task> subtask = taskDAO.getChildTask(idProject, task.getId());
				for (Task subTask : subtask) {
					if (getTimeCharged(subTask.getId()) > 0){
						throw new TaskWithHoursChargedException(subTask.getName());
					}
				}
			}
			// Si ni la tarea padre ni sus hijas tienen horas cargas ==> borro las hijas y la tarea padre
			for (Task task : taskList) {
				List<Task> subTaskList = taskDAO.getChildTask(idProject, task.getId());
				for (Task subTask : subTaskList) {
					log.info("Trying to delete task ... " + subTask.getName());
					taskDAO.delete(subTask);
					log.info("Delete success task ... " + subTask.getName());
				}
				log.info("Trying to delete task ... " + task.getName());
				taskDAO.delete(task);
				log.info("Delete success task ... " + task.getName());
			}
			log.info("Trying to delete task ... " + taskToDelete.getName());
			taskDAO.delete(taskToDelete);
			log.info("Delete success task ... " + taskToDelete.getName());
		} else {
			throw new TaskWithHoursChargedException(taskToDelete.getName());
		}
		return taskToDelete.getName();
	}
	
	private long getTimeCharged(Integer taskId){
		return taskUserDAO.hoursChargedByTask(taskId);
	}
	
	public TaskDTO updateTask(TaskDTO taskDTO){
		log.info("UPDATE - TASK");
		List<TaskType> taskList = new ArrayList<TaskType>();
		TaskType exampleInstance = new TaskType();
		exampleInstance.setName(taskDTO.getTaskTypeDTO().getName());
		log("Task Type " + taskDTO.getTaskTypeDTO().getName());
		taskList = taskTypeDAO.findByExample(exampleInstance);
		
		Project project = new Project();
		project.setId(taskDTO.getProject().getId());
		
		Task a = mapper.map(taskDTO, Task.class);
		a.setTaskType(taskList.get(0));
		a.setProject(project);
		
		Task makePersistent = taskDAO.makePersistent(a);
		return mapper.map(makePersistent, TaskDTO.class);
	}

	@Override
	public long getTimeChargedToTask(Integer id) {
		return getTimeCharged(id);
	}

	@Override
	public long getTotalTimeChargedToTask(Integer id) {
		return taskUserDAO.getTotalTimeByTask(id);
	}

}
