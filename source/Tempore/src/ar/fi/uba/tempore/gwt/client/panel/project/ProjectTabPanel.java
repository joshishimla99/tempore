package ar.fi.uba.tempore.gwt.client.panel.project;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import ar.fi.uba.tempore.dto.ClientDTO;
import ar.fi.uba.tempore.dto.ProjectDTO;
import ar.fi.uba.tempore.dto.ProjectStateDTO;
import ar.fi.uba.tempore.gwt.client.ClientServicesClient;
import ar.fi.uba.tempore.gwt.client.ProjectServicesClient;
import ar.fi.uba.tempore.gwt.client.ProjectStateServicesClient;
import ar.fi.uba.tempore.gwt.client.panel.TabsPanelContainer;
import ar.fi.uba.temporeutils.observer.ProjectObserver;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.MultipleAppearance;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class ProjectTabPanel extends TabsPanelContainer implements ProjectObserver {

	private static final String ID_FIELD = "pId";
	private static final String NAME_FIELD = "pName";
	private static final String DESCRIPTION_FIELD = "pDescr";
	private static final String BUDGET_FIELD = "pBudget";
	private static final String START_FIELD = "pStartDate";
	private static final String END_FIELD = "pEndDate";
	private static final String CLIENT_FIELD = "pClient";
	private static final String FILE_FIELD = "pLogo";
	private static final String STATE_FIELD = "pState";
	
	private DynamicForm form;

	
	
	public ProjectTabPanel() {
		super();
		updateContent();
	}
	
	@Override
	public void destroy() {
		ProjectPanel.getInstance().removeObserver(this);
		super.destroy();
	}
	
	public void updateContent() {
		ProjectPanel.getInstance().addObserver(this);
		
		final Label title = new Label("Administraci&oacute;n de Proyectos");
		title.setWidth(200);
		title.setHeight(15);
		
		//FORM
		form = new DynamicForm();		
		final TextItem idCode = new TextItem(ID_FIELD, "C&oacute;digo");
		idCode.setDisabled(true);
		
		final TextItem txtName = new TextItem(NAME_FIELD, "Nombre");
		txtName.setLength(30);
		txtName.setHint("<nobr>30 caracteres m&aacute;ximo</nobr>");
		txtName.setRequired(true);

		final SelectItem selClient = new SelectItem(CLIENT_FIELD, "Clientes");
		selClient.setMultiple(true);
		selClient.setMultipleAppearance(MultipleAppearance.PICKLIST);
		//TODO hacerlo obligatorio
		selClient.setRequired(false);		
		
		ClientServicesClient.Util.getInstance().fetch(new AsyncCallback<List<ClientDTO>>() {			
			@Override
			public void onSuccess(List<ClientDTO> result) {
				FormItem item = form.getItem(CLIENT_FIELD);
				LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();  
				for (ClientDTO clientDTO : result) {
					valueMap.put(clientDTO.getId().toString(), clientDTO.getName());
				}
				item.setValueMap(valueMap);
			}
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Fallo la carga del combo 'Clientes'");				
			}
		});

		final DateItem startDate = new DateItem(START_FIELD, "Fecha Inicio");
		startDate.setUseTextField(true);
		startDate.setDisplayFormat(DateDisplayFormat.TOEUROPEANSHORTDATE);
		startDate.setUseMask(true);
		startDate.setRequired(true);
		startDate.setInvalidDateStringMessage("La fecha ingresada no es v&aacute;lida");

		final DateItem endDate = new DateItem(END_FIELD, "Fecha Fin");
		endDate.setUseTextField(true);
		endDate.setDisplayFormat(DateDisplayFormat.TOEUROPEANSHORTDATE);
		endDate.setHint("<nobr>Fecha estimada</nobr>");
		endDate.setUseMask(true);
		endDate.setRequired(true);
		endDate.setInvalidDateStringMessage("La fecha ingresada no es v&aacute;lida");
		
		final TextItem budget = new TextItem(BUDGET_FIELD, "Presupuesto");
		budget.setHint("$");
		budget.setKeyPressFilter("[0-9]");
		budget.setRequired(true);
		
		final TextAreaItem txtDescription = new TextAreaItem(DESCRIPTION_FIELD, "Descripci&oacute;n");
		txtDescription.setLength(150);
		txtDescription.setWidth(250);
		txtDescription.setRequired(true);

		final SelectItem selState = new SelectItem(STATE_FIELD, "Estado");
		selState.setRequired(true);
		ProjectStateServicesClient.Util.getInstance().findAll(new AsyncCallback<List<ProjectStateDTO>>() {
			@Override
			public void onSuccess(List<ProjectStateDTO> result) {
				LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();  
				for (ProjectStateDTO dto : result) {
					valueMap.put(dto.getId().toString(), dto.getName());
				}
				selState.setValueMap(valueMap);	
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error al cargar los Estados del Proyecto");
			}
		});
		
		form.setFields(	idCode, 
				txtName, 
				selClient,
				startDate, 
				endDate, 
				budget, 
				txtDescription,
				selState);		

		
		
		//BOTONERA
		final IButton createProjectButton = new IButton();
		createProjectButton.setTitle("Nuevo");
		createProjectButton.setIcon("../images/ico/add.ico");
		createProjectButton.addClickHandler(newProjectEvent);
		
		final IButton applyButton = new IButton();
		applyButton.setTitle("Guardar");
		applyButton.setIcon("../images/ico/save.ico");
		applyButton.addClickHandler(saveProjectEvent);
						
		//LAYOUTs
		final HLayout hLayoutButton = new HLayout();
		hLayoutButton.setMembersMargin(10);
		hLayoutButton.setWidth(340);
		hLayoutButton.setAlign(Alignment.RIGHT);
	    hLayoutButton.addMember(createProjectButton);
	    hLayoutButton.addMember(applyButton);

	    final VLayout vLayoutInfo = new VLayout();
//	    vLayoutInfo.setMembersMargin(30);
	    vLayoutInfo.addMember(form);
//	    vLayoutInfo.addMember(uploaderItem);
	    
	    final HLayout hLayoutBody = new HLayout();
//	    hLayoutBody.setMembersMargin(50);
	    hLayoutBody.addMember(vLayoutInfo);
//	    hLayoutBody.addMember(panelImages);
	    
		final VLayout vLayout = new VLayout();		
		vLayout.setMembersMargin(20);
		vLayout.addMember(title);
		vLayout.addMember(hLayoutBody);
		vLayout.addMember(hLayoutButton);
		
		addChild(vLayout);
		this.redraw();
		
		updateProjectSelected();
	}
	
	/**
	 * Evento para crear un nuevo proyecto
	 */
	private ClickHandler newProjectEvent = new ClickHandler(){
		@Override
		public void onClick(ClickEvent event) {
			if (form.validate(false)) {
				form.clearValues();
			}
		}
	};

	/**
	 * Evento para guardar o actualizar un proyecto
	 */
	private ClickHandler saveProjectEvent = new ClickHandler(){
		public void onClick(ClickEvent event) {
			if (form.validate()) {
				ProjectDTO dto = new ProjectDTO();
				copy (form, dto );
				ProjectServicesClient.Util.getInstance().add(dto, new AsyncCallback<ProjectDTO>() {
					@Override
					public void onSuccess(ProjectDTO result) {
						//Actualizo panel del proyecto
						ProjectPanel.getInstance().fetchData();
					}
					@Override
					public void onFailure(Throwable caught) {
						Window.alert("No se pudo guradar los cambios");						
					}
				});
			}
		}
	};

	
	/**
	 * Metodo del ProjectPanel cuando se selecciona otro Proyecto
	 */
	@Override
	public void updateProjectSelected() {
		ProjectDTO selected = ProjectPanel.getInstance().getSelected();
		if (selected != null){
			copy(selected, form);
		}
	}
	
	/**
	 * Copia del formulario al DTO
	 * @param ProjectDTO
	 */
	private void copy (DynamicForm from, ProjectDTO to){
		to.setId((Integer)from.getValue(ID_FIELD));
		to.setDescription(from.getValue(DESCRIPTION_FIELD).toString());
		to.setEndDate((Date) from.getValue(END_FIELD));
		to.setInitDate((Date) from.getValue(START_FIELD));
		to.setName(from.getValue(NAME_FIELD).toString());
		
		to.setBudget(new Float(from.getValue(BUDGET_FIELD).toString()));
		
		//TODO faltan los clientes
		ProjectStateDTO projectState = new ProjectStateDTO();
		projectState.setId(new Integer(form.getValue(STATE_FIELD).toString()));
		to.setProjectState(projectState);
	}
	
	/**
	 * Copia del DTO al formulario
	 * @return DynamicForm
	 */
	private void copy (ProjectDTO from, DynamicForm to){
		form.clearValues();
		to.setValue(ID_FIELD, from.getId());
		to.setValue(NAME_FIELD, from.getName());
		to.setValue(BUDGET_FIELD, from.getBudget());
		to.setValue(DESCRIPTION_FIELD, from.getDescription());
		
		if (from.getEndDate() != null)
			to.setValue(END_FIELD, from.getEndDate());
		if (from.getInitDate() != null)
			to.setValue(START_FIELD, from.getInitDate());
		
		//to.setValue(CLIENT_FIELD, 2);
		//TODO falta Client
		to.setValue(STATE_FIELD, from.getProjectState().getId());
	}
}
