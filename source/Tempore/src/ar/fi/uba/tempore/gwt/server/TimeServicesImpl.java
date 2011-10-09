package ar.fi.uba.tempore.gwt.server;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.dozer.DozerBeanMapper;

import ar.fi.uba.tempore.dao.TaskUserDAO;
import ar.fi.uba.tempore.dto.TaskUserDTO;
import ar.fi.uba.tempore.entity.TaskUser;
import ar.fi.uba.tempore.gwt.client.TimeServicesClient;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class TimeServicesImpl extends RemoteServiceServlet implements TimeServicesClient {

	private static final long serialVersionUID = -7296572794226418672L;

	private final Logger log = Logger.getLogger(this.getClass());	
	private final DozerBeanMapper mapper = new DozerBeanMapper();
	private final TaskUserDAO tuDAO = new TaskUserDAO(); 


	@Override
	public List<TaskUserDTO> fetch() {	
		log.info("FETCH - TaskUser");
		List<TaskUserDTO> list = new ArrayList<TaskUserDTO>();
		
		List<TaskUser> findAll = tuDAO.findAll();
		for (TaskUser c : findAll) {
			TaskUserDTO cDTO = mapper.map(c, TaskUserDTO.class);
			log.info("FETCH DATA - TaskUser");log.info(cDTO.getDate().toString());
			list.add(cDTO);
		}
		
		return list;
	}

	@Override
	public TaskUserDTO add(TaskUserDTO clientDTO) {		
		return update(clientDTO);
	}

	@Override
	public TaskUserDTO update(TaskUserDTO taskUserDTO) {
		log.info("UPDATE - TaskUser");
//		taskUserDTO.setComment("Comentario harcodeado");
//		taskUserDTO.setDate(new Date());
//		taskUserDTO.setHourCount(2);
//		TaskDTO tarea = new TaskDTO();
//		tarea.setId(1);
//		taskUserDTO.setTask(tarea);
//		UserDTO usuario = new UserDTO();
//		usuario.setId(1);
//		taskUserDTO.setUser(usuario);
		
		
		
		TaskUser userTask = mapper.map(taskUserDTO, TaskUser.class);
		
		
		log.info("ID USER: " + userTask.getUser().getId());
		log.info("Id task: " + userTask.getTask().getId());
		
		TaskUser newUserTask = tuDAO.makePersistent(userTask );
		TaskUserDTO newDTO = mapper.map(newUserTask, TaskUserDTO.class); 
		newDTO.getTask().setProject(taskUserDTO.getTask().getProject());
		return newDTO;
	}

	@Override
	public void remove(TaskUserDTO taskUserDTO) {
		log.info("REMOVE - TaskUser");
		TaskUser entity = mapper.map(taskUserDTO, TaskUser.class);
		tuDAO.delete(entity);		
	}	
}



