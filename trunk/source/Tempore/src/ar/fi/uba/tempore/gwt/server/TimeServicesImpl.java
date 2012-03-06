package ar.fi.uba.tempore.gwt.server;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.dozer.DozerBeanMapper;

import ar.fi.uba.tempore.dao.TaskUserDAO;
import ar.fi.uba.tempore.dto.TaskUserDTO;
import ar.fi.uba.tempore.dto.TimeFilterDTO;
import ar.fi.uba.tempore.entity.TaskUser;
import ar.fi.uba.tempore.gwt.client.TimeServicesClient;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class TimeServicesImpl extends RemoteServiceServlet implements TimeServicesClient {

	private static final long serialVersionUID = -7296572794226418672L;

	public static final Long QUINCE_MIN = 15*60*1000L;
	private final Logger log = Logger.getLogger(this.getClass());	
	private final DozerBeanMapper mapper = new DozerBeanMapper(); 


	@Override
	public List<TaskUserDTO> fetch(TimeFilterDTO filter) {	
		TaskUserDAO tuDAO = new TaskUserDAO();
		log.info("FETCH - TaskUser");
		List<TaskUserDTO> list = new ArrayList<TaskUserDTO>();
		
		List<TaskUser> findAll = tuDAO.findByDate(filter.getUserId(), filter.getDateFilter());
		for (TaskUser c : findAll) {
			TaskUserDTO cDTO = mapper.map(c, TaskUserDTO.class);
			log.info(cDTO.getDate().toString());
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
		TaskUserDAO tuDAO = new TaskUserDAO();
		log.info("UPDATE - TaskUser");

		//Redondeo de las horas a 15 min
		Long factor = Math.round((double)taskUserDTO.getHourCount()/QUINCE_MIN);
		taskUserDTO.setHourCount(factor*QUINCE_MIN);
		
		TaskUser userTask = mapper.map(taskUserDTO, TaskUser.class);
		
		log.info("ID USER: " + userTask.getUser().getId() +","+ taskUserDTO.getHourCount());
		log.info("Id task: " + userTask.getTask().getId());
		
		TaskUser newUserTask = tuDAO.makePersistent(userTask );
		TaskUserDTO newDTO = mapper.map(newUserTask, TaskUserDTO.class); 
		newDTO.getTask().setProject(taskUserDTO.getTask().getProject());
		return newDTO;
	}

	@Override
	public void remove(TaskUserDTO taskUserDTO) {
		TaskUserDAO tuDAO = new TaskUserDAO();
		log.info("REMOVE - TaskUser");
		TaskUser entity = mapper.map(taskUserDTO, TaskUser.class);
		tuDAO.delete(entity);		
	}	
}



