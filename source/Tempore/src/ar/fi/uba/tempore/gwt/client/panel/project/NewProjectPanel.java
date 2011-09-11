package ar.fi.uba.tempore.gwt.client.panel.project;

import ar.fi.uba.tempore.dto.ProjectDTO;
import ar.fi.uba.tempore.gwt.client.ClientServicesClient;
import ar.fi.uba.tempore.gwt.client.ClientServicesClientAsync;
import ar.fi.uba.tempore.gwt.client.ProjectServicesClient;
import ar.fi.uba.tempore.gwt.client.ProjectServicesClientAsync;
import ar.fi.uba.tempore.gwt.client.panel.menus.ContextChildPanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.smartgwt.client.types.MultipleAppearance;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class NewProjectPanel extends Canvas implements ContextChildPanel {

	private VLayout vPanel = null;
	private DynamicForm form;
	private TextItem projectNameLabel, budget;
	private TextAreaItem projectDescription;
	private DateItem startDate;
	private DateItem endDate;
	private SelectItem projectClient;
	private HLayout hLayout;
	private IButton createProjectButton, editProjectButton, applyButton;

	public NewProjectPanel() {
		super();
	}

	@Override
	public void updateContent() {

		if (this.vPanel == null) {
			this.vPanel = new VLayout();
			this.addComponents();
		} else {
			form.clearValues();
		}
		this.vPanel.addChild(this.form);
		this.vPanel.addChild(hLayout);
		this.addChild(vPanel);
		this.redraw();
	}

	private void addComponents() {
		form = new DynamicForm();
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
		
		budget = new TextItem();
		budget.setTitle("Costo");
		budget.setLength(30);
		budget.setRequired(true);
		
		startDate = new DateItem();
		startDate.setTitle("Fecha Inicio");
		startDate.setRequired(true);
		startDate.setInvalidDateStringMessage("La fecha ingresada no es v&aacute;lida");

		endDate = new DateItem();
		endDate.setTitle("Fecha Fin");
		endDate.setHint("<nobr>Fecha estimada</nobr>");
		endDate.setRequired(true);
		endDate.setInvalidDateStringMessage("La fecha ingresada no es v&aacute;lida");
		
		projectClient = new SelectItem();
		projectClient.setTitle("Clientes");
		projectClient.setMultiple(true);
		projectClient.setMultipleAppearance(MultipleAppearance.PICKLIST);
		projectClient.setRequired(true);
		ClientServicesClientAsync clientService = GWT.create(ClientServicesClient.class);
		
		// TODO: traer el listado de la base de datos!!!!!!!!!!!!!
		projectClient.setValueMap("Gemalto", "Nobleza Picardo", "Tata",	"itMentor", "PetroleraX", "EmpresaX");
		
		createProjectButton = new IButton();
		createProjectButton.setTitle(" Crear Nuevo ");
		createProjectButton.setIcon("../images/ico/add.ico");
		createProjectButton.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				if (form.validate(false)) {
					form.clearValues();
				}
			}
		});
		
		applyButton = new IButton();
		applyButton.setTitle(" Aplicar ");
		applyButton.setIcon("../images/ico/apply.ico");
		applyButton.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				if (form.validate(false)) {
					saveProject();
				}
			}
		});
		
		editProjectButton = new IButton();
		editProjectButton.setTitle(" Editar ");
		editProjectButton.setIcon("../images/ico/modify.ico");
		editProjectButton.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				Window.alert("SE COMENTO CON PRUEBAS");
//				ListGridRecord projectSelected = projectPanel.getProjectSelected();
//				if (projectSelected != null) {
//					ProjectServicesClientAsync projectService = (ProjectServicesClientAsync) GWT.create(ProjectServicesClient.class);
//					String[] id = projectSelected.getAttributeAsStringArray("ProjectId");
//					projectService.getProject(id.toString(), new AsyncCallback<ProjectDTO>(){
//
//						@Override
//						public void onFailure(Throwable caught) {
//							Window.alert("Error al obtener el objeto seleccionado");
//						}
//
//						@Override
//						public void onSuccess(ProjectDTO result) {
//							projectNameLabel.setValue(result.getName());
//							projectDescription.setValue(result.getDescription());
//							budget.setValue(result.getBudget());
//							startDate.setDataPath(result.getInitDate().toString());
//							endDate.setEndDate(result.getEndDate());
//						} 
//					
//					});
//				} else {
//					MessagesModalWindow modal = new MessagesModalWindow("Proyectos", "Se debe seleccionar un proyecto");
//					modal.display();
//				}
			}
		});
		
		hLayout = new HLayout();
		hLayout.setTop(250);
		hLayout.setMembersMargin(20);  
	    hLayout.addMember(createProjectButton);
	    hLayout.addMember(applyButton);
	    hLayout.addMember(editProjectButton);
		this.form.setFields(projectNameLabel, projectDescription, budget, startDate, endDate, projectClient);
		
	}

	private void saveProject() {
		ProjectServicesClientAsync projectService = (ProjectServicesClientAsync) GWT
		.create(ProjectServicesClient.class);

		ProjectDTO project = new ProjectDTO();
		project.setDescription(projectDescription.getValueField());
		project.setName(projectNameLabel.getValueField());
		project.setEndDate(endDate.getValueAsDate());
		project.setInitDate(startDate.getValueAsDate());
		project.setBudget(Double.valueOf(budget.getValueAsString()));
		
		//TODO: UPDATE EL ARBOL projectService.save(project, callback);
		ProjectPanel.getInstance().fetchData();
	}

}
