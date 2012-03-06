package ar.fi.uba.tempore.gwt.client.panel.task;

import java.util.LinkedHashMap;
import java.util.List;

import ar.fi.uba.tempore.dto.TaskDTO;
import ar.fi.uba.tempore.dto.TaskTypeDTO;
import ar.fi.uba.tempore.gwt.client.TaskServicesClient;
import ar.fi.uba.tempore.gwt.client.TaskTypeServicesClient;
import ar.fi.uba.tempore.gwt.client.panel.project.ProjectPanel;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class EditTaskModalWindow {
	protected static final String ID = "ID_TASK";
	protected static final String NAME = "NAME_TASK";
	protected static final String DESCRIPTION = "DESCRPTION_TASK";
	protected static final String BUDGET = "BUDGET_TASK";
	protected static final String TYPE = "TYPE_TASK";
	protected static final String FATHER = "ID_PADRE_TASK";
	private static final Long HORA = 60*60*1000L;
	
	
	private Window winModal;
	private DynamicForm form;
	private TaskBox father;
	
	public EditTaskModalWindow(TaskBox task){
		super();
		this.father = task;
		final TaskDTO taskDTO = task.getTaskDTO();
		winModal = new Window();  
        winModal.setWidth(360);  
        winModal.setHeight(320);
        winModal.setTitle("Editar Tarea");  
        winModal.setShowMinimizeButton(false);  
        winModal.setIsModal(true);  
        winModal.setShowModalMask(true);  
        winModal.centerInPage();  
        winModal.addCloseClickHandler(new CloseClickHandler() {
			@Override
			public void onCloseClick(CloseClickEvent event) {
				winModal.destroy();				
			}
		}); 
          
        
        final TextItem idLabel = new TextItem(ID, "N&uacute;mero");
        idLabel.setValue(taskDTO.getId().toString());
        idLabel.setDisabled(true);
        
        //Nombre de la tarea
        final TextItem taskNameLabel = new TextItem(NAME, "Nombre");
		taskNameLabel.setValue(taskDTO.getName());
		taskNameLabel.setLength(30);
		taskNameLabel.setRequired(true);

		//description
		final TextAreaItem taskDescription = new TextAreaItem(DESCRIPTION,"Descripci&oacute;n");
		taskDescription.setValue(taskDTO.getDescription());
		taskDescription.setLength(150);
		taskDescription.setRequired(true);
		
		final HiddenItem taskParentId = new HiddenItem(FATHER);
		taskParentId.setValue(taskDTO.getTaskId());
		
		final TextItem estimatedTimeLabel = new TextItem(BUDGET, "Tiempo Estimado");
		estimatedTimeLabel.setMask("###");
		estimatedTimeLabel.setHint("Hs");
		estimatedTimeLabel.setValue(taskDTO.getBudget()/HORA);
		estimatedTimeLabel.setRequired(true);  
        
		final SelectItem taskType = new SelectItem(TYPE, "Tipo");

		form = new DynamicForm();  
        form.setHeight100();  
        form.setWidth100();  
        form.setPadding(5);  
        form.setLayoutAlign(VerticalAlignment.BOTTOM);
		form.setFields(idLabel, taskParentId, taskNameLabel,taskDescription, estimatedTimeLabel, taskType);

		
		
		TaskTypeServicesClient.Util.getInstance().fetch(new AsyncCallback<List<TaskTypeDTO>>() {			
			@Override
			public void onSuccess(List<TaskTypeDTO> result) {
				LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();  
				for (TaskTypeDTO taskTypeDTO : result) {
					valueMap.put(taskTypeDTO.getId().toString(), taskTypeDTO.getName());
				}
				taskType.setValueMap(valueMap);
				taskType.setValue(taskDTO.getTaskTypeDTO().getId());
			}
			@Override
			public void onFailure(Throwable caught) {
				SC.warn("Error en Servicio", "Fallo la carga del combo de Tipo de Tarea", null, null);				
			}
		});
		
		
		IButton updateButton = new IButton();
		updateButton.setTitle("Guardar");
		updateButton.setIcon("../images/ico/save.ico");
		updateButton.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				if (form.validate()) {
					
					final TaskDTO taskDTO = new TaskDTO();
					taskDTO.setProject(ProjectPanel.getInstance().getSelected());
					taskDTO.setId(new Integer(form.getValue(ID).toString()));
					taskDTO.setName(form.getValue(NAME).toString());
					taskDTO.setDescription(form.getValue(DESCRIPTION).toString());
					taskDTO.setBudget(new Long(form.getValue(BUDGET).toString())*HORA);
					
					if (form.getValue(FATHER) == null){
						taskDTO.setTaskId(null);
					} else {
						taskDTO.setTaskId(new Integer(form.getValue(FATHER).toString()));
					}
					
					final TaskTypeDTO taskTypeDTO = new TaskTypeDTO();
					taskTypeDTO.setId(new Integer(form.getValue(TYPE).toString()));
					taskDTO.setTaskTypeDTO(taskTypeDTO);
					
					
					TaskServicesClient.Util.getInstance().validateTask(taskDTO, new AsyncCallback<Boolean>() {
						@Override
						public void onSuccess(Boolean isTaskValid) {
							if (isTaskValid) {
								//Actualizo la tarea
								TaskServicesClient.Util.getInstance().updateTask(taskDTO, new AsyncCallback<TaskDTO>(){
									@Override
									public void onSuccess(TaskDTO result) {
										father.refresh(result);
										winModal.destroy();
									}
									@Override
									public void onFailure(Throwable caught) {
										SC.warn("Error en Servicio", "Ha ocurrido un error al intentar actualizar la tarea", null, null);
									}
								});
							} else {
								SC.warn("El nombre de la tarea ("+taskDTO.getName()+") ya existe en el mismo nivel. Cambie el nombre e intente nuevamente.");
							}
						}
						
						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub
							
						}
					});
					
				}
			}
		});    			

		IButton cancelTaskButton = new IButton();
		cancelTaskButton.setTitle("Cerrar");
		cancelTaskButton.setIcon("../images/ico/close.ico");
		cancelTaskButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				winModal.destroy();
			}
		});  
		
          

        HLayout buttonLayout = new HLayout();
        buttonLayout.setMembersMargin(10);
        buttonLayout.setMargin(10);
        buttonLayout.setAlign(Alignment.CENTER);
        buttonLayout.addMember(updateButton);
        buttonLayout.addMember(cancelTaskButton);
        
        VLayout vLayout = new VLayout();
        vLayout.setMembersMargin(10);
        vLayout.setMargin(10);
        vLayout.addMember(form);
        vLayout.addMember(buttonLayout);
        
        winModal.addItem(vLayout);
	}
	
	public void show(){
		this.winModal.show();
	}
}
