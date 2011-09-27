package ar.fi.uba.tempore.gwt.client.panel.configuration;

import java.util.ArrayList;
import java.util.List;

import ar.fi.uba.tempore.dto.UserDTO;
import ar.fi.uba.tempore.gwt.client.UserServicesClient;
import ar.fi.uba.tempore.gwt.client.UserServicesClientAsync;
import ar.fi.uba.temporeutils.listgrid.GenericGwtRpcDataSource;

import com.google.gwt.user.client.Window;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourcePasswordField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class UserConfigurationDataSource extends GenericGwtRpcDataSource<UserDTO, ListGridRecord, UserServicesClientAsync> {

	private static UserConfigurationDataSource instance;

	public static final String ID = "idCol";
	public static final String NAME = "nameCol";
	public static final String LAST_NAME = "lastNameCol";
	public static final String USER_NAME = "userNameCol";
	public static final String PASSWORD = "passwordCol";
	public static final String EMAIL = "emailCol";
	public static final String ADDRESS = "addressCol";
	public static final String COMPANY = "companyCol";
	public static final String COUNTRY = "countryCol";
	public static final String PHONE = "phoneCol";
	public static final String ZIP_CODE = "zipCodeCol";
	public static final String IMAGE_NAME = "imageName";
	//TODO falta saber si es cliente o no
	

	public static UserConfigurationDataSource getInstance(){
		if (instance == null){
			instance = new UserConfigurationDataSource();
		}
		return instance;
	}
	
	@Override
	public List<DataSourceField> getDataSourceFields() {
		List<DataSourceField> fields = new ArrayList<DataSourceField>();
		
		DataSourceIntegerField field = new DataSourceIntegerField(ID);
		field.setPrimaryKey(true);
		field.setHidden(true);
		fields.add(field);
		
		DataSourceTextField name = new DataSourceTextField(NAME, "Nombre");
		name.setRequired(true);
		fields.add(name);
		DataSourceTextField lastName = new DataSourceTextField(LAST_NAME, "Apellido");
		lastName.setRequired(true);
		fields.add(lastName);
		DataSourceTextField user = new DataSourceTextField(USER_NAME, "Usuario");
		user.setRequired(true);
		fields.add(user);
		DataSourcePasswordField password = new DataSourcePasswordField(PASSWORD, "Contrase&ntilde;a");
		password.setRequired(true);
		fields.add(password);		
		DataSourceTextField email = new DataSourceTextField(EMAIL, "Email");
		email.setRequired(true);
		fields.add(email);
		DataSourceTextField address = new DataSourceTextField(ADDRESS, "Direcci&oacute;n");
		address.setRequired(false);
		fields.add(address);		
		DataSourceTextField country = new DataSourceTextField(COUNTRY, "Pais");
		country.setRequired(false);
		fields.add(country);
		DataSourceTextField phone = new DataSourceTextField(PHONE, "Tel&eacute;fono");
		phone.setRequired(false);
		fields.add(phone);
		DataSourceTextField zipCode = new DataSourceTextField(ZIP_CODE, "C&oacute;digo Postal");
		zipCode.setRequired(false);
		fields.add(zipCode);
		DataSourceField imageName = new DataSourceTextField(IMAGE_NAME, "Codigo Imagen");
		imageName.setRequired(false);
		imageName.setHidden(true);
		fields.add(imageName);
		
		return fields;
	}

	@Override
	public void copyValues(ListGridRecord rec, UserDTO dto) {
		dto.setId(rec.getAttributeAsInt(ID));
		dto.setAddress(rec.getAttribute(ADDRESS));
		dto.setCompany(rec.getAttribute(COMPANY));
		dto.setCountry(rec.getAttribute(COUNTRY));
		dto.setEmail(rec.getAttribute(EMAIL));
		dto.setLastName(rec.getAttribute(LAST_NAME));
		dto.setName(rec.getAttribute(NAME));
		dto.setPassword(rec.getAttribute(PASSWORD));
		dto.setPhone(rec.getAttribute(PHONE));		
		dto.setUserName(rec.getAttribute(USER_NAME));
		dto.setZipCode(rec.getAttribute(ZIP_CODE));		
		dto.setImageName(rec.getAttribute(IMAGE_NAME));
	}

	@Override
	public void copyValues(UserDTO dto, ListGridRecord rec) {
		rec.setAttribute(ID, dto.getId());
		rec.setAttribute(ADDRESS, dto.getAddress());
		rec.setAttribute(COMPANY, dto.getCompany());
		rec.setAttribute(COUNTRY, dto.getCountry());
		rec.setAttribute(EMAIL, dto.getEmail());
		rec.setAttribute(LAST_NAME, dto.getLastName());
		rec.setAttribute(NAME, dto.getName());
		rec.setAttribute(PASSWORD, dto.getPassword());
		rec.setAttribute(PHONE, dto.getPhone());		
		rec.setAttribute(USER_NAME, dto.getUserName());
		rec.setAttribute(ZIP_CODE, dto.getZipCode());
		rec.setAttribute(IMAGE_NAME, dto.getImageName());
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