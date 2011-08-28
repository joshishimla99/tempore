package ar.fi.uba.tempore.gwt.client.panel.configuration;

import java.util.List;

import ar.fi.uba.tempore.dto.AlertDTO;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;

public class AlertListGrid extends ListGrid{
	private static final String COL_ID = "idCol";
	private static final String COL_NAME = "nameCol";
	private static final String COL_DESCRIPTION = "descriptionCol";
	
	/**
	 * Constructor de la tabla
	 */
	public AlertListGrid(){	
		//Armo la tabla
		ListGridField idCol = new ListGridField(COL_ID, "ID");
		idCol.setType(ListGridFieldType.INTEGER); 
		ListGridField nameCol = new ListGridField(COL_NAME, "Nombre");
		nameCol.setRequired(true);
		ListGridField descriptionCol = new ListGridField(COL_DESCRIPTION,	"Descripción");
		descriptionCol.setRequired(true);
		
		this.setFields( idCol, nameCol, descriptionCol);
	}
	
	/**
	 * Sobrecargo el metodo pasandole el DTO directamente
	 * @param alerts
	 */
	public void setData (List<AlertDTO> alerts){
		//cargo los datos		
		RecordList r = new RecordList();
		for (AlertDTO alertDTO : alerts) {
			r.add(new AlertRecord(alertDTO));			
		}		
		this.setData(r);		
	}
	
	/**
	 * 
	 * @author Ngarcia
	 *
	 */
	class AlertRecord extends Record {
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
			alertDTO.setId(this.getAttributeAsInt(COL_ID));
			alertDTO.setName(this.getAttributeAsString(COL_NAME));
			alertDTO.setDescription(this.getAttributeAsString(COL_DESCRIPTION));
			return alertDTO;
		}
	}
}