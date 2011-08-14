package ar.fi.uba.tempore.gwt.client.panel.configuration;

import java.util.List;

import ar.fi.uba.tempore.dto.UserDTO;
import ar.fi.uba.tempore.gwt.client.UserServicesClient;
import ar.fi.uba.tempore.gwt.client.UserServicesClientAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.smartgwt.client.types.Autofit;
import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.RowEndEditAction;
import com.smartgwt.client.types.SelectionAppearance;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;

public class UserConfigurationPanel extends VerticalPanel {

//	List<UserDTO> users;
	private final UserServicesClientAsync userService = GWT.create(UserServicesClient.class);
	
	public UserConfigurationPanel() {
		Label label = new Label("Configuracion de usuarios");
		label.setSize("195px", "39px");
		this.add(label);

		final Canvas canvas = new Canvas();
		final ListGrid userGrid = new ListGrid();
		userService.getUsers(new AsyncCallback<List<UserDTO>>(){

			@Override
			public void onFailure(Throwable caught) {
				/*Label errorLabel = new Label();
				errorLabel.setIcon("/images/64x64/Alert.png");
				errorLabel.setContents("Ha ocurrido un error intentando recuperar el listado de usuarios");
				errorLabel.setStyleName("label-errorMessages");
				errorLabel.setSize("395px", "39px");
				canvas.addChild(errorLabel);
				*/
				Window.alert("Error");
			}

			@Override
			public void onSuccess(List<UserDTO> userList) {
				Window.alert("OK users" + userList);
				
				userGrid.setWidth(600);
				userGrid.setHeight(224);
				userGrid.setCellHeight(22);
				userGrid.setData(UserData.getRecords(userList));
				userGrid.setAutoFitData(Autofit.HORIZONTAL);
				userGrid.setSelectionAppearance(SelectionAppearance.CHECKBOX);
				Window.alert("OK users 2");
				ListGridField clientField = new ListGridField("client", "Cliente");
				clientField.setType(ListGridFieldType.BOOLEAN); 
				ListGridField nameField = new ListGridField("userName", "Nombre");
				nameField.setRequired(true);
				ListGridField userLastNameField = new ListGridField("userLastName",	"Apellido");
				userLastNameField.setRequired(true);
				ListGridField companyField = new ListGridField("company", "Empresa");
				Window.alert("OK users 3");
				// TODO: obtener este listado de empresas, de las que esten almacenadas
				companyField.setValueMap("Gemalto", "Nobleza Picardo", "Tata", "itMentor", "PetroleraX", "EmpresaX");
				companyField.setRequired(true);
				Window.alert("OK users 4");
				ListGridField phoneField = new ListGridField("phone", "Telefono");
				ListGridField emailField = new ListGridField("email", "Email");
				ListGridField userField = new ListGridField("user", "Usuario");
				userField.setRequired(true);
				userGrid.setFields( nameField, userLastNameField, companyField, clientField,
						phoneField, userField, emailField);
				Window.alert("OK users 5");
//				userGrid.setAutoFetchData(true);
				userGrid.setCanEdit(true);
//				userGrid.setModalEditing(true);
				userGrid.setEditEvent(ListGridEditEvent.CLICK);
				userGrid.setListEndEditAction(RowEndEditAction.NEXT);
				userGrid.setAutoSaveEdits(false);
				userGrid.setCanRemoveRecords(true);
				canvas.addChild(userGrid);
				
				userGrid.setAutoFitWidth("email", true);
				userGrid.setAutoFitWidth("user", true);
				userGrid.setAutoFitWidth("phone", true);
				userGrid.setAutoFitWidth("userName", true);
				userGrid.setAutoFitWidth("company", true);
				userGrid.setAutoFitWidth("userLastName", true);
				
			}
		});
		

		
		IButton editButton = new IButton("Nuevo");
		editButton.setTop(250);
		editButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				userGrid.startEditingNew();
			}
		});
		canvas.addChild(editButton);

		IButton saveButton = new IButton("Guardar");
		saveButton.setTop(250);
		saveButton.setLeft(110);
		saveButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				userGrid.saveAllEdits();
			}
		});
		canvas.addChild(saveButton);

		IButton discardButton = new IButton("Eliminar");
		discardButton.setTop(250);
		discardButton.setLeft(220);
		discardButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				userGrid.discardAllEdits();
			}
		});
		canvas.addChild(discardButton);

		this.add(canvas);
		canvas.draw();
	}
}
