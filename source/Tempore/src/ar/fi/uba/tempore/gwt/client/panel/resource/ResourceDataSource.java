package ar.fi.uba.tempore.gwt.client.panel.resource;

import java.util.ArrayList;
import java.util.List;

import ar.fi.uba.tempore.dto.ProjectDTO;
import ar.fi.uba.tempore.dto.UserDTO;
import ar.fi.uba.tempore.dto.UserProjectDTO;
import ar.fi.uba.tempore.gwt.client.UserProjectServicesClient;
import ar.fi.uba.tempore.gwt.client.UserProjectServicesClientAsync;
import ar.fi.uba.tempore.gwt.client.panel.project.ProjectPanel;
import ar.fi.uba.temporeutils.listgrid.filter.GenericGwtRpcDataSourceFilterId;

import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.fields.DataSourceImageField;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class ResourceDataSource extends GenericGwtRpcDataSourceFilterId<Integer, UserProjectDTO, ListGridRecord, UserProjectServicesClientAsync> {

	private static ResourceDataSource instance = null;
	
	public static ResourceDataSource getInstance(){
		if (instance == null){
			instance = new ResourceDataSource();
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
		
		DataSourceTextField name = new DataSourceTextField(ResourceTabPanel.NAME);
		fields.add(name);
		DataSourceTextField lastName = new DataSourceTextField(ResourceTabPanel.LAST_NAME);
		fields.add(lastName);
		DataSourceTextField user = new DataSourceTextField(ResourceTabPanel.USER_NAME);
		fields.add(user);
		DataSourceTextField email = new DataSourceTextField(ResourceTabPanel.EMAIL);
		fields.add(email);
		
		DataSourceImageField imageName = new DataSourceImageField(ResourceTabPanel.IMAGE_NAME);
		imageName.setImageURLPrefix("http://localhost:8080/Tempore/imageServlet.img?id=");
		fields.add(imageName);
		
		DataSourceTextField owner = new DataSourceTextField(ResourceTabPanel.IS_OWNER);
		fields.add(owner);
		
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
		
		//Coloco los que no son owner
		//TODO si el que se arrastra es el owner decir que no
		dto.setOwner(0);
	}

	@Override
	public void copyValues(UserProjectDTO from, ListGridRecord to) {
		to.setAttribute(ResourceTabPanel.USER_PROJECT_ID, from.getId());
		to.setAttribute(ResourceTabPanel.IS_OWNER, from.getOwner());
		
		UserDTO user = from.getUser();
		to.setAttribute(ResourceTabPanel.USER_ID, user.getId());
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

	@Override  
    protected Object transformRequest(DSRequest dsRequest) {
		//GWT.log("Evento de DataSource - " + dsRequest.getOperationType());
		if (ProjectPanel.getInstance().getSelected() != null){
			this.setId(ProjectPanel.getInstance().getSelected().getId());		
		}
		return super.transformRequest(dsRequest);
    }  
}
