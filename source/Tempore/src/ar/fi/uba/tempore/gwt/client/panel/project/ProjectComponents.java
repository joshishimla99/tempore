package ar.fi.uba.tempore.gwt.client.panel.project;

import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;


/**
 * Agrupa todos los elementos de los paneles de creacion y edicion de proyecto.
 * Contiene el form, los texts, los combos, etc.
 * @author Ludmila
 *
 */
public class ProjectComponents {

	private DynamicForm form;
	TextItem projectNameLabel;
	TextAreaItem projectDescription;
	DateItem startDate;
	DateItem endDate;
	ComboBoxItem projectClient;
	ButtonItem actionButton;
	
	public ProjectComponents(){
		form = new DynamicForm();
	}
	
	public void addProjectComponents(String buttonName, ClickHandler clickHandler){
		// Nombre del proyecto
		projectNameLabel = new TextItem();
		projectNameLabel.setTitle("Nombre");
		projectNameLabel.setLength(30);
		projectNameLabel.setHint("<nobr>No debe superar los 30 caracteres</nobr>");
		projectNameLabel.setRequired(true);

		// description
		projectDescription = new TextAreaItem();
		projectDescription.setTitle("Descripci&oacute;n");
		projectDescription.setLength(150);
		projectDescription.setRequired(true);

		startDate = new DateItem();
		startDate.setTitle("Fecha Inicio");
		startDate.setRequired(true);
		startDate.setInvalidDateStringMessage("La fecha ingresada no es v&aacute;lida");

		endDate = new DateItem();
		endDate.setTitle("Fecha Fin");
		endDate.setHint("<nobr>Fecha estimada</nobr>");
		endDate.setRequired(true);
		endDate.setInvalidDateStringMessage("La fecha ingresada no es v&aacute;lida");
		
		projectClient = new ComboBoxItem();
		projectClient.setTitle("Cliente");
		projectClient.setType("comboBox");
		// TODO: traer el listado de la base de datos!!!!!!!!!!!!!
		projectClient.setValueMap("Gemalto", "Nobleza Picardo", "Tata",	"itMentor", "PetroleraX", "EmpresaX");
		projectClient.setRequired(true);
		
		actionButton = new ButtonItem();
		actionButton.setTitle(buttonName);
		actionButton.addClickHandler(clickHandler);
		
		this.form.setFields(projectNameLabel, projectDescription, startDate, endDate, projectClient, actionButton);
		
	}
	
	public DynamicForm getForm(){
		return this.form;
	}
	
	public void clearProjectComponents(){
		form.clearValues();
	}
	
}
