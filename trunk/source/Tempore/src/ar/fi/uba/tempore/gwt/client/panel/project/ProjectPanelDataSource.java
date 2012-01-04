package ar.fi.uba.tempore.gwt.client.panel.project;

import java.util.ArrayList;
import java.util.List;

import ar.fi.uba.tempore.dto.ClientDTO;
import ar.fi.uba.tempore.dto.ProjectDTO;
import ar.fi.uba.tempore.dto.ProjectStateDTO;
import ar.fi.uba.tempore.gwt.client.ProjectServicesClient;
import ar.fi.uba.tempore.gwt.client.ProjectServicesClientAsync;
import ar.fi.uba.tempore.gwt.client.login.SessionUser;
import ar.fi.uba.temporeutils.listgrid.filter.GenericGwtRpcDataSourceFilterId;

import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.fields.DataSourceDateField;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class ProjectPanelDataSource extends GenericGwtRpcDataSourceFilterId<Integer, ProjectDTO, ListGridRecord, ProjectServicesClientAsync> {

	public static final String NAME_FIELD = "nameCol";
	public static final String ID_FIELD = "idCol";
	public static final String BUDGET_FIELD = "budgetCol";
	public static final String DESCRIPTION_FIELD = "descCol";
	public static final String ENDDATE_FIELD = "endCol";
	public static final String INITDATE_FIELD = "iniDate";
	
	public static final String CLIENT_ID_FIELD = "clientIdCol";
	public static final String CLIENT_NAME_FIELD = "clientNameCol";
	
	public static final String STATE_ID_FIELD = "stateIdCol";
	public static final String STATE_NAME_FIELD = "stateNameCol";
	
	public static final String IS_OWNER_FIELD = "isOwnerCol";
	
	public ProjectPanelDataSource(){
		super();
		//Agrego id del usaurio que quiero recuperar los proyectos
		setId(SessionUser.getInstance().getUser().getId());
	}
	
	@Override
	public List<DataSourceField> getDataSourceFields() {
		List<DataSourceField> list = new ArrayList<DataSourceField>();
		
		DataSourceField fId = new DataSourceTextField(ID_FIELD);
		fId.setPrimaryKey(true);
		list.add(fId);		
		
		DataSourceField fName = new DataSourceTextField(NAME_FIELD);
		list.add(fName);
		
		//Ocultos
		DataSourceField fh1 = new DataSourceTextField(BUDGET_FIELD);
		list.add(fh1);
		
		DataSourceField fh2 = new DataSourceTextField(DESCRIPTION_FIELD);
		list.add(fh2);
		
		DataSourceField fh3 = new DataSourceDateField(ENDDATE_FIELD);
		list.add(fh3);
		
		DataSourceField fh4 = new DataSourceDateField(INITDATE_FIELD);
		list.add(fh4);
		
		DataSourceField fh5 = new DataSourceIntegerField(STATE_ID_FIELD);
		list.add(fh5);
		
		DataSourceField fh6 = new DataSourceTextField(STATE_NAME_FIELD);
		list.add(fh6);
		
		DataSourceField fh7 = new DataSourceTextField(IS_OWNER_FIELD);
		list.add(fh7);
		
		DataSourceField fh8 = new DataSourceTextField(CLIENT_ID_FIELD);
		list.add(fh8);
		
		DataSourceField fh9 = new DataSourceTextField(CLIENT_NAME_FIELD);
		list.add(fh9);
		
		return list;
	}
	
	@Override
	public void copyValues(ListGridRecord from, ProjectDTO to) {
		//No deberia utilizarce
		to.setId(from.getAttributeAsInt(ID_FIELD));
		to.setName(from.getAttribute(NAME_FIELD));
		to.setBudget(from.getAttributeAsInt(BUDGET_FIELD));
		to.setDescription(from.getAttribute(DESCRIPTION_FIELD));
		to.setEndDate(from.getAttributeAsDate(ENDDATE_FIELD));
		to.setInitDate(from.getAttributeAsDate(INITDATE_FIELD));
		to.setIsOwner(from.getAttributeAsInt(IS_OWNER_FIELD));
		
		ProjectStateDTO projectState = new ProjectStateDTO();
		projectState.setId(from.getAttributeAsInt(STATE_ID_FIELD));
		projectState.setName(from.getAttribute(STATE_NAME_FIELD));
		to.setProjectState(projectState );
		
		ClientDTO client = new ClientDTO();
		client.setId(from.getAttributeAsInt(CLIENT_ID_FIELD));
		client.setName(from.getAttribute(CLIENT_NAME_FIELD));
		to.setClient(client);
		
	}
	
	@Override
	public void copyValues(ProjectDTO from, ListGridRecord to) {
		to.setAttribute(ID_FIELD, from.getId());
		to.setAttribute(NAME_FIELD, from.getName());
		to.setAttribute(BUDGET_FIELD, from.getBudget());
		to.setAttribute(DESCRIPTION_FIELD, from.getDescription());
		to.setAttribute(ENDDATE_FIELD, from.getEndDate());
		to.setAttribute(INITDATE_FIELD, from.getInitDate());
		
		to.setAttribute(STATE_ID_FIELD, from.getProjectState().getId());
		to.setAttribute(STATE_NAME_FIELD, from.getProjectState().getName());
		
		to.setAttribute(CLIENT_ID_FIELD, from.getClient().getId());
		to.setAttribute(CLIENT_NAME_FIELD, from.getClient().getName());
		
		to.setAttribute(IS_OWNER_FIELD, from.getIsOwner());
	}
	
	@Override
	public ProjectServicesClientAsync getServiceAsync() {
		return ProjectServicesClient.Util.getInstance();
	}
	
	@Override
	public ListGridRecord getNewRecordInstance() {
		return new ListGridRecord();
	}
	
	@Override
	public ProjectDTO getNewDataObjectInstance() {
		return new ProjectDTO();
	}
}
