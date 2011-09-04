package ar.fi.uba.tempore.gwt.client.panel.configuration;

import ar.fi.uba.tempore.gwt.client.panel.menus.ContextChildPanel;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.types.RowEndEditAction;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;

public class AlertConfigurationPanel extends Canvas implements ContextChildPanel{

	//private Canvas canvas = null;
	private VerticalPanel vPanel;
	

	public AlertConfigurationPanel() {
		super();
	}

	@Override
	public void updateContent() {
		Window.alert("Pagina de Alertas. Actualizacion Num 9");

		vPanel = new VerticalPanel();
		
		Label title = new Label("Configuración de Alertas");
		title.setSize("195px", "39px");
		vPanel.add(title);

		//final AlertDataSource dataSource = AlertDataSource.getInstance();
		//dataSource.fetchData();
		final ListGrid grid = new ListGrid();
		AlertGwtRcpDataSource dataSource = new AlertGwtRcpDataSource(); 		
		grid.setDataSource(dataSource);
		grid.setWidth(600);
		grid.setHeight(200);		
		grid.setAutoFetchData(true);
		grid.setCanEdit(true);
		grid.setEditEvent(ListGridEditEvent.CLICK);		
		grid.setListEndEditAction(RowEndEditAction.NEXT);
		grid.setAutoSaveEdits(true);
		grid.setCanRemoveRecords(true);	
		
		vPanel.add(grid);	
		this.addChild(this.vPanel);
		this.redraw();

		
		// BOTONERA  
		IButton editButton = new IButton("Nuevo");
		editButton.setTop(250);
		editButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				grid.startEditingNew();
			}
		});
		this.addChild(editButton);
		
		// BOTONERA  
//		IButton saveButton = new IButton("Guardar");
//		saveButton.setTop(250);
//		saveButton.setLeft(110);
//		saveButton.addClickHandler(new ClickHandler() {
//			public void onClick(ClickEvent event) {
//				grid.saveAllEdits();
//			}
//		});
//		this.addChild(saveButton);
//
//
//		IButton discardButton = new IButton("Restaurar");
//		discardButton.setTop(250);
//		discardButton.setLeft(220);
//		discardButton.addClickHandler(new ClickHandler() {
//			public void onClick(ClickEvent event) {
//				grid.discardAllEdits();
//			}
//		});
//		this.addChild(discardButton);

		//vPanel.add(canvas);
		//this.draw();
		//this.redraw();		
	}
}
