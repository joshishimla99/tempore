package ar.fi.uba.tempore.gwt.server;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;

import ar.fi.uba.tempore.dao.TaskTypeDAO;
import ar.fi.uba.tempore.dto.TaskTypeDTO;
import ar.fi.uba.tempore.entity.TaskType;
import ar.fi.uba.tempore.gwt.client.TaskTypeServicesClient;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class TaskTypeServicesImpl extends RemoteServiceServlet implements TaskTypeServicesClient {

	private static final long serialVersionUID = -508734123193154880L;
	private final DozerBeanMapper mapper = new DozerBeanMapper();

	public List<TaskTypeDTO> fetch(){
		TaskTypeDAO taskTypeDAO = new TaskTypeDAO();
		List<TaskTypeDTO> taskTypeDTOList = new ArrayList<TaskTypeDTO>();
		List<TaskType> taskTypeList = new ArrayList<TaskType>();
		taskTypeList = taskTypeDAO.findAll();
		
		for (TaskType taskType : taskTypeList) {
			TaskTypeDTO map = mapper.map(taskType, TaskTypeDTO.class);
			taskTypeDTOList.add(map);
		}
		
		return taskTypeDTOList;
	}
	
}