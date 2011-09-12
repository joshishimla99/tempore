package ar.fi.uba.tempore.gwt.client.panel.project;

import ar.fi.uba.tempore.dto.ProjectDTO;

import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;

/**
 * Panel con el arbol de proyectos, es singleton
 * @author Ludmila
 *
 */
public class ProjectPanel extends ListGrid {

	static private ProjectPanel instance = null;
	//private ListGrid this;
	
	private ProjectPanel() {
		super();
		
		this.setDataSource(new ProjectPanelDataSource());
		this.setAutoFetchData(true);
		this.setEditByCell(false);
//		myList1.setCanDragRecordsOut(true);  
//		myList1.setCanAcceptDroppedRecords(true);  
//		myList1.setCanReorderFields(true);  
//		myList1.setDragDataAction(DragDataAction.MOVE);  
		
		this.setWidth(200);
		this.setHeight(700);
		this.setCellHeight(24);
		this.setImageSize(16);
		this.setShowEdges(true);
		
		//this.setBorder("0px");
		//this.setBodyStyleName("normal");
		this.setShowHeader(false);
		this.setLeaveScrollbarGap(false);
		
		this.addRecordClickHandler(new RecordClickHandler() {
			@Override
			public void onRecordClick(RecordClickEvent event) {
				ProjectDTO dto = null;
				ListGridRecord selectedRecord = (ListGridRecord) event.getRecord();
				if (selectedRecord == null) {
					dto = new ProjectDTO();
					((ProjectPanelDataSource)getDataSource()).copyValues(selectedRecord, dto);
				}
				
				//TODO Avisar al resto que se produjo una actualizacion				
			}
		});
		
		this.redraw();	
	}
		
	static public ProjectPanel getInstance() {
		if (instance == null) {
			instance = new ProjectPanel();
		}
		return instance;
	}
	
	@Override  
    protected String getCellCSSText(ListGridRecord record, int rowNum, int colNum) {  
        return "font-weight:bold;";  
    }  

	/**
	 * Obtiene el Proyecto DTO seleccionado
	 * @return Si hay seleccionado devuelve el DTO. De lo contrario devuelve null.
	 */
	public ProjectDTO getSelected(){
		ProjectDTO dto = null;
		ListGridRecord selectedRecord = this.getSelectedRecord();
		if (selectedRecord == null) {
			dto = new ProjectDTO();
			((ProjectPanelDataSource)getDataSource()).copyValues(selectedRecord, dto);
		}
		
		return dto ;
	}
}
