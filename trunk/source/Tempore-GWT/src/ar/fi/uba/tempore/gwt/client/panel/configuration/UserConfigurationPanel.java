package ar.fi.uba.tempore.gwt.client.panel.configuration;

import com.google.gwt.user.client.ui.VerticalPanel;
import com.smartgwt.client.types.Autofit;
import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.types.RowEndEditAction;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;

public class UserConfigurationPanel extends VerticalPanel {

	public UserConfigurationPanel() {
		this.add(new Label("Configuracion de usuarios"));

		Canvas canvas = new Canvas();

		final ListGrid userGrid = new ListGrid();
		userGrid.setWidth(800);
		userGrid.setHeight(224);
		userGrid.setCellHeight(22);
		userGrid.setData(UserData.getRecords());
		//TODO: hacer que las columnas tengan el tamanio en funcion del contenido
//		userGrid.setAutoFitWidthApproach(AutoFitWidthApproach.VALUE);
//		userGrid.setAutoFitField(true);
		userGrid.setAutoFitData(Autofit.HORIZONTAL);
//		userGrid.setAutoFitMaxColumns(7);
		
		ListGridField clientField = new ListGridField("client", "Cliente");
		ListGridField nameField = new ListGridField("userName", "Nombre");
		nameField.setRequired(true);
		ListGridField userLastNameField = new ListGridField("userLastName",	"Apellido");
		userLastNameField.setRequired(true);
		ListGridField companyField = new ListGridField("company", "Empresa");
		// TODO: obtener este listado de empresas, de las que esten almacenadas
		companyField.setValueMap("Gemalto", "Nobleza Picardo", "Tata", "itMentor", "PetroleraX", "EmpresaX");
		companyField.setRequired(true);
		
		ListGridField phoneField = new ListGridField("phone", "Telefono");
		ListGridField emailField = new ListGridField("email", "Email");
		ListGridField userField = new ListGridField("user", "Usuario");
		userField.setRequired(true);
		userGrid.setFields(clientField, nameField, userLastNameField, companyField,
				phoneField, userField, emailField);

		userGrid.setAutoFetchData(true);
		userGrid.setCanEdit(true);
		userGrid.setModalEditing(true);
		userGrid.setEditEvent(ListGridEditEvent.CLICK);
		userGrid.setListEndEditAction(RowEndEditAction.NEXT);
		userGrid.setAutoSaveEdits(false);
		canvas.addChild(userGrid);

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
