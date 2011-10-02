package ar.fi.uba.tempore.gwt.client.panel.resource;

import java.util.ArrayList;
import java.util.List;

import ar.fi.uba.tempore.dto.ProjectDTO;
import ar.fi.uba.tempore.dto.UserDTO;
import ar.fi.uba.tempore.dto.UserProjectDTO;
import ar.fi.uba.tempore.gwt.client.UserProjectServicesClient;
import ar.fi.uba.tempore.gwt.client.UserProjectServicesClientAsync;
import ar.fi.uba.tempore.gwt.client.panel.project.ProjectPanel;
import ar.fi.uba.temporeutils.listgrid.GenericGwtRpcDataSource;

import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.fields.DataSourceImageField;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class UserProjectDataSource extends GenericGwtRpcDataSource<UserProjectDTO, ListGridRecord, UserProjectServicesClientAsync> {

	private static UserProjectDataSource instance = null;
	
	public static UserProjectDataSource getInstance(){
		if (instance == null){
			instance = new UserProjectDataSource();
		}
		return instance;
	}
	
	@Override
	public List<DataSourceField> getDataSourceFields() {
		List<DataSourceField> fields = new ArrayList<DataSourceField>();
		
		DataSourceIntegerField field = new DataSourceIntegerField(ResourceTabPanel.USER_PROJECT_ID);
		field.setPrimaryKey(true);
		field.setHidden(true);
		fields.add(field);
		
		DataSourceIntegerField userId = new DataSourceIntegerField(ResourceTabPanel.USER_ID);
		field.setHidden(true);
		fields.add(userId);
		
		DataSourceTextField name = new DataSourceTextField(ResourceTabPanel.NAME, "Nombre");
		fields.add(name);
		DataSourceTextField lastName = new DataSourceTextField(ResourceTabPanel.LAST_NAME, "Apellido");
		fields.add(lastName);
		DataSourceTextField user = new DataSourceTextField(ResourceTabPanel.USER_NAME, "Usuario");
		fields.add(user);
		DataSourceTextField email = new DataSourceTextField(ResourceTabPanel.EMAIL, "Email");
		fields.add(email);
		DataSourceImageField imageName = new DataSourceImageField(ResourceTabPanel.IMAGE_NAME, "Codigo Imagen");
		imageName.setImageURLPrefix("http://localhost:8080/Tempore/imageServlet.img?id=");
		fields.add(imageName);
		
		return fields;

	}

	@Override
	public void copyValues(ListGridRecord from, UserProjectDTO dto) {
		dto.setId(from.getAttributeAsInt(ResourceTabPanel.USER_PROJECT_ID));
		
		UserDTO user = new UserDTO();
		user.setId(from.getAttributeAsInt(ResourceTabPanel.USER_ID));
		dto.setUser(user);
		
		ProjectDTO project = new ProjectDTO();
		project.setId(ProjectPanel.getInstance().getSelected().getId());
		dto.setProject(project);
	}

	@Override
	public void copyValues(UserProjectDTO from, ListGridRecord to) {
		to.setAttribute(ResourceTabPanel.USER_PROJECT_ID, from.getId());
		
		UserDTO user = from.getUser();
		to.setAttribute(ResourceTabPanel.EMAIL, user.getEmail());
		to.setAttribute(ResourceTabPanel.LAST_NAME, user.getLastName());
		to.setAttribute(ResourceTabPanel.NAME, user.getName());
		to.setAttribute(ResourceTabPanel.USER_NAME, user.getUserName());
		to.setAttribute(ResourceTabPanel.IMAGE_NAME, user.getImageName());		
	}

	@Override
	public UserProjectServicesClientAsync getServiceAsync() {
		return UserProjectServicesClient.Util.getInstance();
	}

	@Override
	public ListGridRecord getNewRecordInstance() {
		return new ListGridRecord();
	}

	@Override
	public UserProjectDTO getNewDataObjectInstance() {
		return new UserProjectDTO();
	}

}
