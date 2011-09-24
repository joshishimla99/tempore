package ar.fi.uba.tempore.gwt.server;

import java.util.ArrayList;
import java.util.List;

import ar.fi.uba.tempore.dto.TaskDTO;
import ar.fi.uba.tempore.gwt.client.TaskServicesClient;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class TaskServicesImpl extends RemoteServiceServlet implements TaskServicesClient {

	private static final long serialVersionUID = -2875888868382111997L;
	
	public List<TaskDTO> getChildTask(String id){
		List<TaskDTO> childTask = new ArrayList<TaskDTO>();
		return childTask;
	}
	
	public void addTask(TaskDTO task){
		
	}
	
	public void deleteTask(String id){
		
	}
	
	public void updateTask(TaskDTO task){
		this.addTask(task);
	}
	
}
