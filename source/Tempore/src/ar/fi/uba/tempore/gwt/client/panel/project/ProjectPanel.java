package ar.fi.uba.tempore.gwt.client.panel.project;

import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;

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
		this.setHeight(400);
		this.setCellHeight(24);
		this.setImageSize(16);
		this.setShowEdges(true);
		
		//this.setBorder("0px");
		//this.setBodyStyleName("normal");
		this.setShowHeader(false);
		this.setLeaveScrollbarGap(false);		
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
}
