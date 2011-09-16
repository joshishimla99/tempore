package ar.fi.uba.tempore.gwt.client.panel.configuration;


import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.types.RowEndEditAction;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class AlertConfigurationPanel extends Canvas{

	public AlertConfigurationPanel() {
		super();
		updateContent();
	}

	public void updateContent() {
		final VLayout vLayout = new VLayout();
		vLayout.setMembersMargin(6);
		int width = 600;
	
		//TITULO
		final Label title = new Label("Configuraci&oacute;n de Alertas");
		title.setWidth(200);
		title.setHeight(15);
		
		//GRILLA
		final ListGrid grid = new ListGrid();
		AlertConfigurationDataSource dataSource = new AlertConfigurationDataSource(); 		
		grid.setDataSource(dataSource);
		grid.setWidth(width);
		grid.setHeight(200);		
		grid.setAutoFetchData(true);
		grid.setCanEdit(true);
		grid.setEditEvent(ListGridEditEvent.CLICK);		
		grid.setListEndEditAction(RowEndEditAction.NEXT);
		grid.setAutoSaveEdits(true);		
		
		//BOTONERA 
		final HLayout btnLayout = new HLayout();
		btnLayout.setWidth(width);
		btnLayout.setMembersMargin(6);
		btnLayout.setAlign(Alignment.RIGHT);
		
		final IButton newButton = new IButton("Nuevo");
		newButton.setIcon("../images/ico/add.ico");
		newButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				grid.startEditingNew();
			}
		});
		btnLayout.addMember(newButton);
		
		final IButton removeButton = new IButton("Eliminar");
		removeButton.setIcon("../images/ico/remove.ico");
		removeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				grid.removeSelectedData();
			}
		});
		btnLayout.addMember(removeButton);
		
		//Agrego los Componentes al Panel
		vLayout.addMember(title);
		vLayout.addMember(btnLayout);
		vLayout.addMember(grid);	

		this.addChild(vLayout);
		this.redraw();
	}
}
