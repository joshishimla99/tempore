package ar.fi.uba.tempore.gwt.client.panel.help;

import java.util.ArrayList;
import java.util.List;

import ar.fi.uba.tempore.dto.TaskTimeDTO;
import ar.fi.uba.tempore.gwt.client.TaskCounterServicesClient;
import ar.fi.uba.tempore.gwt.client.TaskCounterServicesClientAsync;
import ar.fi.uba.tempore.gwt.client.login.SessionUser;
import ar.fi.uba.temporeutils.listgrid.filter.GenericGwtRpcDataSourceFilterId;

import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.widgets.tree.TreeNode;

public class TaskListDS extends GenericGwtRpcDataSourceFilterId<Integer, TaskTimeDTO, TreeNode, TaskCounterServicesClientAsync>  {
	private static TaskListDS instance = null;  
	private static final Integer ROOT_VALUE = 0;
	
	public static TaskListDS getInstance() {  
		if (instance == null) {  
			instance = new TaskListDS();  
		}  
		return instance;  
	}  

	@Override
	public List<DataSourceField> getDataSourceFields() {
		List<DataSourceField> list = new ArrayList<DataSourceField>();
		
		DataSourceIntegerField taskIdField = new DataSourceIntegerField(HelpTabPanel.COL_TASK_ID, "ID");  
		taskIdField.setPrimaryKey(true); 
		list.add(taskIdField);
		
		
		DataSourceIntegerField reportsToField = new DataSourceIntegerField(HelpTabPanel.COL_REPORT_TO, "Manager");  
		reportsToField.setForeignKey(getID() + "." + HelpTabPanel.COL_TASK_ID);  
		reportsToField.setRootValue(ROOT_VALUE);  
		list.add(reportsToField);

		DataSourceTextField nameField = new DataSourceTextField(HelpTabPanel.COL_NAME, "Nombre");  
		list.add(nameField);
		
		DataSourceTextField descField = new DataSourceTextField(HelpTabPanel.COL_DESCRIPTION, "Descripcion");
		list.add(descField);
		
		return list;
	}

	@Override
	public void copyValues(TreeNode from, TaskTimeDTO to) {
		// No haria falta ya que nunca se escribe el DTO
	}

	@Override
	public void copyValues(TaskTimeDTO from, TreeNode to) {
		to.setAttribute(HelpTabPanel.COL_TASK_ID, from.getId());  
		to.setAttribute(HelpTabPanel.COL_REPORT_TO, from.getTaskId()==null?ROOT_VALUE:from.getTaskId());  
		to.setAttribute(HelpTabPanel.COL_NAME, from.getName());  
		to.setAttribute(HelpTabPanel.COL_DESCRIPTION, from.getDescription());
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
		this.setId(SessionUser.getInstance().getUser().getId());
		return super.transformRequest(dsRequest);
    }  
}