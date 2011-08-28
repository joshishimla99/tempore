package ar.fi.uba.tempore.gwt.client.panel.configuration;

import java.util.List;

import ar.fi.uba.tempore.dto.AlertDTO;
import ar.fi.uba.tempore.gwt.client.AlertServicesClient;
import ar.fi.uba.tempore.gwt.client.panel.ContextChildPanel;

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

public class AlertConfigurationPanel extends VerticalPanel implements ContextChildPanel{

	private Canvas canvas = null; 
	private AlertListGrid alertGrid = null;
	
	public AlertConfigurationPanel() {
		Label title = new Label("Configuracion de usuarios");
		title.setSize("195px", "39px");
		this.add(title);
	}

	@Override
	public void UpdateContent() {
		
		AlertServicesClient.Util.getInstance().getAlerts(new AsyncCallback<List<AlertDTO>>() {
			
			@Override
			public void onSuccess(List<AlertDTO> alertList) {
				// Si es la primera vez que se accedio al panel, se crearan los componentes
				if (alertGrid == null){
					alertGrid = new AlertListGrid(alertList);
					alertGrid.setWidth(600);
					alertGrid.setHeight(224);
					alertGrid.setCellHeight(22);					
					alertGrid.setAutoFitData(Autofit.HORIZONTAL);
					alertGrid.setSelectionAppearance(SelectionAppearance.CHECKBOX);

					
					alertGrid.setCanEdit(true);
					alertGrid.setEditEvent(ListGridEditEvent.CLICK);
					alertGrid.setListEndEditAction(RowEndEditAction.NEXT);
					alertGrid.setAutoSaveEdits(false);
					alertGrid.setCanRemoveRecords(true);
					canvas.addChild(alertGrid);
										
				} else { // si ya se habia accedido, solo se actualizan los componentes
					alertGrid.refreshFields();
				}				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Label errorLabel = new Label();
				errorLabel.setIcon("/images/64x64/Alert.png");
				errorLabel.setContents("Ha ocurrido un error intentando recuperar el listado de usuarios");
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

		IButton saveButton = new IButton("Guardar");
		saveButton.setTop(250);
		saveButton.setLeft(110);
		saveButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				alertGrid.saveAllEdits();
			}
		});
		canvas.addChild(saveButton);

		IButton discardButton = new IButton("Eliminar");
		discardButton.setTop(250);
		discardButton.setLeft(220);
		discardButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				alertGrid.discardAllEdits();
			}
		});
		canvas.addChild(discardButton);

		this.add(canvas);
		canvas.draw();
		
	}
}
