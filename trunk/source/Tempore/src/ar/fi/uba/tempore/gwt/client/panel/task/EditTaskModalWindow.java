package ar.fi.uba.tempore.gwt.client.panel.task;

import ar.fi.uba.tempore.dto.TaskDTO;
import ar.fi.uba.tempore.gwt.client.TaskServicesClient;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.VerticalAlignment;
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
	
	public EditTaskModalWindow(String name, String description, String estimation, String status){
		winModal = new Window();  
        winModal.setWidth(360);  
        winModal.setHeight(265);  
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
        
     // Nombre de la tarea
		TextItem taskNameLabel = new TextItem();
		taskNameLabel.setTitle("Nombre");
		taskNameLabel.setValue(name);
		taskNameLabel.setLength(30);
		taskNameLabel.setRequired(true);

		// description
		TextAreaItem taskDescription = new TextAreaItem();
		taskDescription.setTitle("Descripci&oacute;n");
		taskDescription.setValue(description);
		taskDescription.setLength(150);
		taskDescription.setRequired(true);
		
		TextItem estimatedTimeLabel = new TextItem();
		estimatedTimeLabel.setTitle("Tiempo Estimado");
		estimatedTimeLabel.setValue(estimation);
		estimatedTimeLabel.setKeyPressFilter("[0-9.]");
		estimatedTimeLabel.setRequired(true);  
        
		SelectItem taskStatus = new SelectItem();
		taskStatus.setTitle("Estado");
		taskStatus.setValue(status);
		taskStatus.setValueMap("<span style='color:#FF0000;'>Nueva</span>",
				"<span style='color:#00FF00;'>Cerrada</span>",
				"<span style='color:#0000FF;'>En progreso</span>");

		
		IButton editTaskButton = new IButton();
		editTaskButton.setTitle("Guardar");
		editTaskButton.setIcon("../images/ico/save.ico");
		editTaskButton.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				if (form.validate()) {
					TaskDTO taskDTO = new TaskDTO();
					TaskServicesClient.Util.getInstance().updateTask(taskDTO, new AsyncCallback<Void>(){

						@Override
						public void onFailure(Throwable caught) {
							com.google.gwt.user.client.Window.alert("Ha ocurrido un error al intentar actualizar la tarea");
							
						}

						@Override
						public void onSuccess(Void result) {
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
		
        form.setFields(taskNameLabel,taskDescription, estimatedTimeLabel, taskStatus);  
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
