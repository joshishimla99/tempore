package ar.fi.uba.tempore.gwt.client.panel.configuration;

import java.util.ArrayList;
import java.util.List;

import ar.fi.uba.tempore.dto.UserDTO;
import ar.fi.uba.tempore.gwt.client.UserServicesClient;
import ar.fi.uba.tempore.gwt.client.UserServicesClientAsync;
import ar.fi.uba.temporeutils.listgrid.GenericGwtRpcDataSource;

import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourcePasswordField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class UserConfigurationDataSource extends GenericGwtRpcDataSource<UserDTO, ListGridRecord, UserServicesClientAsync> {

	private static final String ID = "idCol";
	private static final String NAME = "nameCol";
	private static final String LAST_NAME = "lastNameCol";
	private static final String USER_NAME = "userNameCol";
	private static final String PASSWORD = "passwordCol";
	private static final String EMAIL = "emailCol";
	private static final String ADDRESS = "addressCol";
	private static final String COMPANY = "companyCol";
	private static final String COUNTRY = "countryCol";
	private static final String PHONE = "phoneCol";
	private static final String ZIP_CODE = "zipCodeCol";

	@Override
	public List<DataSourceField> getDataSourceFields() {
		List<DataSourceField> fields = new ArrayList<DataSourceField>();
		
		DataSourceIntegerField field = new DataSourceIntegerField(ID);
		field.setPrimaryKey(true);
		field.setHidden(true);
		fields.add(field);
		
		DataSourceTextField field1 = new DataSourceTextField(NAME, "Nombre");
		field1.setRequired(true);
		fields.add(field1);
		DataSourceTextField field2 = new DataSourceTextField(LAST_NAME, "Apellido");
		field2.setRequired(true);
		fields.add(field2);
		DataSourceTextField field3 = new DataSourceTextField(USER_NAME, "Usuario");
		field3.setRequired(true);
		fields.add(field3);
		DataSourcePasswordField field4 = new DataSourcePasswordField(PASSWORD, "Contraseña");
		field4.setRequired(true);
		fields.add(field4);		
		DataSourceTextField field7 = new DataSourceTextField(EMAIL, "Email");
		field7.setRequired(true);
		fields.add(field7);
		DataSourceTextField field8 = new DataSourceTextField(ADDRESS, "Direccion");
		field8.setRequired(true);
		fields.add(field8);		
		DataSourceTextField field9 = new DataSourceTextField(COMPANY, "Empresa");
		field9.setRequired(true);
		fields.add(field9);
		DataSourceTextField field10 = new DataSourceTextField(COUNTRY, "Pais");
		field10.setRequired(true);
		fields.add(field10);
		DataSourceTextField field11 = new DataSourceTextField(PHONE, "Telefono");
		field11.setRequired(true);
		fields.add(field11);
		DataSourceTextField field12 = new DataSourceTextField(ZIP_CODE, "Codogo Postal");
		field12.setRequired(true);
		fields.add(field12);
		
		
		return fields;
	}

	@Override
	public void copyValues(ListGridRecord rec, UserDTO dto) {
		dto.setId(rec.getAttributeAsInt(ID));
		dto.setAddress(rec.getAttribute(ADDRESS));
		//dto.setClient(rec.getAttributeAsBoolean(IS_CLIENT));
		dto.setCompany(rec.getAttribute(COMPANY));
		dto.setCountry(rec.getAttribute(COUNTRY));
		dto.setEmail(rec.getAttribute(EMAIL));
		dto.setLastName(rec.getAttribute(LAST_NAME));
		dto.setName(rec.getAttribute(NAME));
		dto.setPassword(rec.getAttribute(PASSWORD));
		dto.setPhone(rec.getAttribute(PHONE));		
		dto.setUserName(rec.getAttribute(USER_NAME));
		dto.setZipCode(rec.getAttribute(ZIP_CODE));		
	}

	@Override
	public void copyValues(UserDTO dto, ListGridRecord rec) {
		rec.setAttribute(ID, dto.getId());
		rec.setAttribute(ADDRESS, dto.getAddress());
		//rec.setAttribute(IS_CLIENT, dto.isClient());
		rec.setAttribute(COMPANY, dto.getCompany());
		rec.setAttribute(COUNTRY, dto.getCountry());
		rec.setAttribute(EMAIL, dto.getEmail());
		rec.setAttribute(LAST_NAME, dto.getLastName());
		rec.setAttribute(NAME, dto.getName());
		rec.setAttribute(PASSWORD, dto.getPassword());
		rec.setAttribute(PHONE, dto.getPhone());		
		rec.setAttribute(USER_NAME, dto.getUserName());
		rec.setAttribute(ZIP_CODE, dto.getZipCode());
	}

	@Override
	public UserServicesClientAsync getServiceAsync() {		
		return UserServicesClient.Util.getInstance();
	}

	@Override
	public ListGridRecord getNewRecordInstance() {
		return new ListGridRecord();
	}

	@Override
	public UserDTO getNewDataObjectInstance() {
		return new UserDTO();
	}
}