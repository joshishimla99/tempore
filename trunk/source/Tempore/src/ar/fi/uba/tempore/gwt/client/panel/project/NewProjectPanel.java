package ar.fi.uba.tempore.gwt.client.panel.project;

import java.text.DateFormat;

import javax.swing.text.DateFormatter;

import ar.fi.uba.tempore.gwt.client.ClientServicesClient;
import ar.fi.uba.tempore.gwt.client.ClientServicesClientAsync;
import ar.fi.uba.tempore.gwt.client.panel.menus.ContextChildPanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.smartgwt.client.types.DateDisplayFormat;
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

	public NewProjectPanel() {
		super();
	}

	@Override
	public void updateContent() {
		
		// Nombre del proyecto
		final TextItem txtName = new TextItem();
		txtName.setTitle("Nombre");
		txtName.setLength(30);
		txtName.setHint("<nobr>30 caracteres m&aacute;ximo</nobr>");
		txtName.setRequired(true);

		// description
		final TextAreaItem projectDescription = new TextAreaItem();
		projectDescription.setTitle("Descripci&oacute;n");
		projectDescription.setLength(150);
		projectDescription.setRequired(true);
		
		final TextItem budget = new TextItem();
		budget.setTitle("Costo");
		budget.setLength(30);
		budget.setRequired(true);
		
		final DateItem startDate = new DateItem();
		startDate.setTitle("Fecha Inicio");
		startDate.setUseTextField(true);
		startDate.setDisplayFormat(DateDisplayFormat.TOEUROPEANSHORTDATE);
		startDate.setRequired(true);
		startDate.setInvalidDateStringMessage("La fecha ingresada no es v&aacute;lida");

		final DateItem endDate = new DateItem();
		endDate.setTitle("Fecha Fin");
		endDate.setUseTextField(true);
		endDate.setDisplayFormat(DateDisplayFormat.TOEUROPEANSHORTDATE);
		endDate.setHint("<nobr>Fecha estimada</nobr>");
		endDate.setRequired(true);
		endDate.setInvalidDateStringMessage("La fecha ingresada no es v&aacute;lida");
		
		final SelectItem selClient = new SelectItem();
		selClient.setTitle("Clientes");
		selClient.setMultiple(true);
		selClient.setMultipleAppearance(MultipleAppearance.PICKLIST);
		selClient.setRequired(true);
		ClientServicesClientAsync clientService = GWT.create(ClientServicesClient.class);
		
		// TODO: traer el listado de la base de datos!!!!!!!!!!!!!
		selClient.setValueMap("Gemalto", "Nobleza Picardo", "Tata",	"itMentor", "PetroleraX", "EmpresaX");
		
		final DynamicForm form = new DynamicForm();
		form.setFields(txtName, projectDescription, budget, startDate, endDate, selClient);
		
		final IButton createProjectButton = new IButton();
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
		
		final IButton applyButton = new IButton();
		applyButton.setTitle(" Aplicar ");
		applyButton.setIcon("../images/ico/apply.ico");
		applyButton.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				if (form.validate(false)) {
//					saveProject();
				}
			}
		});
		
		final IButton editProjectButton = new IButton();
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
		
		final HLayout hLayout = new HLayout();
		hLayout.setMembersMargin(10);  
	    hLayout.addMember(createProjectButton);
	    hLayout.addMember(applyButton);
	    hLayout.addMember(editProjectButton);

		final VLayout vLayout = new VLayout();
		vLayout.setMembersMargin(20);
		vLayout.addMember(form);
		vLayout.addMember(hLayout);
		
		addChild(vLayout);
		this.redraw();
	}

//	private void saveProject() {
//		ProjectServicesClientAsync projectService = (ProjectServicesClientAsync) GWT.create(ProjectServicesClient.class);
//
//		ProjectDTO project = new ProjectDTO();
//		project.setDescription(projectDescription.getValueField());
//		project.setName(projectNameLabel.getValueField());
//		project.setEndDate(endDate.getValueAsDate());
//		project.setInitDate(startDate.getValueAsDate());
//		project.setBudget(Double.valueOf(budget.getValueAsString()));
//		
//		//TODO: UPDATE EL ARBOL projectService.save(project, callback);
//		ProjectPanel.getInstance().fetchData();
//	}

}
