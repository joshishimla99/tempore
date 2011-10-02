package ar.fi.uba.tempore.gwt.client.panel.configuration;

import java.util.ArrayList;
import java.util.List;

import ar.fi.uba.tempore.dto.TaskTimeDTO;
import ar.fi.uba.tempore.gwt.client.TaskTimeServicesClient;
import ar.fi.uba.tempore.gwt.client.TaskTimeServicesClientAsync;
import ar.fi.uba.temporeutils.listgrid.filter.GenericGwtRpcDataSourceFilterId;

import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class TaskTimeDataSource extends GenericGwtRpcDataSourceFilterId<TaskTimeDTO, ListGridRecord, TaskTimeServicesClientAsync> {

	public static final String COL_ID = "idCol";
	public static final String COL_NAME = "nameCol";
	public static final String COL_DESCRIPTION = "descriptionCol";
	public static final String COL_PARENT_ID = "idParentCol";

	@Override
	public List<DataSourceField> getDataSourceFields() {
		List<DataSourceField> fields = new ArrayList<DataSourceField>();
		
		DataSourceIntegerField field = new DataSourceIntegerField(COL_ID);
		field.setPrimaryKey(true);
//		field.setHidden(true);
		fields.add(field);
		
		DataSourceTextField field2 = new DataSourceTextField(COL_NAME, "Nombre de la Tarea");
		field2.setRequired(true);
		fields.add(field2);
		
		DataSourceTextField field3 = new DataSourceTextField(COL_DESCRIPTION, "Descripci&oacuten");
		field3.setRequired(true);
		fields.add(field3);
		
		DataSourceIntegerField field4 = new DataSourceIntegerField(COL_PARENT_ID);
		field4.setForeignKey(getID() + "." + COL_ID);
		field4.setRootValue(-1);
//		field.setHidden(true);
		fields.add(field4);
		
		return fields;
	}

	@Override
	public void copyValues(ListGridRecord rec, TaskTimeDTO dto) {
		dto.setId(rec.getAttributeAsInt(COL_ID));
		dto.setName(rec.getAttribute(COL_NAME));
		dto.setDescription(rec.getAttribute(COL_DESCRIPTION));	
		dto.setTaskId(rec.getAttributeAsInt(COL_PARENT_ID));
	}

	@Override
	public void copyValues(TaskTimeDTO dto, ListGridRecord rec) {
		rec.setAttribute(COL_ID, dto.getId());
		rec.setAttribute(COL_NAME, dto.getName());
		rec.setAttribute(COL_DESCRIPTION, dto.getDescription());
		rec.setAttribute(COL_PARENT_ID, dto.getTaskId()==null?-1:dto.getTaskId());

		
	}

	@Override
	public TaskTimeServicesClientAsync getServiceAsync() {		
		return TaskTimeServicesClient.Util.getInstance();
	}

	@Override
	public ListGridRecord getNewRecordInstance() {
		return new ListGridRecord();
	}

	@Override
	public TaskTimeDTO getNewDataObjectInstance() {
		return new TaskTimeDTO();
	}
}