package ar.fi.uba.tempore.gwt.client.panel.configuration;

import java.util.List;

import ar.fi.uba.tempore.dto.AlertDTO;
import ar.fi.uba.tempore.gwt.client.AlertServicesClient;
import ar.fi.uba.tempore.gwt.client.panel.configuration.AlertListGrid.AlertRecord;
import ar.fi.uba.tempore.gwt.client.panel.menus.ContextChildPanel;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.smartgwt.client.types.Autofit;
import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.types.RowEndEditAction;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.events.CellSavedEvent;
import com.smartgwt.client.widgets.grid.events.CellSavedHandler;

public class AlertConfigurationPanel extends Canvas implements ContextChildPanel{

	private Canvas canvas = null; 
	private AlertListGrid alertGrid = null;
	private VerticalPanel vPanel;
	
	public AlertConfigurationPanel() {
		super();
		vPanel = new VerticalPanel();
		Label title = new Label("Configuraci&oacute;n de Alertas");
		title.setSize("195px", "39px");
		vPanel.add(title);
	}

	@Override
	public void UpdateContent() {
		
		AlertServicesClient.Util.getInstance().getAlerts(new AsyncCallback<List<AlertDTO>>() {
			
			@Override
			public void onSuccess(List<AlertDTO> alertList) {
				// Si es la primera vez que se accedio al panel, se crearan los componentes
				if (alertGrid == null){
					alertGrid = new AlertListGrid();
					alertGrid.setData(alertList);
					alertGrid.setWidth(600);
					alertGrid.setHeight(224);
					alertGrid.setCellHeight(22);					
					alertGrid.setAutoFitData(Autofit.HORIZONTAL);
					//alertGrid.setSelectionAppearance(SelectionAppearance.CHECKBOX);					
					alertGrid.setCanEdit(true);
					alertGrid.setEditEvent(ListGridEditEvent.CLICK);
					alertGrid.setListEndEditAction(RowEndEditAction.NEXT);
					alertGrid.setAutoSaveEdits(true);
					//alertGrid.setCanRemoveRecords(true);
					
					alertGrid.addCellSavedHandler(new CellSavedHandler() {
						@Override
						public void onCellSaved(CellSavedEvent event) {
							AlertRecord record = (AlertRecord) event.getRecord();
							AlertDTO alertDTO = record.getAlertDTO();							
							AlertServicesClient.Util.getInstance().updateSaveAlert(alertDTO, new AsyncCallback<AlertDTO>() {
								
								@Override
								public void onSuccess(AlertDTO result) {
									// TODO Auto-generated method stub
									Window.alert("Actualizacion exitosa!!!");
								}
								
								@Override
								public void onFailure(Throwable caught) {
									// TODO Auto-generated method stub
									Window.alert("Error al guardar");
								}
							});
						}
					});
					
					canvas.addChild(alertGrid);
										
				} else { // si ya se habia accedido, solo se actualizan los componentes
					alertGrid.setData(alertList);
					alertGrid.refreshFields();
				}				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Label errorLabel = new Label();
				errorLabel.setIcon("/images/64x64/Alert.png");
				errorLabel.setContents("Ha ocurrido un error intentando recuperar el listado de Alertas");
				errorLabel.setStyleName("label-errorMessages");
				errorLabel.setSize("395px", "39px");
				canvas.addChild(errorLabel);
			}
		});
		
		IButton editButton = new IButton("Nuevo");
		editButton.setTop(250);
		editButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				alertGrid.startEditingNew();
			}
		});
		
		if (canvas == null){
			canvas = new Canvas();
		}
		canvas.addChild(editButton);
		

		IButton discardButton = new IButton("Eliminar");
		discardButton.setTop(250);
		discardButton.setLeft(220);
		discardButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				alertGrid.discardAllEdits();
			}
		});
		canvas.addChild(discardButton);

		vPanel.add(canvas);
		this.addChild(this.vPanel);
		canvas.draw();
		
	}
}
