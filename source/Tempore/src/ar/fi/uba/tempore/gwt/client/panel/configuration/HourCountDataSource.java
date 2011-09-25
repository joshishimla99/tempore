package ar.fi.uba.tempore.gwt.client.panel.configuration;

import java.util.ArrayList;
import java.util.List;

import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

import ar.fi.uba.tempore.dto.TaskUserDTO;
import ar.fi.uba.tempore.gwt.client.TimeServicesClient;
import ar.fi.uba.tempore.gwt.client.TimeServicesClientAsync;
import ar.fi.uba.temporeutils.listgrid.GenericGwtRpcDataSource;

public class HourCountDataSource extends GenericGwtRpcDataSource<TaskUserDTO, ListGridRecord, TimeServicesClientAsync> {

	private static final String COL_ID = "idCol";
//	private static final String COL_ID_USER = "useridCol";
//	private static final String COL_ID_TASK = "taskidCol";
	private static final String COL_HOURS = "hoursCol";
	private static final String COL_COMMENTS = "commentsCol";

	@Override
	public List<DataSourceField> getDataSourceFields() {
		List<DataSourceField> fields = new ArrayList<DataSourceField>();
		
		DataSourceIntegerField field = new DataSourceIntegerField(COL_ID);
		field.setPrimaryKey(true);
//		field.setHidden(true);
		fields.add(field);
		
//		DataSourceIntegerField field1 = new DataSourceIntegerField(COL_ID_USER, "useridCol");
//		field1.setRequired(true);
//		fields.add(field1);
//		
//		DataSourceIntegerField field2 = new DataSourceIntegerField(COL_ID_TASK, "taskidCol");
//		field2.setRequired(true);
//		fields.add(field2);
		
		DataSourceIntegerField field3 = new DataSourceIntegerField(COL_HOURS, "hoursCol");
		field3.setRequired(true);
		fields.add(field3);
		
		DataSourceTextField field4 = new DataSourceTextField(COL_COMMENTS, "commentsCol");
		field3.setRequired(true);
		fields.add(field4);
		
		return fields;
	}

	@Override
	public void copyValues(ListGridRecord rec, TaskUserDTO dto) {
		dto.setId(rec.getAttributeAsInt(COL_ID));
//		dto.setUser(rec.getAttributeAsInt(COL_ID_USER));
//		dto.setTask(rec.getAttributeAsInt(COL_ID_TASK));
		dto.setHourCount(rec.getAttributeAsInt(COL_HOURS));
		dto.setComment(rec.getAttribute(COL_COMMENTS));	
	}

	@Override
	public void copyValues(TaskUserDTO dto, ListGridRecord rec) {
		rec.setAttribute(COL_ID, dto.getId());
//		rec.setAttribute(COL_ID_USER, dto.getUser());
//		rec.setAttribute(COL_ID_TASK, dto.getTask());
		rec.setAttribute(COL_HOURS, dto.getHourCount());
		rec.setAttribute(COL_COMMENTS, dto.getComment());
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