package ar.fi.uba.tempore.gwt.client.panel.configuration;

import java.util.ArrayList;
import java.util.List;

import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

import ar.fi.uba.tempore.dto.AlertDTO;

public class AlertListGrid extends ListGrid{
	private static final String COL_ID = "idCol";
	private static final String COL_NAME = "nameCol";
	private static final String COL_DESCRIPTION = "descriptionCol";
	
	public AlertListGrid(){	
	}
	
	public AlertListGrid(List<AlertDTO> alerts){
		List<AlertRecord> r = new ArrayList<AlertRecord>();
		for (AlertDTO alertDTO : alerts) {
			r.add(new AlertRecord(alertDTO));			
		}		
		this.setData(r.toArray(new AlertRecord[r.size()]));
		
		ListGridField idCol = new ListGridField(COL_ID, "ID");
		idCol.setType(ListGridFieldType.INTEGER); 
		ListGridField nameCol = new ListGridField(COL_NAME, "Nombre");
		nameCol.setRequired(true);
		ListGridField descriptionCol = new ListGridField(COL_DESCRIPTION,	"Descripción");
		descriptionCol.setRequired(true);
		
		this.setFields( idCol, nameCol, descriptionCol);
	}	
	
	/**
	 * 
	 * @author Ngarcia
	 *
	 */
	class AlertRecord extends ListGridRecord {
		private AlertDTO alertDTO;
		public AlertRecord() {
			alertDTO = new AlertDTO();
		}

		public AlertRecord(AlertDTO a) {
			this.alertDTO = a;
			setIdCol(this.alertDTO.getId());
			setNameCol(this.alertDTO.getName());
			setDescriptionCol(this.alertDTO.getDescription());			
		}		
		public void setIdCol(Integer id){
			this.setAttribute(COL_ID, id);			
		}
		public void setNameCol(String name){
			this.setAttribute(COL_NAME, name);
		}
		public void setDescriptionCol (String description){
			this.setAttribute(COL_DESCRIPTION, description);		
		}
		
		public AlertDTO getAlertDTO(){
			return alertDTO;
		}
	}
}