package ar.fi.uba.tempore.gwt.client.panel.task;

import java.util.LinkedHashMap;
import java.util.List;

import ar.fi.uba.tempore.dto.TaskDTO;
import ar.fi.uba.tempore.dto.TaskTypeDTO;
import ar.fi.uba.tempore.gwt.client.TaskServicesClient;
import ar.fi.uba.tempore.gwt.client.TaskTypeServicesClient;
import ar.fi.uba.tempore.gwt.client.panel.project.ProjectPanel;
import ar.fi.uba.tempore.gwt.client.panel.task.TaskTabPanel.TaskColumn;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.AnimationCallback;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.LayoutSpacer;
import com.smartgwt.client.widgets.layout.VLayout;

public class NewTaskModalWindow extends Window{
	
	protected static final String NAME = "NAME_TASK";
	protected static final String DESCRIPTION = "DESCRPTION_TASK";
	protected static final String BUDGET = "BUDGET_TASK";
	protected static final String TYPE = "TYPE_TASK";
	protected static final String FATHER = "ID_PADRE_TASK";
	private static final Long HORA = 60*60*1000L;
	
	
	private TaskTabPanel taskTabPanel;
	private final DynamicForm form = new DynamicForm();
	
	
	public NewTaskModalWindow(final TaskTabPanel taskTabPanel,final ButtonItem addTask) {
		super();
		this.taskTabPanel = taskTabPanel;
		
		this.setWidth(360);
		this.setHeight(265);
		this.setTitle("Editar Tarea");
		this.setShowMinimizeButton(false);
		this.setIsModal(true);
		this.setShowModalMask(true);
		this.centerInPage();
		this.addCloseClickHandler(new CloseClickHandler() {
			@Override
			public void onCloseClick(CloseClickEvent event) {
				destroy();					
			}
		});

		form.setHeight100();
		form.setWidth100();
		form.setPadding(5);
		form.setLayoutAlign(VerticalAlignment.BOTTOM);

		// Nombre de la tarea
		final TextItem taskNameLabel = new TextItem(NAME, "Nombre");
		taskNameLabel.setLength(30);
		taskNameLabel.setRequired(true);

		// description
		final TextAreaItem taskDescription = new TextAreaItem(DESCRIPTION, "Descripci&oacute;n");
		taskDescription.setLength(150);
		taskDescription.setRequired(true);

		final SelectItem taskType = new SelectItem(TYPE, "Tipo");
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
				SC.warn("Error en Servicio", "Fallo la carga del combo de Tipo de Tarea", null, null);				
			}
		});

		final TextItem estimatedTimeLabel = new TextItem(BUDGET, "Tiempo Estimado");
		estimatedTimeLabel.setMask("###");
		estimatedTimeLabel.setHint("Hs");
		estimatedTimeLabel.setRequired(true);  
		
		form.setFields(taskNameLabel, taskDescription, taskType, estimatedTimeLabel);
		
		
		final IButton saveTaskButton = new IButton();
		saveTaskButton.setTitle("Guardar");
		saveTaskButton.setIcon("../images/ico/save.ico");
		saveTaskButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (form.validate()) {
					TaskDTO taskDTO = new TaskDTO();
					taskDTO.setName(form.getValue(NAME).toString());
					taskDTO.setDescription(form.getValue(DESCRIPTION).toString());
					taskDTO.setBudget(new Long(form.getValue(BUDGET).toString())*HORA);					
					if (taskTabPanel.getLevel() > 0){
						taskDTO.setTaskId(taskTabPanel.getParentTaskId());
					} else {
						taskDTO.setTaskId(null);
					}
					
					taskDTO.setProject(ProjectPanel.getInstance().getSelected());
					final TaskTypeDTO taskTypeDTO = new TaskTypeDTO();
					taskTypeDTO.setId(new Integer(form.getValue(TYPE).toString()));
					taskDTO.setTaskTypeDTO(taskTypeDTO);
					
					updateTaskList(addTask,taskDTO);
				}
			}
		});

		final IButton cancelTaskButton = new IButton();
		cancelTaskButton.setTitle("Cerrar");
		cancelTaskButton.setIcon("../images/ico/close.ico");
		cancelTaskButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				destroy();
			}
		});

		
		VLayout vLayout = new VLayout();
		HLayout buttonLayout = new HLayout();
		buttonLayout.setMembersMargin(10);
		buttonLayout.setAlign(Alignment.CENTER);
		buttonLayout.addMember(saveTaskButton);
		buttonLayout.addMember(cancelTaskButton);

		vLayout.addMember(form);
		vLayout.addMember(buttonLayout);

		this.addItem(vLayout);
	}

	private void updateTaskList(final ButtonItem addTask, final TaskDTO taskDTO) {
		
		TaskServicesClient.Util.getInstance().validateTask(taskDTO, new AsyncCallback<Boolean>() {
			@Override
			public void onSuccess(Boolean isTaskValid) {
				
				if (isTaskValid) {
					//Agregar o Actualiza una tarea 
					TaskServicesClient.Util.getInstance().updateTask(taskDTO , new AsyncCallback<TaskDTO>() {
						@Override
						public void onSuccess(final TaskDTO taskDTO) {
							//Destruimos todas las tareas
							destroy();
							
							//Obtengo las horas de las tareas
							TaskServicesClient.Util.getInstance().getTimeChargedToTask(taskDTO.getId(), new AsyncCallback<Long>() {
								@Override
								public void onSuccess(final Long taskHs) {

									//Calular las horas totales de la tarea
									TaskServicesClient.Util.getInstance().getTotalTimeChargedToChildsTask(taskDTO.getId(), new AsyncCallback<Long>() {

										@Override
										public void onSuccess(Long totalHs) {
											final TaskBox newTask = new TaskBox(taskTabPanel, taskDTO, taskHs, totalHs);
											newTask.setVisible(false);
											TaskColumn column = taskTabPanel.getTaskBoxPanel().addTask(newTask);

											final LayoutSpacer placeHolder = new LayoutSpacer();
											placeHolder.setRect(newTask.getRect());
											column.addMember(placeHolder, 0); // add to top

											// create an outline around the clicked button
											final Canvas outline = new Canvas();
											outline.setLeft(taskTabPanel.getFormTitles().getAbsoluteLeft() + addTask.getLeft());
											outline.setTop(taskTabPanel.getFormTitles().getAbsoluteTop());
											outline.setWidth(addTask.getWidth());
											outline.setHeight(addTask.getHeight());
											outline.setBorder("2px solid #8289A6");
											outline.draw();
											outline.bringToFront();

											outline.animateRect(newTask.getPageLeft(), newTask.getPageTop(),
													newTask.getVisibleWidth(), newTask.getViewportHeight(),
													new AnimationCallback() {
												public void execute(boolean earlyFinish) {
													placeHolder.destroy();
													outline.destroy();
													newTask.show();
												}
											}, 750);
										}
										@Override
										public void onFailure(Throwable caught) {
											SC.warn("Ha ocurrido un error al intentar recuperar las horas totales cargadas a la tarea");
										}

									});
								}
								@Override
								public void onFailure(Throwable caught) {
									SC.warn("Ha ocurrido un error al intentar recuperar las horas cargadas a la tarea");
								}
							});

						}
						@Override
						public void onFailure(Throwable caught) {
							SC.warn("Ha ocurrido un error al intentar actualizar la tarea");					
						}
					});

				} else {
					SC.warn("El nombre de la tarea ("+taskDTO.getName()+") ya existe en el mismo nivel. Cambie el nombre e intente nuevamente.");
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				SC.warn("Error", "Error al validar la nueva Tarea", null, null);
			}
		});
		
		
	}
}
