package ar.fi.uba.tempore.gwt.client.panel.task;

import java.util.List;

import ar.fi.uba.tempore.dto.TaskDTO;
import ar.fi.uba.tempore.gwt.client.TaskServicesClient;
import ar.fi.uba.tempore.gwt.client.exception.TaskWithHoursChargedException;
import ar.fi.uba.tempore.gwt.client.panel.project.ProjectPanel;
import ar.fi.uba.tempore.gwt.client.util.MySimpleDateFormat;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.types.DragAppearance;
import com.smartgwt.client.types.HeaderControls;
import com.smartgwt.client.types.LayoutPolicy;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.HeaderControl;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.events.DoubleClickEvent;
import com.smartgwt.client.widgets.events.DoubleClickHandler;

/**
 * CLASS TASKBOX
 */
public class TaskBox extends Window {
	private static final String COLOR_DEFAULT = "#00FF00";
	private static final String COLOR_SOPORTE = "#FFFF99";
	private static final String COLOR_DESARROLLO = "#C0C0C0";
	private static final String COLOR_ANALISIS = "#CCFFCC";
	private static final String COLOR_QC = "rgb(254,208,122)";
	private static final String COLOR_ADMINSITRACION = "rgb(255,166,210)";

	private TaskTabPanel taskTabPanel;
	private TaskDTO taskDTO;
	private Long realHs;
	private Long totalHs;
	private Label content;
	
	
	public TaskDTO getTaskDTO(){
		return taskDTO;
	}

	public TaskBox(final TaskTabPanel taskTabPanel, final TaskDTO taskDTO, final long realHs, final long totalRealHs) {
		this.taskTabPanel = taskTabPanel;
		this.taskDTO = taskDTO;
		this.realHs = realHs;
		this.totalHs = totalRealHs;

		String color = COLOR_DEFAULT;
		switch (taskDTO.getTaskTypeDTO().getId()){
		case 1:
			color = COLOR_ANALISIS;
			break;
		case 2:
			color = COLOR_DESARROLLO;
			break;
		case 3:
			color = COLOR_SOPORTE;
			break;
		case 4:
			color = COLOR_QC;
			break;
		case 5:
			color = COLOR_ADMINSITRACION;
			break;
		}

		//Caracteristicas de la ventana que tiene la tarea
		this.setShowShadow(false);
		this.setAnimateMinimize(true);
		this.setDragAppearance(DragAppearance.OUTLINE);
		this.setCanDrop(true);
		this.setHeaderControls(
				HeaderControls.MINIMIZE_BUTTON,
				HeaderControls.HEADER_LABEL, 
				new HeaderControl(HeaderControl.SETTINGS, new EditTaskHandler(this)) , 
				HeaderControls.CLOSE_BUTTON);
		this.setDragOpacity(30);
		this.setVPolicy(LayoutPolicy.NONE);
		this.setHeight(130);

		this.setTitle(taskDTO.getName());
		content = new Label("<span style=\" font-weight: bold;\">Tipo: </span>" + taskDTO.getTaskTypeDTO().getName() + "<br/>"  
				+ "<span style=\" font-weight: bold;\">Horas Consumidas de la tarea: </span>" + MySimpleDateFormat.formatTime(realHs) + "<br/>"
				+ "<span style=\" font-weight: bold;\">Horas Consumidas totales: </span>" + MySimpleDateFormat.formatTime(totalHs) + "<br/>"
				+ "<span style=\" font-weight: bold;\">Horas Estimadas: </span> "+ MySimpleDateFormat.formatTime(taskDTO.getBudget()) + "</br>"
				+ "<span style=\" font-weight: bold;\">Descripci&oacute;n: </span> "+ taskDTO.getDescription());

		content.setBackgroundColor(color);
		content.setHeight100();
		content.setWidth100();
		this.addItem(content);

		this.redraw();


		addDoubleClickHandler(new DoubleClickHandler() {
			@Override
			public void onDoubleClick(DoubleClickEvent event) {
				TaskServicesClient.Util.getInstance().getChildTask(ProjectPanel.getInstance().getSelected().getId(), taskDTO.getId(), new AsyncCallback<List<TaskDTO>>(){
					@Override
					public void onSuccess(List<TaskDTO> result) {
						taskTabPanel.changeChildsTask(taskDTO,result);
					}
					@Override
					public void onFailure(Throwable caught) {
						SC.warn("Ha ocurrido un error al intentar recuperar las tareas");
					}
				});
			}
		});

		addCloseClickHandler(new CloseClickHandler() {				
			@Override
			public void onCloseClick(CloseClickEvent event) {
				// Si el proyecto esta cerrado, suspendido o cancelado no es posible eliminar la tarea -- Regla de negocio
				if (ProjectPanel.getInstance().getSelected().getProjectState().getId() != 4 && ProjectPanel.getInstance().getSelected().getProjectState().getId() != 5 && ProjectPanel.getInstance().getSelected().getProjectState().getId() != 6){
					SC.ask("Eliminar Tarea", "Desea eliminar la tarea seleccionada", new BooleanCallback() {
						@Override
						public void execute(Boolean yes) {
							if(yes) {
								try {
									TaskServicesClient.Util.getInstance().deleteTask(taskDTO.getId(), ProjectPanel.getInstance().getSelected().getId(), new AsyncCallback<String>() {
										@Override
										public void onSuccess(String result) {
											close();
											SC.say("Eliminar Tarea", "La tarea " + result + " y sus hijas, si contenia, se han eliminado satisfactoriamente.");
										}
										@Override
										public void onFailure(Throwable caught) {
											SC.warn("Ha ocurrido un error al intentar eliminar la tarea. Intentelo luego");
										}
									});
								} catch (TaskWithHoursChargedException e) {
									SC.say("Eliminar Tarea", "La tarea seleccionada no puede eliminarse dado la tarea " + e.getTaskName() + " tiene horas cargadas");
								}
							}
						}
					});

				} else{
					SC.warn("No es posible eliminar la tarea dado que el proyecto est&aacute; cerrado");
				}					
			}
		});

	}

	public void close() {
		super.destroy();
	}

	private class EditTaskHandler implements ClickHandler {
		private TaskBox taskBox;

		public EditTaskHandler(TaskBox taskBox) {
			this.taskBox = taskBox;
		}

		@Override
		public void onClick(ClickEvent event) {
			// Si el proyecto esta cerrado no es posible editar las tareas (si es posible editar cuando el proyecto este canccelado o suspendido) - Regla de negocio
			if (ProjectPanel.getInstance().getSelected().getProjectState().getId() != 4){
				if (ProjectPanel.getInstance().getSelected().getIsOwner() == 1 || (ProjectPanel.getInstance().getSelected().getIsOwner() == 0 &&  taskTabPanel.getLevel() > 0) ){
					EditTaskModalWindow editTaskModalWin = new EditTaskModalWindow(taskBox);
					editTaskModalWin.show();
				} else {
					SC.warn("No eres el due&ntildeo del proyecto para poder editar la tarea primaria");
				}
			} else{
				SC.warn("No es posible crear nuevas tareas dado que el proyecto est&aacute; cerrado");
			}
		}
	}

	/**
	 * Actualizacion de la caja de la tarea con los datos guardados
	 * @param taskDTO
	 */
	public void refresh(TaskDTO taskDTO) {
		this.taskDTO = taskDTO;

		this.setTitle(taskDTO.getName()); 
		content.setContents("<span style=\" font-weight: bold;\">Tipo: </span>" + taskDTO.getTaskTypeDTO().getName() + "<br/>"  
				+ "<span style=\" font-weight: bold;\">Horas Consumidas de la tarea: </span>" + MySimpleDateFormat.formatTime(realHs) + "<br/>" 
				+ "<span style=\" font-weight: bold;\">Horas Consumidas totales: </span>" + MySimpleDateFormat.formatTime(totalHs) + "<br/>"
				+ "<span style=\" font-weight: bold;\">Horas Estimadas: </span> "+ MySimpleDateFormat.formatTime(taskDTO.getBudget()) + "</br>"
				+ "<span style=\" font-weight: bold;\">Descripci&oacute;n: </span> "+ taskDTO.getDescription());


		String color = COLOR_DEFAULT;
		switch (taskDTO.getTaskTypeDTO().getId()){
		case 1:
			color = COLOR_ANALISIS;
			break;
		case 2:
			color = COLOR_DESARROLLO;
			break;
		case 3:
			color = COLOR_SOPORTE;
			break;
		case 4:
			color = COLOR_QC;
			break;
		case 5:
			color = COLOR_ADMINSITRACION;
			break;
		}
		content.setBackgroundColor(color);		
	}
}
