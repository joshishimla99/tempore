package ar.fi.uba.tempore.gwt.client.panel.configuration;

import java.util.List;

import ar.fi.uba.tempore.dto.ClientDTO;
import ar.fi.uba.tempore.gwt.client.ClientServicesClient;
import ar.fi.uba.tempore.gwt.client.ClientServicesClientAsync;
import ar.fi.uba.tempore.gwt.client.panel.ContextChildPanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
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

public class ClientConfigurationPanel extends VerticalPanel implements ContextChildPanel{

//	List<ClientDTO> Clients;
	private final ClientServicesClientAsync clientService = GWT.create(ClientServicesClient.class);
	private Canvas canvas = null; 
	private ListGrid ClientGrid = null;
	
	public ClientConfigurationPanel() {
		Label title = new Label("Configuracion de clientes");
		title.setSize("195px", "39px");
		this.add(title);
	}

	@Override
	public void UpdateContent() {
		clientService.getClients(new AsyncCallback<List<ClientDTO>>(){

			@Override
			public void onFailure(Throwable caught) {
				Label errorLabel = new Label();
				errorLabel.setIcon("/images/64x64/Alert.png");
				errorLabel.setContents("Ha ocurrido un error intentando recuperar el listado de clientes");
				errorLabel.setStyleName("label-errorMessages");
				errorLabel.setSize("395px", "39px");
				canvas.addChild(errorLabel);
			}

			@Override
			public void onSuccess(List<ClientDTO> ClientList) {
				// Si es la primera vez que se accedio al panel, se crearan los componentes
				if (ClientGrid == null){
					ClientGrid = new ListGrid();
					ClientGrid.setWidth(600);
					ClientGrid.setHeight(224);
					ClientGrid.setCellHeight(22);		
					
					ClientGrid.setData(ClientData.getRecords(ClientList));	
					
					ClientGrid.setAutoFitData(Autofit.HORIZONTAL);
					ClientGrid.setSelectionAppearance(SelectionAppearance.CHECKBOX); 					
					ListGridField nameField = new ListGridField("clientName", "Razon Social");
					nameField.setRequired(true);					
					ListGridField addressField = new ListGridField("clientAddress",	"Direccion");
					addressField.setRequired(true);					
					ListGridField countryField = new ListGridField("clientCountry", "Pais");
					countryField.setRequired(true);					
					ListGridField stateField = new ListGridField("clientState", "Provincia");
					ListGridField zipField = new ListGridField("clientZip", "Provincia");
					ListGridField fiscalNumberField = new ListGridField("clientFiscalNumber", "Provincia");					
					ListGridField phoneField = new ListGridField("clientPhone", "Telefono");					
					ClientGrid.setFields( nameField, addressField, countryField, stateField, zipField, fiscalNumberField, phoneField);
//					ClientGrid.setAutoFetchData(true);
					ClientGrid.setCanEdit(true);
					ClientGrid.setEditEvent(ListGridEditEvent.CLICK);
					ClientGrid.setListEndEditAction(RowEndEditAction.NEXT);
					ClientGrid.setAutoSaveEdits(false);
					ClientGrid.setCanRemoveRecords(true);
					canvas.addChild(ClientGrid);
					
				/*	ClientGrid.setAutoFitWidth("email", true);
					ClientGrid.setAutoFitWidth("Client", true);
					ClientGrid.setAutoFitWidth("phone", true);
					ClientGrid.setAutoFitWidth("ClientName", true);
					ClientGrid.setAutoFitWidth("company", true);
					ClientGrid.setAutoFitWidth("ClientLastName", true);*/
				} else { // si ya se habia accedido, solo se actualizan los componentes
					ClientGrid.refreshFields();
				}
			}
		});
		
		IButton editButton = new IButton("Nuevo");
		editButton.setTop(250);
		editButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				ClientGrid.startEditingNew();
			}
		});
		if (canvas == null){
			canvas = new Canvas();
		}
		canvas.addChild(editButton);

		IButton saveButton = new IButton("Guardar");
		saveButton.setTop(250);
		saveButton.setLeft(110);
		saveButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				ClientGrid.saveAllEdits();
			}
		});
		canvas.addChild(saveButton);

		IButton discardButton = new IButton("Eliminar");
		discardButton.setTop(250);
		discardButton.setLeft(220);
		discardButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				ClientGrid.discardAllEdits();
			}
		});
		canvas.addChild(discardButton);

		this.add(canvas);
		canvas.draw();
		
	}
}

