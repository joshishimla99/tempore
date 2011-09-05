package ar.fi.uba.tempore.gwt.client.panel.configuration;

import java.util.List;

import ar.fi.uba.tempore.dto.UserDTO;
import ar.fi.uba.tempore.gwt.client.UserServicesClientAsync;
import ar.fi.uba.temporeutils.listgrid.GenericGwtRpcDataSource;

import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class UserConfigurationDataSource extends GenericGwtRpcDataSource<UserDTO, ListGridRecord, UserServicesClientAsync> {

	@Override
	public List<DataSourceField> getDataSourceFields() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void copyValues(ListGridRecord from, UserDTO to) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void copyValues(UserDTO from, ListGridRecord to) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public UserServicesClientAsync getServiceAsync() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListGridRecord getNewRecordInstance() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserDTO getNewDataObjectInstance() {
		// TODO Auto-generated method stub
		return null;
	}

	
}