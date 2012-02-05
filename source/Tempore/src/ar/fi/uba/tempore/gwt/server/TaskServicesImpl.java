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
//	private static final Long GMT_3 = 10800000L;
	private final DozerBeanMapper mapper = new DozerBeanMapper();
	private final Logger log = Logger.getLogger(this.getClass());
	
	public List<TaskDTO> getChildTask(Integer idProject, Integer idTask){
		log.info("TASK - FETCH ChildTask");
		TaskDAO taskDAO = new TaskDAO();
		List<TaskDTO> taskDTOList = new ArrayList<TaskDTO>();
		
		List<Task> taskList = taskDAO.getChildTask(idProject, idTask); 
		for (Task task : taskList) {
			TaskDTO map = mapper.map(task, TaskDTO.class);
			map.setTaskTypeDTO(mapper.map(task.getTaskType(), TaskTypeDTO.class));
			taskDTOList.add(map);
		}
		
		return taskDTOList;
	}
	
	
	
	public String deleteTask(Integer id, Integer idProject) throws TaskWithHoursChargedException{
		log.info("TASK - DELETE");
		TaskDAO taskDAO = new TaskDAO();
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
		TaskUserDAO taskUserDAO = new TaskUserDAO();
		return taskUserDAO.hoursChargedByTask(taskId);
	}
	
	/**
	 * Agrega una nueva tarea
	 */
	public TaskDTO addTask(TaskDTO taskDTO){
		return this.updateTask(taskDTO);
	}

	/**
	 * Actualiza o Agrega una nueva tarea, dependiendo si el taskDTO viene con ID o no. 
	 * Sino viene el ID se crea la tarea. Si viene y existe ese ID se actualiza con los datos que viene.
	 */
	public TaskDTO updateTask(TaskDTO taskDTO){
		log.info("UPDATE - TASK");
		TaskDAO taskDAO = new TaskDAO();

		//Actualizo el tiempo acumulado sacando le el GMT
		Task task = mapper.map(taskDTO, Task.class);

		//Seteamos el task type a partir del nombre
		TaskType taskType = new TaskTypeDAO().findById(taskDTO.getTaskTypeDTO().getId());
		task.setTaskType(taskType);
		TaskTypeDTO taskTypeDTO = mapper.map(taskType, TaskTypeDTO.class);
		
		//Marco cual es el proyecto
		Project project = new Project();
		project.setId(taskDTO.getProject().getId());
		task.setProject(project);
		
		//Actualizo la tarea
		taskDAO.makePersistent(task);
		
		taskDTO.setTaskTypeDTO(taskTypeDTO);
		return taskDTO;
	}

	/**
	 * 
	 */
	@Override
	public long getTimeChargedToTask(Integer taskId) {
		return getTimeCharged(taskId);
	}

	@Override
	public long getTotalTimeChargedToChildsTask(Integer taskId) {
		TaskUserDAO taskUserDAO = new TaskUserDAO();
		return taskUserDAO.getTotalTimeByTask(taskId);
	}

	@Override
	public boolean validateTask (TaskDTO taskDTO) {
		log.info("VALIDATE TASK - " + taskDTO.getName());
		boolean result = true;
		TaskDAO taskDAO = new TaskDAO();
		
		//validamos la tarea que vamos a guardar
		List<Task> childTaskList = taskDAO.getChildTask(taskDTO.getProject().getId(), taskDTO.getTaskId());
		for (Task task : childTaskList) {
			String nameTask = new String(task.getName());
			if (task.getId() != taskDTO.getId() &&
					nameTask.trim().toUpperCase().equals(taskDTO.getName().toUpperCase().trim())){
				result = false;
			}
		}	
		
		return result;
	}
}
