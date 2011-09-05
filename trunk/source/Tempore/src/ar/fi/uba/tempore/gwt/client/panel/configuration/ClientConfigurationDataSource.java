package ar.fi.uba.tempore.gwt.client.panel.configuration;

import java.util.ArrayList;
import java.util.List;

import ar.fi.uba.tempore.dto.ClientDTO;
import ar.fi.uba.tempore.gwt.client.ClientServicesClient;
import ar.fi.uba.tempore.gwt.client.ClientServicesClientAsync;
import ar.fi.uba.temporeutils.listgrid.GenericGwtRpcDataSource;

import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class ClientConfigurationDataSource extends GenericGwtRpcDataSource<ClientDTO, ListGridRecord, ClientServicesClientAsync>{

	private static final String COL_ID = "idCol";
	private static final String COL_NAME = "nameCol";
	private static final String COL_ADDRESS = "addressCol";
	private static final String COL_COUNTRY = "countryCol";
	private static final String COL_STATE = "stateCol";
	private static final String COL_PHONE = "phoneCol";
	private static final String COL_FISCAL_NUMBER = "fiscalCol";
	private static final String COL_ZIP = "zipCol";
	

	@Override
	public List<DataSourceField> getDataSourceFields() {
		List<DataSourceField> fields = new ArrayList<DataSourceField>();
		
		DataSourceTextField itemField1 = new DataSourceTextField(COL_NAME,"Nombre");
		itemField1.setRequired(true);
		fields.add(itemField1);
		
		DataSourceTextField itemField2 = new DataSourceTextField(COL_ADDRESS,"Direccion");
		itemField2.setRequired(true);
		fields.add(itemField2);
		
		DataSourceTextField itemField3 = new DataSourceTextField(COL_STATE,"Provincia");
		itemField3.setRequired(true);
		fields.add(itemField3);

		DataSourceTextField itemField4 = new DataSourceTextField(COL_COUNTRY,"Pais");
		itemField4.setRequired(true);
		fields.add(itemField4);
		
		
		DataSourceTextField itemField5 = new DataSourceTextField(COL_PHONE,"Telefono");
		//itemField5.setRequired(true);
		fields.add(itemField5);
		
		DataSourceTextField itemField6 = new DataSourceTextField(COL_FISCAL_NUMBER,"CUIT");
		//itemField6.setRequired(true);
		fields.add(itemField6);
		
		DataSourceTextField itemField7 = new DataSourceTextField(COL_ZIP,"Codigo Postal");
		//itemField7.setRequired(true);
		fields.add(itemField7);
		
		
		DataSourceIntegerField idField = new DataSourceIntegerField(COL_ID);
		idField.setPrimaryKey(true);
		idField.setHidden(true);
		fields.add(idField);
		
		return fields;
	}

	@Override
	public void copyValues(ListGridRecord rec, ClientDTO dto) {
		dto.setAddress(rec.getAttribute(COL_ADDRESS));
		dto.setCountry(rec.getAttribute(COL_COUNTRY));
		dto.setFiscalNumber(rec.getAttribute(COL_FISCAL_NUMBER));
		dto.setName(rec.getAttribute(COL_NAME));
		dto.setPhone(rec.getAttribute(COL_PHONE));
		dto.setState(rec.getAttribute(COL_STATE));
		dto.setZip(rec.getAttribute(COL_ZIP));
		dto.setId(rec.getAttributeAsInt(COL_ID));
	}

	@Override
	public void copyValues(ClientDTO dto, ListGridRecord rec) {
		rec.setAttribute(COL_ADDRESS, dto.getAddress());
		rec.setAttribute(COL_COUNTRY, dto.getCountry());
		rec.setAttribute(COL_FISCAL_NUMBER, dto.getFiscalNumber());
		rec.setAttribute(COL_NAME, dto.getName());
		rec.setAttribute(COL_PHONE, dto.getPhone());
		rec.setAttribute(COL_STATE, dto.getState());
		rec.setAttribute(COL_ZIP, dto.getZip());
		rec.setAttribute(COL_ID, dto.getId());
	}

	@Override
	public ClientServicesClientAsync getServiceAsync() {		
		return ClientServicesClient.Util.getInstance();
	}

	@Override
	public ListGridRecord getNewRecordInstance() {
		return new ListGridRecord();
	}

	@Override
	public ClientDTO getNewDataObjectInstance() {
		return new ClientDTO();
	}
}
