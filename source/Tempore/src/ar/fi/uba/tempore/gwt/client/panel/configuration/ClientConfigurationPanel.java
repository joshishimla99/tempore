package ar.fi.uba.tempore.gwt.client.panel.configuration;

import ar.fi.uba.tempore.gwt.client.panel.menus.ContextChildPanel;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.types.RowEndEditAction;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class ClientConfigurationPanel extends Canvas implements ContextChildPanel{
	
	public ClientConfigurationPanel() {
		super();
	}

	@Override
	public void updateContent() {
		final VLayout vLayout = new VLayout();
		vLayout.setMembersMargin(6);
		int width = 600;
		
		final Label title = new Label("Configuraci&oacute;n de Clientes");
		title.setWidth(200);
		title.setHeight(15);
		
		final ListGrid grid = new ListGrid();
		ClientConfigurationDataSource dataSource = new ClientConfigurationDataSource(); 		
		grid.setDataSource(dataSource);
		grid.setWidth(width);
		grid.setHeight(200);		
		grid.setAutoFetchData(true);
		grid.setCanEdit(true);
		grid.setEditEvent(ListGridEditEvent.CLICK);		
		grid.setListEndEditAction(RowEndEditAction.NEXT);
		grid.setAutoSaveEdits(true);
		
		
		// BOTONERA 
		final HLayout btnHLayout = new HLayout();		
		btnHLayout.setWidth(width);
		btnHLayout.setMembersMargin(6);
		btnHLayout.setAlign(Alignment.RIGHT);
		//btnHLayout.setAlign(VerticalAlignment.CENTER);
		
		IButton newButton = new IButton("Nuevo");
		newButton.setIcon("../images/ico/add.ico");
		newButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				grid.startEditingNew();
			}
		});
		btnHLayout.addMember(newButton);		
		
		final IButton removeButton = new IButton("Eliminar");
		removeButton.setIcon("../images/ico/remove.ico");
		removeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				grid.removeSelectedData();
			}
		});
		btnHLayout.addMember(removeButton);
		
		//Agrego los componentes al panel
		vLayout.addMember(title);
		vLayout.addMember(btnHLayout);
		vLayout.addMember(grid);	
		this.addChild(vLayout);
	}
}

