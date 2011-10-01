package ar.fi.uba.tempore.gwt.client.panel.task;

import java.util.LinkedHashMap;
import java.util.List;

import ar.fi.uba.tempore.dto.ProjectDTO;
import ar.fi.uba.tempore.dto.TaskDTO;
import ar.fi.uba.tempore.dto.TaskTypeDTO;
import ar.fi.uba.tempore.gwt.client.TaskServicesClient;
import ar.fi.uba.tempore.gwt.client.TaskTypeServicesClient;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.events.CloseClientEvent;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class EditTaskModalWindow {

	private Window winModal;
	
	public EditTaskModalWindow(final Integer id, String name, String description, String estimation, String type, final Integer projectId){
		winModal = new Window();  
        winModal.setWidth(360);  
        winModal.setHeight(305);  
        winModal.setTitle("Editar Tarea");  
        winModal.setShowMinimizeButton(false);  
        winModal.setIsModal(true);  
        winModal.setShowModalMask(true);  
        winModal.centerInPage();  
        winModal.addCloseClickHandler(new CloseClickHandler() {  
            public void onCloseClick(CloseClientEvent event) {  
                winModal.destroy();  
            }  
        }); 
        final DynamicForm form = new DynamicForm();  
        form.setHeight100();  
        form.setWidth100();  
        form.setPadding(5);  
        form.setLayoutAlign(VerticalAlignment.BOTTOM);  
        
        final TextItem idLabel = new TextItem("N&uacute;mero");
        idLabel.setValue(String.valueOf(id));
        idLabel.setDisabled(true);
        
     // Nombre de la tarea
		final TextItem taskNameLabel = new TextItem();
		taskNameLabel.setTitle("Nombre");
		taskNameLabel.setValue(name);
		taskNameLabel.setLength(30);
		taskNameLabel.setRequired(true);

		// description
		final TextAreaItem taskDescription = new TextAreaItem();
		taskDescription.setTitle("Descripci&oacute;n");
		taskDescription.setValue(description);
		taskDescription.setLength(150);
		taskDescription.setRequired(true);
		
		final TextItem estimatedTimeLabel = new TextItem();
		estimatedTimeLabel.setTitle("Tiempo Estimado");
		estimatedTimeLabel.setValue(estimation);
		estimatedTimeLabel.setKeyPressFilter("[0-9.]");
		estimatedTimeLabel.setRequired(true);  
        
		final SelectItem taskType = new SelectItem("taskType", "Tipo");
		taskType.setValue(type);
		TaskTypeServicesClient.Util.getInstance().fetch(new AsyncCallback<List<TaskTypeDTO>>() {			
			@Override
			public void onSuccess(List<TaskTypeDTO> result) {
				LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();  
				for (TaskTypeDTO taskTypeDTO : result) {
					valueMap.put(taskTypeDTO.getId().toString(), taskTypeDTO.getName());
				}
				taskType.setValueMap(valueMap);
			}
			@Override
			public void onFailure(Throwable caught) {
				SC.say("Fallo la carga del combo 'Clientes'");				
			}
		});

		
		IButton editTaskButton = new IButton();
		editTaskButton.setTitle("Guardar");
		editTaskButton.setIcon("../images/ico/save.ico");
		editTaskButton.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				if (form.validate()) {
					TaskDTO taskDTO = new TaskDTO();
					ProjectDTO projectDTO = new ProjectDTO();
					projectDTO.setId(projectId);
					taskDTO.setProject(projectDTO);
					taskDTO.setId(id);
					taskDTO.setName(taskNameLabel.getValueAsString());
					taskDTO.setDescription(taskDescription.getValueAsString());
					taskDTO.setBudget(Integer.parseInt(estimatedTimeLabel.getValueAsString()));
					final TaskTypeDTO taskTypeDTO = new TaskTypeDTO();
					taskTypeDTO.setName(form.getValueAsString("taskType"));
					taskDTO.setTaskTypeDTO(taskTypeDTO);
					
					TaskServicesClient.Util.getInstance().updateTask(taskDTO, new AsyncCallback<TaskDTO>(){

						@Override
						public void onFailure(Throwable caught) {
							com.google.gwt.user.client.Window.alert("Ha ocurrido un error al intentar actualizar la tarea");
							
						}

						@Override
						public void onSuccess(TaskDTO result) {
							winModal.destroy();
							
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
		
        form.setFields(idLabel, taskNameLabel,taskDescription, estimatedTimeLabel, taskType);  
        VLayout vLayout = new VLayout();
        HLayout buttonLayout = new HLayout();
        buttonLayout.setMembersMargin(10);
        buttonLayout.setAlign(Alignment.CENTER);
        buttonLayout.addMember(editTaskButton);
        buttonLayout.addMember(cancelTaskButton);
        vLayout.addMember(form);
        vLayout.addMember(buttonLayout);
        
        winModal.addItem(vLayout);
	}
	
	public void show(){
		this.winModal.show();
	}
}
