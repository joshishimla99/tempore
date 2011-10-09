package ar.fi.uba.tempore.gwt.client.panel.time;

import java.util.ArrayList;
import java.util.List;

import ar.fi.uba.tempore.dto.ProjectDTO;
import ar.fi.uba.tempore.dto.TaskDTO;
import ar.fi.uba.tempore.dto.TaskUserDTO;
import ar.fi.uba.tempore.dto.UserDTO;
import ar.fi.uba.tempore.gwt.client.TimeServicesClient;
import ar.fi.uba.tempore.gwt.client.TimeServicesClientAsync;
import ar.fi.uba.tempore.gwt.client.panel.project.ProjectPanel;
import ar.fi.uba.temporeutils.listgrid.GenericGwtRpcDataSource;

import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.fields.DataSourceDateField;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class HourCountDataSource extends GenericGwtRpcDataSource<TaskUserDTO, ListGridRecord, TimeServicesClientAsync> {

	public static final String COL_ID = "idCol";
//	private static final String COL_ID_USER = "useridCol";
//	private static final String COL_ID_TASK = "taskidCol";
	public static final String COL_HOURS = "hoursCol";
	public static final String COL_COMMENTS = "commentsCol";
	public static final String COL_DATE = "dateCol";
	public static final String COL_NAME = "nameCol";
	public static final String COL_TASK_ID = "taskId";
	public static final String COL_DESCRIPTION = "descriptionCol";
	public static final String COL_PROJECT_NAME = "projectCol";
	public static final String COL_PROJECT_ID = "projectIdCol";	

	@Override
	public List<DataSourceField> getDataSourceFields() {
		List<DataSourceField> fields = new ArrayList<DataSourceField>();
		
		DataSourceIntegerField field = new DataSourceIntegerField(COL_ID);
		field.setPrimaryKey(true);
		field.setHidden(true);
		fields.add(field);
		
		DataSourceIntegerField field3 = new DataSourceIntegerField(COL_HOURS, "Horas dedicadas");
		field3.setRequired(true);
		fields.add(field3);
		
		DataSourceTextField field4 = new DataSourceTextField(COL_COMMENTS, "Comentarios");
		field3.setRequired(true);
		fields.add(field4);
		
		DataSourceDateField field5 = new DataSourceDateField(COL_DATE, "Fecha asignaci&oacuten");
		field5.setRequired(true);
		fields.add(field5);
		
		DataSourceTextField field6 = new DataSourceTextField(COL_NAME, "Nombre Tarea");
		field6.setRequired(true);
		fields.add(field6);
		
		DataSourceTextField field7 = new DataSourceTextField(COL_DESCRIPTION, "Descripci&oacuten");
		field7.setRequired(true);
		fields.add(field7);
		
		DataSourceTextField field8 = new DataSourceTextField(COL_PROJECT_NAME, "Proyecto");
//		field8.setRequired(true);
		fields.add(field8);
		
		DataSourceIntegerField field9 = new DataSourceIntegerField(COL_PROJECT_ID, "projectId");
		field9.setRequired(false);
		field9.setHidden(true);
		fields.add(field9);
		
		DataSourceIntegerField field10 = new DataSourceIntegerField(COL_TASK_ID);
		field10.setHidden(true);
		fields.add(field10);
		
		return fields;
	}

	@Override
	public void copyValues(ListGridRecord rec, TaskUserDTO dto) {
		TaskDTO taskDTO = new TaskDTO();
		UserDTO userDTO = new UserDTO();
		ProjectDTO projectDTO = new ProjectDTO();
		
//		projectDTO.setName("JPG");
		projectDTO.setId(ProjectPanel.getInstance().getSelected().getId());
//		taskDTO.setId(2);
		taskDTO.setId(rec.getAttributeAsInt(COL_TASK_ID));
		taskDTO.setProject(projectDTO);
		userDTO.setId(1);
//		rec.getAttributeAsInt(COL_ID_USER)
		
//		SC.say("Id Tarea: " + rec.getAttributeAsString(COL_TASK_ID));
		
		
//		dto.setId(rec.getAttributeAsInt(COL_ID));
		dto.setUser(userDTO);
		dto.setTask(taskDTO);
		dto.setHourCount((rec.getAttributeAsInt(COL_HOURS)==null)?0:rec.getAttributeAsInt(COL_HOURS));
		dto.setComment("");	
//		dto.setDate(rec.getAttributeAsDate(COL_DATE));
	}

	@Override
	public void copyValues(TaskUserDTO dto, ListGridRecord rec) {
		rec.setAttribute(COL_ID, dto.getId());
//		rec.setAttribute(COL_ID_USER, dto.getUser());
		rec.setAttribute(COL_TASK_ID, dto.getTask().getId());
//		SC.say("ID TASK: " + dto.getTask().getId());
		rec.setAttribute(COL_HOURS, dto.getHourCount());
		rec.setAttribute(COL_COMMENTS, dto.getComment());
		rec.setAttribute(COL_DATE, dto.getDate());
		rec.setAttribute(COL_NAME, dto.getTask().getName());
		rec.setAttribute(COL_DESCRIPTION, dto.getTask().getDescription());
		rec.setAttribute(COL_PROJECT_NAME, dto.getTask().getProject().getName());
		rec.setAttribute(COL_PROJECT_ID, dto.getTask().getProject().getId().toString());
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