package ar.fi.uba.tempore.gwt.client.panel.time;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ar.fi.uba.tempore.dto.ProjectDTO;
import ar.fi.uba.tempore.dto.TaskDTO;
import ar.fi.uba.tempore.dto.TaskUserDTO;
import ar.fi.uba.tempore.dto.UserDTO;
import ar.fi.uba.tempore.gwt.client.TimeServicesClient;
import ar.fi.uba.tempore.gwt.client.TimeServicesClientAsync;
import ar.fi.uba.tempore.gwt.client.login.SessionUser;
import ar.fi.uba.tempore.gwt.client.panel.project.ProjectPanel;
import ar.fi.uba.temporeutils.listgrid.GenericGwtRpcDataSource;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.fields.DataSourceDateField;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.types.SummaryFunctionType;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class HourCountDataSource extends GenericGwtRpcDataSource<TaskUserDTO, ListGridRecord, TimeServicesClientAsync> {



	@Override
	public List<DataSourceField> getDataSourceFields() {
		List<DataSourceField> fields = new ArrayList<DataSourceField>();
		
		DataSourceIntegerField field = new DataSourceIntegerField(DragDropTimePanel.COL_ID);
		field.setPrimaryKey(true);
		field.setHidden(true);
		fields.add(field);
		
		DataSourceIntegerField field3 = new DataSourceIntegerField(DragDropTimePanel.COL_HOURS, "Horas dedicadas");
		field3.setRequired(true);
		field3.setPluralTitle("Horas"); 
		field3.setSummaryFunction(SummaryFunctionType.SUM); 
		fields.add(field3);
		
		DataSourceTextField field4 = new DataSourceTextField(DragDropTimePanel.COL_COMMENTS, "Comentarios");
		field3.setRequired(true);
		fields.add(field4);
		
		DataSourceDateField field5 = new DataSourceDateField(DragDropTimePanel.COL_DATE, "Fecha asignaci&oacuten");
		field5.setRequired(true);
		fields.add(field5);
		
		DataSourceTextField field6 = new DataSourceTextField(DragDropTimePanel.COL_NAME, "Nombre Tarea");
		field6.setRequired(true);
		fields.add(field6);
		
		DataSourceTextField field7 = new DataSourceTextField(DragDropTimePanel.COL_DESCRIPTION, "Descripci&oacuten");
		field7.setRequired(true);
		fields.add(field7);
		
		DataSourceTextField field8 = new DataSourceTextField(DragDropTimePanel.COL_PROJECT_NAME, "Proyecto");
//		field8.setRequired(true);
		fields.add(field8);
		
		DataSourceIntegerField field9 = new DataSourceIntegerField(DragDropTimePanel.COL_PROJECT_ID, "projectId");
		field9.setRequired(false);
		field9.setHidden(true);
		fields.add(field9);
		
		DataSourceIntegerField field10 = new DataSourceIntegerField(DragDropTimePanel.COL_TASK_ID);
		field10.setHidden(true);
		fields.add(field10);
		
		return fields;
	}

	@Override
	public void copyValues(ListGridRecord rec, TaskUserDTO dto) {
		TaskDTO taskDTO = new TaskDTO();
		UserDTO userDTO = new UserDTO();
		ProjectDTO projectDTO = new ProjectDTO();
		projectDTO.setName(ProjectPanel.getInstance().getSelected().getName());
		projectDTO.setId(ProjectPanel.getInstance().getSelected().getId());
		dto.setId(rec.getAttributeAsInt(DragDropTimePanel.COL_ID));	
		taskDTO.setId(rec.getAttributeAsInt(DragDropTimePanel.COL_TASK_ID));
		taskDTO.setName(rec.getAttributeAsString(DragDropTimePanel.COL_NAME));
		taskDTO.setDescription(rec.getAttributeAsString(DragDropTimePanel.COL_DESCRIPTION));
		taskDTO.setProject(projectDTO);
		GWT.log("id de tarea: " + rec.getAttributeAsInt(DragDropTimePanel.COL_TASK_ID));
		userDTO.setId(SessionUser.getInstance().getUser().getId());
		dto.setUser(userDTO);
		dto.setTask(taskDTO);
		dto.setHourCount((rec.getAttributeAsInt(DragDropTimePanel.COL_HOURS)==null)?0:rec.getAttributeAsInt(DragDropTimePanel.COL_HOURS));
		dto.setComment(rec.getAttribute(DragDropTimePanel.COL_COMMENTS));
		dto.setDate(rec.getAttributeAsDate(DragDropTimePanel.COL_DATE));
	}

	@Override
	public void copyValues(TaskUserDTO dto, ListGridRecord rec) {
		rec.setAttribute(DragDropTimePanel.COL_ID, dto.getId());
//		rec.setAttribute(COL_ID_USER, dto.getUser());
		rec.setAttribute(DragDropTimePanel.COL_TASK_ID, dto.getTask().getId());
//		SC.say("ID TASK: " + dto.getTask().getId());
		rec.setAttribute(DragDropTimePanel.COL_HOURS, dto.getHourCount());
		rec.setAttribute(DragDropTimePanel.COL_COMMENTS, dto.getComment());
		rec.setAttribute(DragDropTimePanel.COL_DATE, dto.getDate());
		rec.setAttribute(DragDropTimePanel.COL_NAME, dto.getTask().getName());
		rec.setAttribute(DragDropTimePanel.COL_DESCRIPTION, dto.getTask().getDescription());
		rec.setAttribute(DragDropTimePanel.COL_PROJECT_NAME, dto.getTask().getProject().getName());
		rec.setAttribute(DragDropTimePanel.COL_PROJECT_ID, dto.getTask().getProject().getId().toString());
	}

	@Override
	public TimeServicesClientAsync getServiceAsync() {		
		return TimeServicesClient.Util.getInstance();
	}

	@Override
	public ListGridRecord getNewRecordInstance() {
		return new ListGridRecord();
	}

	@Override
	public TaskUserDTO getNewDataObjectInstance() {
		return new TaskUserDTO();
	}
}