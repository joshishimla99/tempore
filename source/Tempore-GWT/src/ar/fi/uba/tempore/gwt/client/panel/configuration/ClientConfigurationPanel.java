package ar.fi.uba.tempore.gwt.client.panel.configuration;

import com.google.gwt.user.client.ui.VerticalPanel;
import com.smartgwt.client.types.Autofit;
import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.types.RowEndEditAction;
import com.smartgwt.client.types.SelectionAppearance;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;

public class ClientConfigurationPanel extends VerticalPanel {

	public ClientConfigurationPanel(){
		Label label = new Label("Configuracion de Clientes");
		label.setSize("195px", "39px");
		this.add(label);

		Canvas canvas = new Canvas();

		final ListGrid clientGrid = new ListGrid();
		clientGrid.setWidth(600);
		clientGrid.setHeight(224);
		clientGrid.setCellHeight(22);
		clientGrid.setData(ClientData.getClientRecords());
		clientGrid.setAutoFitData(Autofit.HORIZONTAL);
		clientGrid.setSelectionAppearance(SelectionAppearance.CHECKBOX);
		
		ListGridField nameField = new ListGridField("clientName", "Razon Social");
		//clientField.setType(ListGridFieldType.BOOLEAN); 
		
		ListGridField addressField = new ListGridField("clientAddress", "Direccion");
		nameField.setRequired(true);
		
		ListGridField countryField = new ListGridField("clientCountry",	"Pais");
		countryField.setRequired(true);
		
		ListGridField stateField = new ListGridField("clientState", "Provincia");
		
		// TODO: obtener este listado de empresas, de las que esten almacenadas
		//companyField.setValueMap("Gemalto", "Nobleza Picardo", "Tata", "itMentor", "PetroleraX", "EmpresaX");
		//companyField.setRequired(true);
		
		ListGridField zipField = new ListGridField("zipClient", "C.P.");
		
		ListGridField fiscalNumberField = new ListGridField("clientFiscalNumber", "C.U.I.T.");
		fiscalNumberField.setRequired(true);
		
		ListGridField phoneField = new ListGridField("clientPhone", "Telefono");

		
		clientGrid.setFields( nameField, addressField, countryField, stateField, zipField, fiscalNumberField, phoneField);

		clientGrid.setAutoFetchData(true);
		clientGrid.setCanEdit(true);
//		userGrid.setModalEditing(true);
		clientGrid.setEditEvent(ListGridEditEvent.CLICK);
		clientGrid.setListEndEditAction(RowEndEditAction.NEXT);
		clientGrid.setAutoSaveEdits(false);
		clientGrid.setCanRemoveRecords(true);
		canvas.addChild(clientGrid);
		
		clientGrid.setAutoFitWidth("clientName", true);
		clientGrid.setAutoFitWidth("clientAddress", true);
		clientGrid.setAutoFitWidth("clientCountry", true);
		clientGrid.setAutoFitWidth("clientState", true);
		clientGrid.setAutoFitWidth("clientZip", true);
		clientGrid.setAutoFitWidth("clientFiscalNumber", true);
		clientGrid.setAutoFitWidth("clientPhone", true);
		
		IButton editButton = new IButton("Nuevo");
		editButton.setTop(250);
		editButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				clientGrid.startEditingNew();
			}
		});
		canvas.addChild(editButton);

		IButton saveButton = new IButton("Guardar");
		saveButton.setTop(250);
		saveButton.setLeft(110);
		saveButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				clientGrid.saveAllEdits();
			}
		});
		canvas.addChild(saveButton);

		IButton discardButton = new IButton("Eliminar");
		discardButton.setTop(250);
		discardButton.setLeft(220);
		discardButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				clientGrid.discardAllEdits();
			}
		});
		canvas.addChild(discardButton);

		this.add(canvas);
		canvas.draw();
	}
}
