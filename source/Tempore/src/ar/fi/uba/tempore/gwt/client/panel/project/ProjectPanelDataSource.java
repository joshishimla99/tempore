package ar.fi.uba.tempore.gwt.client.panel.project;

import java.util.ArrayList;
import java.util.List;

import ar.fi.uba.tempore.dto.ProjectDTO;
import ar.fi.uba.tempore.gwt.client.ProjectServicesClient;
import ar.fi.uba.tempore.gwt.client.ProjectServicesClientAsync;
import ar.fi.uba.temporeutils.listgrid.GenericGwtRpcDataSource;

import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.fields.DataSourceDateField;
import com.smartgwt.client.data.fields.DataSourceImageField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class ProjectPanelDataSource extends GenericGwtRpcDataSource<ProjectDTO, ListGridRecord, ProjectServicesClientAsync> {

	public static final String IMAGE_COL = "IMAGE_COL";
	public static final String NAME_FIELD = "nameCol";
	public static final String ID_FIELD = "idCol";
	private static final String BUDGET_FIELD = "budgetCol";
	private static final String DESCRIPTION_FIELD = "descCol";
	private static final String ENDDATE_FIELD = "endCol";
	private static final String INITDATE_FIELD = "iniDate";
	
	@Override
	public List<DataSourceField> getDataSourceFields() {
		List<DataSourceField> list = new ArrayList<DataSourceField>();
		
		DataSourceField fId = new DataSourceTextField(ID_FIELD);
		fId.setHidden(true);
		fId.setPrimaryKey(true);
		list.add(fId);		
		
		DataSourceImageField fImage = new DataSourceImageField(IMAGE_COL);          
        fImage.setImageURLPrefix("../images/32x32/");
        fImage.setWidth(30);
		list.add(fImage);

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
	}
	
	@Override
	public void copyValues(ProjectDTO from, ListGridRecord to) {
		to.setAttribute(ID_FIELD, from.getId());
		to.setAttribute(NAME_FIELD, from.getName());
		to.setAttribute(IMAGE_COL, "Portfolio.png");
		to.setAttribute(BUDGET_FIELD, from.getBudget());
		to.setAttribute(DESCRIPTION_FIELD, from.getDescription());
		to.setAttribute(ENDDATE_FIELD, from.getEndDate());
		to.setAttribute(INITDATE_FIELD, from.getInitDate());
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
