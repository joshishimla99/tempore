package ar.fi.uba.tempore.gwt.client.panel.task;

import ar.fi.uba.tempore.gwt.client.panel.menus.ContextChildPanel;
import ar.fi.uba.tempore.gwt.client.panel.project.ProjectPanel;

import com.google.gwt.user.client.ui.VerticalPanel;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class NewTaskPanel extends Canvas implements ContextChildPanel{
	
	private ProjectPanel projectPanel;
	private DynamicForm form;
	private VerticalPanel vPanel;
	
	public NewTaskPanel(ProjectPanel projectPanel){
		super();
		this.vPanel = new VerticalPanel();
		this.projectPanel = projectPanel;
	}

	@Override
	public void updateContent() {
		
		if (form == null){
			form = new DynamicForm();
		} else {
			form.clearValues();
		}
		
		// Nombre de la tarea
		TextItem taskNameLabel = new TextItem();
		taskNameLabel.setTitle("Nombre");
		taskNameLabel.setLength(30);
		taskNameLabel.setHint("<nobr>No debe superar los 30 caracteres</nobr>");
		taskNameLabel.setRequired(true);

		// description
		TextAreaItem taskDescription = new TextAreaItem();
		taskDescription.setTitle("Descripci&oacute;n");
		taskDescription.setLength(150);
		taskDescription.setRequired(true);
		
		// Nombre de la tarea
		TextItem estimatedTimeLabel = new TextItem();
		estimatedTimeLabel.setTitle("Tiempo");
		estimatedTimeLabel.setKeyPressFilter("[0-9.]");
		estimatedTimeLabel.setHint("<nobr>Tiempo estimado para la tarea</nobr>");
		estimatedTimeLabel.setRequired(true);;
		
		ComboBoxItem taskResponsable = new ComboBoxItem();
		taskResponsable.setTitle("Responsable");
		taskResponsable.setType("comboBox");
		// TODO: traer el listado de la base de datos!!!!!!!!!!!!!
		taskResponsable.setValueMap("Ludmila", "Juan Pablo", "Nicolas",	"Pablo");
		taskResponsable.setRequired(true);

		ButtonItem createTaskButton = new ButtonItem();
		createTaskButton.setTitle("Crear Tarea");
		createTaskButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				ListGridRecord projectSelected = projectPanel.getProjectSelected();
				if (projectSelected != null) {
					//TODO: agregar la tarea al proyecto
				}
				form.validate(false);
			}
		});

		form.setFields(taskNameLabel, taskDescription, estimatedTimeLabel, taskResponsable, createTaskButton);
		this.vPanel.add(form);
		this.addChild(this.vPanel);
		form.draw();
	}

}
