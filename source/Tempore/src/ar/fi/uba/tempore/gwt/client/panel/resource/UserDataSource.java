package ar.fi.uba.tempore.gwt.client.panel.resource;

import java.util.ArrayList;
import java.util.List;

import ar.fi.uba.tempore.dto.UserDTO;
import ar.fi.uba.tempore.gwt.client.UserServicesClient;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.fields.DataSourceImageField;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.rpc.RPCResponse;
import com.smartgwt.client.types.DSDataFormat;
import com.smartgwt.client.types.DSOperationType;
import com.smartgwt.client.types.DSProtocol;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class UserDataSource extends DataSource {

	private static UserDataSource instance;	

	public static UserDataSource getInstance(){
		if (instance == null){
			instance = new UserDataSource();
		}
		return instance;
	}
	
	public UserDataSource (){
		setDataProtocol (DSProtocol.CLIENTCUSTOM);
        setDataFormat (DSDataFormat.CUSTOM);
        setClientOnly (false);
        
        DataSourceIntegerField field = new DataSourceIntegerField(ResourceTabPanel.USER_ID);
		field.setPrimaryKey(true);
		field.setHidden(true);
		addField(field);
		
		DataSourceTextField name = new DataSourceTextField(ResourceTabPanel.NAME, "Nombre");
		addField(name);
		DataSourceTextField lastName = new DataSourceTextField(ResourceTabPanel.LAST_NAME, "Apellido");
		addField(lastName);
		DataSourceTextField user = new DataSourceTextField(ResourceTabPanel.USER_NAME, "Usuario");
		addField(user);
		DataSourceTextField email = new DataSourceTextField(ResourceTabPanel.EMAIL, "Email");
		addField(email);
		DataSourceImageField imageName = new DataSourceImageField(ResourceTabPanel.IMAGE_NAME, "Codigo Imagen");
		imageName.setImageURLPrefix("http://localhost:8080/Tempore/imageServlet.img?id=");
		addField(imageName);      
	}
	
	 @Override
	 protected Object transformRequest (DSRequest request) {
        final String requestId = request.getRequestId ();
        final DSResponse response = new DSResponse ();
        response.setAttribute ("clientContext", request.getAttributeAsObject ("clientContext"));
        // Asume success
        response.setStatus (0);
        
        if (request.getOperationType() == DSOperationType.FETCH){
        	UserServicesClient.Util.getInstance().fetch(new AsyncCallback<List<UserDTO>>() {
				
				@Override
				public void onSuccess(List<UserDTO> result) {
					List<ListGridRecord> records = new ArrayList<ListGridRecord>();
    				for (UserDTO dto : result) {
    					ListGridRecord newRec = new ListGridRecord();
    					copyValues(dto, newRec);
    					records.add(newRec);
    				}
    				response.setData(records.toArray(new Record[records.size()]));
    				processResponse(requestId, response);
				}
				
				@Override
				public void onFailure(Throwable caught) {
					response.setStatus(RPCResponse.STATUS_FAILURE);
    				processResponse(requestId, response);
				}
			});
        }
        
        return request.getData();
	 }

	public void copyValues(UserDTO dto, ListGridRecord rec) {
		rec.setAttribute(ResourceTabPanel.USER_ID, dto.getId());
		rec.setAttribute(ResourceTabPanel.EMAIL, dto.getEmail());
		rec.setAttribute(ResourceTabPanel.LAST_NAME, dto.getLastName());
		rec.setAttribute(ResourceTabPanel.NAME, dto.getName());
		rec.setAttribute(ResourceTabPanel.USER_NAME, dto.getUserName());
		rec.setAttribute(ResourceTabPanel.IMAGE_NAME, dto.getImageName());
	}	
}