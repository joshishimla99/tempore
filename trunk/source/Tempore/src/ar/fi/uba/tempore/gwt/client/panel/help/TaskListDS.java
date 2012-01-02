package ar.fi.uba.tempore.gwt.client.panel.help;

import java.util.ArrayList;
import java.util.List;

import ar.fi.uba.tempore.dto.TaskTimeDTO;
import ar.fi.uba.tempore.gwt.client.TaskTimeServicesClient;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.types.DSDataFormat;
import com.smartgwt.client.types.DSProtocol;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.tree.TreeNode;

public class TaskListDS extends DataSource {
	private static TaskListDS instance = null;  

	public static TaskListDS getInstance() {  
		if (instance == null) {  
			instance = new TaskListDS("tareasDS");  
		}  
		return instance;  
	}  

	public TaskListDS(String id) {  

		setDataProtocol (DSProtocol.CLIENTCUSTOM);
		setDataFormat (DSDataFormat.CUSTOM);
		setClientOnly(false);
		
		setID(id);  
		setTitleField("Name");  

		DataSourceIntegerField taskIdField = new DataSourceIntegerField(HelpTabPanel.COL_TASK_ID, "ID");  
		taskIdField.setPrimaryKey(true);  
		taskIdField.setRequired(true);  

		DataSourceIntegerField reportsToField = new DataSourceIntegerField(HelpTabPanel.COL_REPORT_TO, "Manager");  
		reportsToField.setRequired(true);  
		reportsToField.setForeignKey(id + "." + HelpTabPanel.COL_TASK_ID);  
		reportsToField.setRootValue(0);  

		DataSourceTextField nameField = new DataSourceTextField(HelpTabPanel.COL_NAME, "Nombre");  
		DataSourceTextField descField = new DataSourceTextField(HelpTabPanel.COL_DESCRIPTION, "Descripcion");  

		this.setFields(taskIdField, reportsToField, nameField, descField);  
	}

	@Override
	protected Object transformRequest (DSRequest request) {
		String requestId = request.getRequestId ();
		DSResponse response = new DSResponse ();
		response.setAttribute ("clientContext", request.getAttributeAsObject ("clientContext"));
		// Asume success
		response.setStatus (0);
		switch (request.getOperationType ()) {
		case FETCH:
			GWT.log("FETCH DataSource Task");
			executeFetch (requestId, request, response);
			break;
		 case ADD:
             GWT.log("executeAdd (requestId, request, response);");
             break;
         case UPDATE:
        	 GWT.log("executeUpdate (requestId, request, response);");
             break;
         case REMOVE:
        	 GWT.log("executeRemove (requestId, request, response);");
             break;
		default:
			// Operation not implemented.
			
			break;
		}
		return request.getData ();
	}

	protected void executeFetch(final String requestId,final  DSRequest request, final DSResponse response) {

		TaskTimeServicesClient.Util.getInstance().fetch(1, new AsyncCallback<List<TaskTimeDTO>>() {
			@Override
			public void onSuccess(List<TaskTimeDTO> result) {
				List<TreeNode> list = new ArrayList<TreeNode>();
				for (TaskTimeDTO d : result) {
					TreeNode node = new TreeNode();
					
					node.setAttribute(HelpTabPanel.COL_TASK_ID, d.getId());  
					node.setAttribute(HelpTabPanel.COL_REPORT_TO, d.getTaskId()==null?0:d.getTaskId());  
					node.setAttribute(HelpTabPanel.COL_NAME, d.getName());  
					node.setAttribute(HelpTabPanel.COL_DESCRIPTION, d.getDescription());  
					
					list.add(node);
				}
				response.setData(list.toArray(new TreeNode[list.size()]));
				processResponse(requestId, response);
			}

			@Override
			public void onFailure(Throwable caught) {
				SC.warn("Error al buscar las tareas");
			}

		});
	}

}
