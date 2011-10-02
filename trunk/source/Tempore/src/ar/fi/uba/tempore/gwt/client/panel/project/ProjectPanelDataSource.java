package ar.fi.uba.tempore.gwt.client.panel.project;

import java.util.ArrayList;
import java.util.List;

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
	public static final String STATE_ID_FIELD = "stateIdCol";
	public static final String STATE_NAME_FIELD = "stateNameCol";
	
	public ProjectPanelDataSource(){
		super();
		//Agrego id del usaurio que quiero recuperar los proyectos
		setId(SessionUser.getInstance().getUser().getId());
	}
	
	@Override
	public List<DataSourceField> getDataSourceFields() {
		List<DataSourceField> list = new ArrayList<DataSourceField>();
		
		DataSourceField fId = new DataSourceTextField(ID_FIELD);
		//fId.setHidden(true);
		fId.setPrimaryKey(true);
		list.add(fId);		
		
		DataSourceField fName = new DataSourceTextField(NAME_FIELD);
		fName.setRequired(true);
		list.add(fName);
		
		//Ocultos
		DataSourceField fh1 = new DataSourceTextField(BUDGET_FIELD);
		fh1.setHidden(true);
		list.add(fh1);
		
		DataSourceField fh2 = new DataSourceTextField(DESCRIPTION_FIELD);
		fh2.setHidden(true);
		list.add(fh2);
		
		DataSourceField fh3 = new DataSourceDateField(ENDDATE_FIELD);
		fh3.setHidden(true);
		list.add(fh3);
		
		DataSourceField fh4 = new DataSourceDateField(INITDATE_FIELD);
		fh4.setHidden(true);
		list.add(fh4);
		
		DataSourceField fh5 = new DataSourceIntegerField(STATE_ID_FIELD);
		fh5.setHidden(true);
		list.add(fh5);
		
		DataSourceField fh6 = new DataSourceTextField(STATE_NAME_FIELD);
		fh6.setHidden(true);
		list.add(fh6);
		
		return list;
	}
	
	@Override
	public void copyValues(ListGridRecord from, ProjectDTO to) {
		to.setId(from.getAttributeAsInt(ID_FIELD));
		to.setName(from.getAttribute(NAME_FIELD));
		to.setBudget(from.getAttributeAsFloat(BUDGET_FIELD));
		to.setDescription(from.getAttribute(DESCRIPTION_FIELD));
		to.setEndDate(from.getAttributeAsDate(ENDDATE_FIELD));
		to.setInitDate(from.getAttributeAsDate(INITDATE_FIELD));

		ProjectStateDTO projectState = new ProjectStateDTO();
		projectState.setId(from.getAttributeAsInt(STATE_ID_FIELD));
		projectState.setName(from.getAttribute(STATE_NAME_FIELD));
		to.setProjectState(projectState );
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
