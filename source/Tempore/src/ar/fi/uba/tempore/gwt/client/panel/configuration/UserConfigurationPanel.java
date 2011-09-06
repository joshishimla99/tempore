package ar.fi.uba.tempore.gwt.client.panel.configuration;

import ar.fi.uba.tempore.gwt.client.panel.menus.ContextChildPanel;

import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.types.RowEndEditAction;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.layout.VLayout;

public class UserConfigurationPanel extends Canvas implements ContextChildPanel{
	private VLayout vPanel;
	
	public UserConfigurationPanel() {
		super();
	}

	@Override
	public void updateContent() {
		vPanel = new VLayout();
		
		Label title = new Label("Configuraci&oacute;n de Alertas");
		title.setSize("195px", "39px");
		vPanel.addChild(title);

		final ListGrid grid = new ListGrid();
		UserConfigurationDataSource dataSource = new UserConfigurationDataSource(); 		
		grid.setDataSource(dataSource);
		grid.setWidth(700);
		grid.setHeight(300);		
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
		editButton.setTop(350);
		editButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				grid.startEditingNew();
			}
		});
		this.addChild(editButton);	
	}
}
