package ar.fi.uba.tempore.gwt.client.panel.project;

import java.util.ArrayList;
import java.util.List;

import ar.fi.uba.tempore.dto.ProjectDTO;
import ar.fi.uba.tempore.gwt.client.ProjectServicesClient;
import ar.fi.uba.tempore.gwt.client.ProjectServicesClientAsync;
import ar.fi.uba.temporeutils.listgrid.GenericGwtRpcDataSource;

import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.fields.DataSourceImageField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class ProjectPanelDataSource extends GenericGwtRpcDataSource<ProjectDTO, ListGridRecord, ProjectServicesClientAsync> {

	private static final String IMAGE_COL = "IMAGE_COL";
	private static final String NAME_COL = "nameCol";
	private static final String ID_COL = "idCol";
	//private static final String DEFAULT_ROOT = "defaultRoot";
	
	@Override
	public List<DataSourceField> getDataSourceFields() {
		List<DataSourceField> list = new ArrayList<DataSourceField>();
		
		DataSourceField fId = new DataSourceTextField(ID_COL);
		fId.setHidden(true);
		fId.setPrimaryKey(true);
		list.add(fId);		
		
		DataSourceImageField fImage = new DataSourceImageField(IMAGE_COL);          
        fImage.setImageURLPrefix("../images/32x32/");
        fImage.setWidth(30);
		list.add(fImage);

		DataSourceField fName = new DataSourceTextField(NAME_COL);
		fName.setRequired(true);
		list.add(fName);
		
		return list;
	}
	
	@Override
	public void copyValues(ListGridRecord from, ProjectDTO to) {
		to.setId(from.getAttributeAsInt(ID_COL));
		to.setName(from.getAttribute(NAME_COL));		
	}
	
	@Override
	public void copyValues(ProjectDTO from, ListGridRecord to) {
		to.setAttribute(ID_COL, from.getId());
		to.setAttribute(NAME_COL, from.getName());
		to.setAttribute(IMAGE_COL, "Portfolio.png");
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
