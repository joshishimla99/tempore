package ar.fi.uba.tempore.gwt.client.panel.counter;

import java.util.ArrayList;
import java.util.List;

import ar.fi.uba.tempore.dto.TaskTimeDTO;
import ar.fi.uba.tempore.gwt.client.TaskCounterServicesClient;
import ar.fi.uba.tempore.gwt.client.TaskCounterServicesClientAsync;
import ar.fi.uba.tempore.gwt.client.login.SessionUser;
import ar.fi.uba.tempore.gwt.client.panel.counter.CounterTimePanel;
import ar.fi.uba.temporeutils.listgrid.filter.GenericGwtRpcDataSourceFilterId;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.widgets.tree.TreeNode;

public class TaskListDataSource extends GenericGwtRpcDataSourceFilterId<Integer, TaskTimeDTO, TreeNode, TaskCounterServicesClientAsync>  {
	private static TaskListDataSource instance = null;  
	private static final Integer ROOT_VALUE = 0;
	
	public static TaskListDataSource getInstance() {  
		if (instance == null) {  
			instance = new TaskListDataSource();  
		}  
		return instance;  
	}  

	@Override
	public List<DataSourceField> getDataSourceFields() {
		List<DataSourceField> list = new ArrayList<DataSourceField>();
		
		DataSourceIntegerField taskIdField = new DataSourceIntegerField(CounterTimePanel.COL_TASK_ID, "ID");  
		taskIdField.setPrimaryKey(true); 
		list.add(taskIdField);
		
		
		DataSourceIntegerField reportsToField = new DataSourceIntegerField(CounterTimePanel.COL_REPORT_TO, "Manager");  
		reportsToField.setForeignKey(getID() + "." + CounterTimePanel.COL_TASK_ID);  
		reportsToField.setRootValue(ROOT_VALUE);  
		list.add(reportsToField);

		DataSourceTextField nameField = new DataSourceTextField(CounterTimePanel.COL_NAME, "Nombre");  
		list.add(nameField);
		
		DataSourceTextField descField = new DataSourceTextField(CounterTimePanel.COL_DESCRIPTION, "Descripcion");
		list.add(descField);
		
		return list;
	}

	@Override
	public void copyValues(TreeNode from, TaskTimeDTO to) {
		// No haria falta ya que nunca se escribe el DTO
	}

	@Override
	public void copyValues(TaskTimeDTO from, TreeNode to) {
		to.setAttribute(CounterTimePanel.COL_TASK_ID, from.getId());  
		to.setAttribute(CounterTimePanel.COL_REPORT_TO, from.getTaskId()==null?ROOT_VALUE:from.getTaskId());  
		to.setAttribute(CounterTimePanel.COL_NAME, from.getName());  
		to.setAttribute(CounterTimePanel.COL_DESCRIPTION, from.getDescription());
	}

	@Override
	public TaskCounterServicesClientAsync getServiceAsync() {
		return TaskCounterServicesClient.Util.getInstance();
	}

	@Override
	public TreeNode getNewRecordInstance() {
		return new TreeNode();
	}

	@Override
	public TaskTimeDTO getNewDataObjectInstance() {
		return new TaskTimeDTO();
	}

	@Override  
    protected Object transformRequest(DSRequest dsRequest) {
		GWT.log("Buscando Tareas para el contador de horas");
		this.setId(SessionUser.getInstance().getUser().getId());
		return super.transformRequest(dsRequest);
    }  
}
