package ar.fi.uba.tempore.gwt.client.panel.task;

import java.util.ArrayList;
import java.util.List;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.fields.DataSourceDateField;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;

public class TaskTreeDataSource extends DataSource {
	public static final String NAME_FIELD = "name";
	public static final String ID_FIELD = "id";
	public static final String DESCRIPTION_FIELD = "description";
	public static final String TYPE_FIELD = "type";
	public static final String ESTIMATED_HOURS_FIELD = "estimatedHs";
	public static final String REAL_HOURS_FIELD = "realHs";

	public TaskTreeDataSource(){
		super();
	}
	
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
		DataSourceField fDesc = new DataSourceTextField(DESCRIPTION_FIELD);
		fDesc.setHidden(true);
		list.add(fDesc);
		
		DataSourceField fh2 = new DataSourceTextField(DESCRIPTION_FIELD);
		fh2.setHidden(true);
		list.add(fh2);
		
		DataSourceField fType = new DataSourceDateField(TYPE_FIELD);
		fType.setHidden(true);
		list.add(fType);
		
		DataSourceField fEstimated = new DataSourceDateField(ESTIMATED_HOURS_FIELD);
		fEstimated.setHidden(true);
		list.add(fEstimated);
		
		DataSourceField fReal = new DataSourceIntegerField(REAL_HOURS_FIELD);
		fReal.setHidden(true);
		list.add(fReal);
		
		return list;
	}

}
