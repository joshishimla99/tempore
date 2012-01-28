package ar.fi.uba.tempore.gwt.client.panel.time;

import java.util.ArrayList;
import java.util.List;

import ar.fi.uba.tempore.dto.TaskTimeDTO;
import ar.fi.uba.tempore.gwt.client.TaskTimeServicesClient;
import ar.fi.uba.tempore.gwt.client.TaskTimeServicesClientAsync;
import ar.fi.uba.tempore.gwt.client.panel.project.ProjectPanel;
import ar.fi.uba.temporeutils.listgrid.filter.GenericGwtRpcDataSourceFilterId;

import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.widgets.tree.TreeNode;

public class TaskTimeDataSource extends GenericGwtRpcDataSourceFilterId<Integer, TaskTimeDTO, TreeNode, TaskTimeServicesClientAsync> {
	private static final Integer ROOT_ID = 0;
	private static TaskTimeDataSource instance = null;

	public static TaskTimeDataSource getInstance(){
		if (instance == null){
			instance = new TaskTimeDataSource();
		}
		return instance;
	}
	
	private TaskTimeDataSource(){
		super();
	}

	@Override
	public List<DataSourceField> getDataSourceFields() {
		setID("TreeTask");
		List<DataSourceField> fields = new ArrayList<DataSourceField>();
		
		DataSourceIntegerField key = new DataSourceIntegerField(DragDropTimePanel.COL_TASK_ID, "Key");
		key.setPrimaryKey(true);
		fields.add(key);
		
		DataSourceIntegerField reportTo = new DataSourceIntegerField(DragDropTimePanel.COL_PARENT_ID,"ReportTo");
		reportTo.setForeignKey(getID() + "." + DragDropTimePanel.COL_TASK_ID);
		reportTo.setRootValue(ROOT_ID);
		fields.add(reportTo);

		DataSourceTextField nameField = new DataSourceTextField(DragDropTimePanel.COL_NAME, "Tarea");
		nameField.setRequired(true);
		fields.add(nameField);
		
		DataSourceTextField descriptionField = new DataSourceTextField(DragDropTimePanel.COL_DESCRIPTION, "Descripci&oacuten");
		descriptionField.setRequired(true);
		fields.add(descriptionField);
		
		return fields;
	}

	@Override
	public void copyValues(TreeNode rec, TaskTimeDTO dto) {
		dto.setId(rec.getAttributeAsInt(DragDropTimePanel.COL_TASK_ID));
		dto.setName(rec.getAttribute(DragDropTimePanel.COL_NAME));
		dto.setDescription(rec.getAttribute(DragDropTimePanel.COL_DESCRIPTION));	
		dto.setTaskId(rec.getAttributeAsInt(DragDropTimePanel.COL_PARENT_ID));
	}

	@Override
	public void copyValues(TaskTimeDTO dto, TreeNode rec) {
//		GWT.log("Id="+dto.getId() + " - report = " + dto.getTaskId());
		rec.setAttribute(DragDropTimePanel.COL_TASK_ID, dto.getId());
		rec.setAttribute(DragDropTimePanel.COL_PARENT_ID, dto.getTaskId()==null?ROOT_ID:dto.getTaskId());

		rec.setAttribute(DragDropTimePanel.COL_NAME, dto.getName());
		rec.setAttribute(DragDropTimePanel.COL_DESCRIPTION, dto.getDescription());
	}

	@Override
	public TaskTimeServicesClientAsync getServiceAsync() {		
		return TaskTimeServicesClient.Util.getInstance();
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
		if (ProjectPanel.getInstance().getSelected() != null) {
			this.setId(ProjectPanel.getInstance().getSelected().getId());
		} else {
			//TODO ver si se puede solucionar de otra manera
			this.setId(1);
		}
		return super.transformRequest(dsRequest);
    }  
}