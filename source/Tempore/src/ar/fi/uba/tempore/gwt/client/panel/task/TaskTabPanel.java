package ar.fi.uba.tempore.gwt.client.panel.task;

import java.util.LinkedHashMap;
import java.util.List;

import ar.fi.uba.tempore.dto.ProjectDTO;
import ar.fi.uba.tempore.dto.TaskDTO;
import ar.fi.uba.tempore.dto.TaskTypeDTO;
import ar.fi.uba.tempore.gwt.client.TaskServicesClient;
import ar.fi.uba.tempore.gwt.client.TaskTypeServicesClient;
import ar.fi.uba.tempore.gwt.client.panel.TabsPanelContainer;
import ar.fi.uba.tempore.gwt.client.panel.project.ProjectPanel;
import ar.fi.uba.temporeutils.observer.ProjectObserver;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DragAppearance;
import com.smartgwt.client.types.HeaderControls;
import com.smartgwt.client.types.LayoutPolicy;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.AnimationCallback;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HeaderControl;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.events.CloseClientEvent;
import com.smartgwt.client.widgets.events.DoubleClickEvent;
import com.smartgwt.client.widgets.events.DoubleClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.SectionItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.LayoutSpacer;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.layout.VStack;

public class TaskTabPanel extends TabsPanelContainer implements ProjectObserver {

	private TaskLayout portalLayout;
	private DynamicForm form;
	private Integer parentTaskId = null;
	private BackButton backButton;
	private int level =0;
	private SectionItem  titleTask, firstLevelTask, secondLevelTask;
	private ProjectDTO selectedProjectDTO;

	public TaskTabPanel() {
		super();
		updateContent();
	}

	@Override
	public void destroy() {
		ProjectPanel.getInstance().removeObserver(this);
		super.destroy();
	}

	@Override
	public void updateProjectSelected() {
		selectedProjectDTO = ProjectPanel.getInstance().getSelected();
		if (selectedProjectDTO != null) {
			titleTask.setDefaultValue(selectedProjectDTO.getName());
			form.showItem("titleTask");
			TaskServicesClient.Util.getInstance().getChildTask(selectedProjectDTO.getId(), null, new AsyncCallback<List<TaskDTO>>() {

				@Override
				public void onFailure(Throwable caught) {
					SC.say("Ha ocurrido un error al intentar recuperar el listado de las tareas del proyecto seleccionado");
					
				}

				@Override
				public void onSuccess(List<TaskDTO> result) {
					copy(result);
				}
			});
		}

	}

	public void updateContent() {
		ProjectPanel.getInstance().addObserver(this);
		titleTask = new SectionItem("titleTask");
		portalLayout = new TaskLayout(3);
		portalLayout.setWidth100();
		portalLayout.setHeight100();

		final VLayout vLayout = new VLayout(15);
		vLayout.setMargin(10);
		vLayout.setWidth100();
		final HLayout buttonsLayout = new HLayout();
		buttonsLayout.setAlign(Alignment.LEFT);

		this.backButton = new BackButton();

		buttonsLayout.setHeight(40);
		buttonsLayout.setMembers(backButton);
		buttonsLayout.setAlign(Alignment.RIGHT);

		form = new DynamicForm();
		form.setAutoWidth();
		form.setNumCols(5);

		final ButtonItem addTask = createAddTaskButton();
		
		form.setItems(addTask, titleTask);
		form.hideItem("titleTask");

		HLayout hLayout = new HLayout();
		hLayout.addMember(form);
		hLayout.addMember(buttonsLayout);
		
		vLayout.addMember(hLayout);
		vLayout.addMember(portalLayout);
		addChild(vLayout);
		
		updateProjectSelected();
	}

	public void copy(List<TaskDTO> taskList){
		portalLayout.cleanLayout();
		for (TaskDTO taskDTO : taskList) {
			Task newTask = new Task(taskDTO.getId(), taskDTO.getName(), taskDTO.getBudget(), taskDTO.getDescription(), taskDTO.getTaskTypeDTO().getName());
			portalLayout.addTask(newTask);
			
		}
		this.redraw();
	}
	
	private ButtonItem createAddTaskButton() {
		final ButtonItem addTask = new ButtonItem("addPortlet", "Agregar Tarea");
		addTask.setIcon("../images/application_view_tile.png");
		addTask.setAutoFit(true);

		addTask.setStartRow(false);
		addTask.setEndRow(false);
		addTask.addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {
			public void onClick(
					com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {

				NewTaskModalWindow newTaskModalWin = new NewTaskModalWindow(
						addTask);
				newTaskModalWin.show();
			}
		});
		return addTask;
	}

	/**
	 * Boton back
	 */

	public class BackButton extends ImgButton {
		public BackButton() {
			setSrc("../images/32x32/Back.png");
			setWidth(32);
			addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					goBack();
				}
			});
		}

		private void goBack() {
			if (level > 0){
				if (level == 1){
					parentTaskId = null;
				}
				TaskServicesClient.Util.getInstance().getChildTask(selectedProjectDTO.getId(), parentTaskId, new AsyncCallback<List<TaskDTO>>() {

					@Override
					public void onFailure(Throwable caught) {
						SC.say("Ha ocurrido un error al intentar obtener las tareas del nivel superior");
					}

					@Override
					public void onSuccess(List<TaskDTO> result) {
						--level;
						parentTaskId = result.get(0).getTaskId();
						copy(result);
						
					}
				});
			}

		}
	}

	public SectionItem getSectionItem(){
		if (this.level == 1){
			this.firstLevelTask = new SectionItem();
			return this.firstLevelTask;
		} 
		this.secondLevelTask = new SectionItem();
		return secondLevelTask;
	}
	
	/**
	 * Handler to create new task
	 * 
	 * @author Ludmila
	 * 
	 */
	private class NewTaskModalWindow {
		private Window winModal;

		public NewTaskModalWindow(final ButtonItem addTask) {
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
			final TextItem taskNameLabel = new TextItem();
			taskNameLabel.setTitle("Nombre");
			taskNameLabel.setLength(30);
			taskNameLabel.setRequired(true);

			// description
			final TextAreaItem taskDescription = new TextAreaItem();
			taskDescription.setTitle("Descripci&oacute;n");
			taskDescription.setLength(150);
			taskDescription.setRequired(true);

			final SelectItem taskType = new SelectItem("taskType", "Tipo");
			
			TaskTypeServicesClient.Util.getInstance().fetch(new AsyncCallback<List<TaskTypeDTO>>() {			
				@Override
				public void onSuccess(List<TaskTypeDTO> result) {
					SC.say("" + result.size());
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
			
			final TextItem estimatedTimeLabel = new TextItem();
			estimatedTimeLabel.setTitle("Tiempo Estimado");
			estimatedTimeLabel.setKeyPressFilter("[0-9.]");
			estimatedTimeLabel.setRequired(true);

			IButton editTaskButton = new IButton();
			editTaskButton.setTitle("Guardar");
			editTaskButton.setIcon("../images/ico/save.ico");
			editTaskButton.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {

					if (form.validate()) {
						// TODO: Update the data base
						winModal.destroy();
						updateTaskList(addTask,
								taskNameLabel.getValueAsString(),
								taskType.getValueAsString(),
								Integer.parseInt(estimatedTimeLabel.getValueAsString()),
								taskDescription.getValueAsString());
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

			form.setFields(taskNameLabel, taskDescription, taskType,
					estimatedTimeLabel);
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

		private void updateTaskList(final ButtonItem addTask, String name,
				String type, int estimation, String description) {
			
			TaskDTO task = new TaskDTO();
			task.setDescription(description);
			task.setName(name);
			task.setBudget(estimation);
			TaskTypeDTO taskTypeDTO = new TaskTypeDTO();
			taskTypeDTO.setName(form.getValueAsString("taskType"));
			task.setTaskTypeDTO(taskTypeDTO);
			task.setProject(selectedProjectDTO);
			if (level > 0){
				task.setTaskId(parentTaskId);
			}
			
			TaskServicesClient.Util.getInstance().addTask(task , new AsyncCallback<TaskDTO>() {

				@Override
				public void onFailure(Throwable caught) {
					SC.say("Ha ocurrido un error al intentar actualizar la tarea");
					
				}

				@Override
				public void onSuccess(TaskDTO result) {
					String name = result.getTaskTypeDTO().getName();
					final Task newTask = new Task(result.getId(), result.getName(), result.getBudget(), result.getDescription(), name);
					newTask.setVisible(false);
					TaskColumn column = portalLayout.addTask(newTask);
					
					final LayoutSpacer placeHolder = new LayoutSpacer();
					placeHolder.setRect(newTask.getRect());
					column.addMember(placeHolder, 0); // add to top
					
					// create an outline around the clicked button
					final Canvas outline = new Canvas();
					outline.setLeft(form.getAbsoluteLeft() + addTask.getLeft());
					outline.setTop(form.getAbsoluteTop());
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
			});
			
		}

		public void show() {
			this.winModal.show();
		}

	}

	/**
	 * TaskColumn class definition
	 */
	private class TaskColumn extends VStack {

		public TaskColumn() {

			setMembersMargin(6);

			setAnimateMembers(true);
			setAnimateMemberTime(300);

			setCanAcceptDrop(true);

			setDropLineThickness(4);

			Canvas dropLineProperties = new Canvas();
			dropLineProperties.setBackgroundColor("aqua");
			setDropLineProperties(dropLineProperties);

			setShowDragPlaceHolder(true);

			Canvas placeHolderProperties = new Canvas();
			placeHolderProperties.setBorder("2px solid #8289A6");
			setPlaceHolderProperties(placeHolderProperties);
		}
	}

	/**
	 * TaskLayout class definition
	 */
	private class TaskLayout extends HLayout {
		
		public TaskLayout(int numColumns) {
			setMembersMargin(6);
			for (int i = 0; i < numColumns; i++) {
				addMember(new TaskColumn());
			}
		}

		public TaskColumn addTask(Task task) {
			// find the column with the fewest portlets
			int fewestPortlets = Integer.MAX_VALUE;
			TaskColumn fewestTasksColumn = null;
			for (int i = 0; i < getMembers().length; i++) {
				int numPortlets = ((TaskColumn) getMember(i)).getMembers().length;
				if (numPortlets < fewestPortlets) {
					fewestPortlets = numPortlets;
					fewestTasksColumn = (TaskColumn) getMember(i);
				}
			}
			fewestTasksColumn.addMember(task);
			return fewestTasksColumn;
		}
		
		public void cleanLayout(){
			for (int i = 0; i < getMembers().length; i++){
				TaskColumn taskColumn = (TaskColumn) getMember(i);
				Canvas[] tasks = taskColumn.getMembers();
				for (Canvas canvas : tasks) {
					canvas.destroy();
				}
			}
		}
	}

	
	public class Task extends Window {

		private int estimatedHs;
		private String name;
		private String description;
		private String type;
		private Integer id;
		private String realHs;
		
		public Task(Integer taskId, final String taskName, int taskEstimation, String taskDescription, String taskType) {

			this.id = taskId;
			this.name = taskName;
			this.description = taskDescription;
			this.estimatedHs = taskEstimation;
			this.type = taskType;
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
					TaskServicesClient.Util.getInstance().getChildTask(selectedProjectDTO.getId(), id, new AsyncCallback<List<TaskDTO>>(){

						@Override
						public void onFailure(Throwable caught) {
							SC.say("Ha ocurrido un error al intentar recuperar las tareas");
							
						}

						@Override
						public void onSuccess(List<TaskDTO> result) {
							parentTaskId = id;
							SC.say(String.valueOf(id));
							++level;
							getSectionItem().setDefaultValue(taskName);
							copy(result);
						}
						
					});
				}
			});
			
			this.setTitle(this.name);
			Label content = new Label("<span style=\" font-weight: bold;\">Tipo: </span>" + this.type + "<br/>"  
	                + "<span style=\" font-weight: bold;\">Horas Consumidas: </span>" + this.realHs + "<br/>"  
	                + "<span style=\" font-weight: bold;\">Horas Estimadas: </span> "+ this.estimatedHs + "</br>"
	                + "<span style=\" font-weight: bold;\">Descripci&oacute;n: </span> "+ this.description);
			
			this.addItem(content);
		}
		
		
		private class EditTaskHandler implements ClickHandler {

			private Task task;
			
			public EditTaskHandler(Task task) {
				this.task = task;
			}

			@Override
			public void onClick(ClickEvent event) {
				EditTaskModalWindow editTaskModalWin = new EditTaskModalWindow(this.task.id, this.task.name, this.task.description, String.valueOf(this.task.estimatedHs), this.task.type, selectedProjectDTO.getId());
				editTaskModalWin.show();
				portalLayout.redraw();
			}
		}
	}
	
	
	
}