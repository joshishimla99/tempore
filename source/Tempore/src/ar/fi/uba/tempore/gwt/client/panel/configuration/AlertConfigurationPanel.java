package ar.fi.uba.tempore.gwt.client.panel.configuration;

import ar.fi.uba.tempore.gwt.client.panel.menus.ContextChildPanel;

import com.google.gwt.user.client.Window;
import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.types.RowEndEditAction;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.layout.VLayout;

public class AlertConfigurationPanel extends Canvas implements ContextChildPanel{

	private VLayout vPanel;

	public AlertConfigurationPanel() {
		super();
	}

	@Override
	public void updateContent() {
		vPanel = new VLayout();
		
		Label title = new Label("Configuración de Alertas");
		title.setSize("195px", "39px");
		vPanel.addChild(title);

		final ListGrid grid = new ListGrid();
		AlertConfigurationDataSource dataSource = new AlertConfigurationDataSource(); 		
		grid.setDataSource(dataSource);
		grid.setWidth(600);
		grid.setHeight(200);		
		grid.setAutoFetchData(true);
		grid.setCanEdit(true);
		grid.setEditEvent(ListGridEditEvent.CLICK);		
		grid.setListEndEditAction(RowEndEditAction.NEXT);
		grid.setAutoSaveEdits(true);
		grid.setCanRemoveRecords(true);	
		
		vPanel.addChild(grid);	
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
	}
}
