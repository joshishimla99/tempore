package ar.fi.uba.tempore.gwt.client.panel.task;

import java.util.List;

import ar.fi.uba.tempore.dto.TaskDTO;
import ar.fi.uba.tempore.gwt.client.ClientServicesClient;
import ar.fi.uba.tempore.gwt.client.TaskServicesClient;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DragAppearance;
import com.smartgwt.client.types.HeaderControls;
import com.smartgwt.client.types.LayoutPolicy;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.HeaderControl;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.DoubleClickEvent;
import com.smartgwt.client.widgets.events.DoubleClickHandler;

public class Task extends Window {

	private String estimatedHs;
	private String name;
	private String description;
	private String status;
	private String id;
	private String realHs;
	
	public Task(String taskName, String taskEstimation, String taskDescription, String taskStatus) {

		this.name = taskName;
		this.description = taskDescription;
		this.estimatedHs = taskEstimation;
		this.status = taskStatus;
		this.realHs = "0";
		
		setShowShadow(false);
		setAnimateMinimize(true);
		
		setDragAppearance(DragAppearance.OUTLINE);
		setCanDrop(true);
		setHeaderControls(HeaderControls.MINIMIZE_BUTTON,
				HeaderControls.HEADER_LABEL, new HeaderControl(
						HeaderControl.SETTINGS, new EditTaskHandler(this)), HeaderControls.CLOSE_BUTTON);
		setDragOpacity(30);

		setVPolicy(LayoutPolicy.NONE);
		setOverflow(Overflow.VISIBLE);
		addDoubleClickHandler(new DoubleClickHandler() {
			
			@Override
			public void onDoubleClick(DoubleClickEvent event) {
				TaskServicesClient.Util.getInstance().getChildTask(id, new AsyncCallback<List<TaskDTO>>(){

					@Override
					public void onFailure(Throwable caught) {
						com.google.gwt.user.client.Window.alert("Ha ocurrido un error al intentar recuperar las tareas");
						
					}

					@Override
					public void onSuccess(List<TaskDTO> result) {
						for (int i=0; i< result.size(); i++){
							
						}
						
					}
					
				});
			}
		});
		
		this.setTitle(this.name);
		Label content = new Label("<span style=\" font-weight: bold;\">Estado:</span> <span style=\"color: green; font-weight: bold;\">" + this.status + "</span><br/>"  
                + "<span style=\" font-weight: bold;\">Horas Consumidas: </span>" + this.realHs + "<br/>"  
                + "<span style=\" font-weight: bold;\">Horas Estimadas: </span> "+ this.estimatedHs + "</br>"
                + "<span style=\" font-weight: bold;\">Descripcion: </span> "+ this.description);
		
		this.addItem(content);
	}
	
	
	private class EditTaskHandler implements ClickHandler {

		private Task task;
		
		public EditTaskHandler(Task task) {
			this.task = task;
		}

		@Override
		public void onClick(ClickEvent event) {
			EditTaskModalWindow editTaskModalWin = new EditTaskModalWindow(this.task.name, this.task.description, this.task.estimatedHs, this.task.status);
			editTaskModalWin.show();
		}
	}
}
