package ar.fi.uba.tempore.gwt.client.panel.task;

import ar.fi.uba.tempore.gwt.client.panel.ContextChildPanel;

import com.google.gwt.user.client.ui.VerticalPanel;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;

public class NewTaskPanel extends VerticalPanel implements ContextChildPanel{
	
	private DynamicForm form;
	//TODO: asociar la tarea al proyecto!!!!!!!!!!!!!!
	
	public NewTaskPanel(){
		// todo nothing
	}

	@Override
	public void UpdateContent() {
		
		if (form == null){
			form = new DynamicForm();
		} else {
			form.clear();
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
				form.validate(false);
			}
		});

		form.setFields(taskNameLabel, taskDescription, estimatedTimeLabel, taskResponsable, createTaskButton);
		this.add(form);
		form.draw();
	}

}
