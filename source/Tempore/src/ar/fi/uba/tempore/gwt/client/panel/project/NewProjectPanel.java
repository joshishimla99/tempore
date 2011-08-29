package ar.fi.uba.tempore.gwt.client.panel.project;

import ar.fi.uba.tempore.gwt.client.panel.ContextChildPanel;

import com.google.gwt.user.client.ui.VerticalPanel;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;

public class NewProjectPanel extends VerticalPanel implements ContextChildPanel {

	private DynamicForm form;

	public NewProjectPanel() {
		this.UpdateContent();
	}

	@Override
	public void UpdateContent() {
		form = new DynamicForm();

		// Nombre del proyecto
		TextItem projectNameLabel = new TextItem();
		projectNameLabel.setTitle("Nombre");
		projectNameLabel.setLength(30);
		projectNameLabel.setHint("<nobr>No debe superar los 30 caracteres</nobr>");
		projectNameLabel.setRequired(true);

		// description
		TextAreaItem projectDescription = new TextAreaItem();
		projectDescription.setTitle("Descripci&oacute;n");
		projectDescription.setLength(150);
		projectDescription.setRequired(true);

		DateItem startDate = new DateItem();
		startDate.setTitle("Fecha Inicio");
		startDate.setRequired(true);

		DateItem endDate = new DateItem();
		endDate.setTitle("Fecha Fin");
		endDate.setHint("<nobr>Fecha estimada</nobr>");
		endDate.setRequired(true);

		ComboBoxItem projectClient = new ComboBoxItem();
		projectClient.setTitle("Cliente");
		projectClient.setType("comboBox");
		// TODO: traer el listado de la base de datos!!!!!!!!!!!!!
		projectClient.setValueMap("Gemalto", "Nobleza Picardo", "Tata",	"itMentor", "PetroleraX", "EmpresaX");
		projectClient.setRequired(true);
		
		ButtonItem createProjectButton = new ButtonItem();
		createProjectButton.setTitle("Crear Proyecto");
		createProjectButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				form.validate(false);
			}
		});

		form.setFields(projectNameLabel, projectDescription, startDate, endDate, projectClient, createProjectButton);
		this.add(form);
		form.draw();
	}

}
