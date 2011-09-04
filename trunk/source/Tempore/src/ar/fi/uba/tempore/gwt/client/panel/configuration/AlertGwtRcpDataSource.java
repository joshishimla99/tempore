package ar.fi.uba.tempore.gwt.client.panel.configuration;


import java.util.ArrayList;
import java.util.List;

import ar.fi.uba.tempore.dto.AlertDTO;
import ar.fi.uba.tempore.gwt.client.AlertServicesClient;
import ar.fi.uba.tempore.gwt.client.AlertServicesClientAsync;
import ar.fi.uba.temporeutils.listgrid.GenericGwtRpcDataSource;

import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class AlertGwtRcpDataSource extends GenericGwtRpcDataSource<AlertDTO, ListGridRecord, AlertServicesClientAsync> {
	private static final String COL_ID = "idCol";
	private static final String COL_NAME = "nameCol";
	private static final String COL_DESCRIPTION = "descriptionCol";
	
	@Override
	public List<DataSourceField> getDataSourceFields() {
		List<DataSourceField> fields = new ArrayList<DataSourceField>();
		
		DataSourceTextField itemNameField = new DataSourceTextField(COL_NAME,"Alert Name");
		itemNameField.setRequired(true);
		fields.add(itemNameField);
		
		DataSourceTextField itemDescField = new DataSourceTextField(COL_DESCRIPTION,"Descripcion");
		itemDescField.setRequired(true);
		fields.add(itemDescField);

		DataSourceIntegerField idField = new DataSourceIntegerField(COL_ID, "Alert Id");
		idField.setPrimaryKey(true);
		idField.setHidden(true);
		fields.add(idField);
		
		return (fields);
	}

	@Override
	public void copyValues(ListGridRecord rec, AlertDTO dto) {
		dto.setDescription(rec.getAttribute(COL_DESCRIPTION));
		dto.setName(rec.getAttribute(COL_NAME));
		dto.setId(rec.getAttributeAsInt(COL_ID));
	}

	@Override
	public void copyValues(AlertDTO dto, ListGridRecord rec) {
		rec.setAttribute(COL_DESCRIPTION, dto.getDescription());
		rec.setAttribute(COL_NAME, dto.getName());
		rec.setAttribute(COL_ID, dto.getId());
	}

	@Override
	public AlertDTO getNewDataObjectInstance() {
		return new AlertDTO();
	}

	@Override
	public ListGridRecord getNewRecordInstance() {
		return new ListGridRecord();
	}

	@Override
	public AlertServicesClientAsync getServiceAsync() {
		return AlertServicesClient.Util.getInstance();
	}

}
