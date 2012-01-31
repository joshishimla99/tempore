package ar.fi.uba.tempore.gwt.client.panel.task;

import java.util.List;

import ar.fi.uba.tempore.dto.ProjectDTO;
import ar.fi.uba.tempore.dto.TaskDTO;
import ar.fi.uba.tempore.gwt.client.TaskServicesClient;
import ar.fi.uba.tempore.gwt.client.panel.TabsPanelContainer;
import ar.fi.uba.tempore.gwt.client.panel.project.ProjectPanel;
import ar.fi.uba.temporeutils.observer.ProjectObserver;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.SectionItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.layout.VStack;

public class TaskTabPanel extends TabsPanelContainer implements ProjectObserver {
	private TaskLayout taskBoxPanel;
	private DynamicForm formTitles;
	private Integer parentTaskIdForBackButton = null, parentTaskId = null;
	private BackButton backButton;
	private int level =0;

	private SectionItem  titleTask, firstLevelTask, secondLevelTask;
	private ProjectDTO projectSelected;
	private final ButtonItem addTask;
	private final TaskTreePanel taskTree;
	private static final String HELPTEXT = "<br><b>Administraci&oacute;n de Tareas</b><br>Esta p&aacute;gina le permitir&aacute; modificar, agregar y eliminar tareas al proyecto seleccionado. Las tareas se visualizan comenzando por las tareas primarias y navegando se desplegar&aacute;n las tareas secundarias." +
			"<br> La informaci&oacute;n que es posible actualizar o definir para cada tarea es la siguiente:" +
			"<br><b>Nombre: </b>Nombre de la tarea" +
			"<br><b>Descripci&oacute;n: </b>Breve descripci&oacute;n de la tarea" +
			"<br><b>Tipo: </b>Tipo de tarea. Los posibles tipos son: An&aacute;lisis, Desarrollo y Soporte." +
			"<br><b>Tiempo Estimado: </b>Tiempo estimado de la tarea en horas." +
			"<br><br><b>Creaci&oacute;n de Nueva Tarea</b><br>Para crear una nueva tarea seleccione el bot&oacute;n Agregar Tarea, complete todos los campos y luego presione Guardar. Si uno o m&aacute;s de los campos no son ingresados, no ser&aacute; posible guardar la tarea." +  
			"<br><br><b>Modificaci&oacute;n de una Tarea</b><br>Seleccione la estrella ubicada en la parte superior izquierda de la tarea deseada, modifique el/los campo/s que se requieran y luego presione Guardar." +  
			"<br><br><b>Eliminaci&oacute;n de una Tarea</b><br>Seleccione la cruz ubicada en la parte superior izquierda de la tarea que se desea eliminar.";  

	public TaskTabPanel() {
		super();
		Label title = new Label("Administraci&oacute;n de Tareas");
		title.setWidth(200);
		title.setHeight(15);
		title.setIcon("[SKIN]/actions/help.png");
		title.setStyleName("Informal");
		title.setIconOrientation("right");
		title.addIconClickHandler(new com.smartgwt.client.widgets.events.IconClickHandler() {
			@Override
			public void onIconClick(
					com.smartgwt.client.widgets.events.IconClickEvent event) {
				SC.say(HELPTEXT);
			}
		});

		setFormTitles(new DynamicForm());
		getFormTitles().setAutoWidth();
		getFormTitles().setWidth100();
		
		addTask = createAddTaskButton();

		titleTask = new SectionItem("titleTask");
		titleTask.setWidth("100%");

		firstLevelTask = new SectionItem("firstLevelTask");
		firstLevelTask.setAlign(Alignment.RIGHT);
		firstLevelTask.setLeft(10);

		secondLevelTask = new SectionItem("secondLevelTask");
		secondLevelTask.setAlign(Alignment.RIGHT);
		secondLevelTask.setLeft(20);

		getFormTitles().setItems(titleTask, firstLevelTask, secondLevelTask);
		getFormTitles().hideItem("titleTask");
		getFormTitles().hideItem("firstLevelTask");
		getFormTitles().hideItem("secondLevelTask");



		setTaskBoxPanel(new TaskLayout(4));
		getTaskBoxPanel().setWidth100();
		getTaskBoxPanel().setHeight100();

		backButton = new BackButton();

		final HLayout upLayout = new HLayout();
		upLayout.addMember(backButton);
		upLayout.addMember(getFormTitles());
		
		DynamicForm formAddButton = new DynamicForm(); 
		formAddButton.setItems(addTask);
		
		taskTree = new TaskTreePanel(this);
		taskTree.setWidth("25%");
		taskTree.setHeight100();
		
		final VLayout taskBoxLayout = new VLayout(15);
		taskBoxLayout.setMargin(10);
		taskBoxLayout.setWidth("75%");
		taskBoxLayout.setHeight100();
		taskBoxLayout.addMember(formAddButton);
		taskBoxLayout.addMember(upLayout);
		taskBoxLayout.addMember(getTaskBoxPanel());
		
		
		final HLayout workLayout = new HLayout();
		workLayout.setWidth100();
		workLayout.setHeight100();
//		workLayout.addMember(taskTree);
		workLayout.addMember(taskBoxLayout);
		
		final VLayout mainLayout = new VLayout();
		mainLayout.setWidth100();
		mainLayout.setHeight100();
		mainLayout.addMember(title);
		mainLayout.addMember(workLayout);

		this.addChild(mainLayout);
	}

	@Override
	public void refreshPanel() {
		ProjectPanel.getInstance().addObserver(this);
		updateProjectSelected();
	}

	@Override
	public void freePanel() {
		ProjectPanel.getInstance().removeObserver(this);
	}

	@Override
	public void updateProjectSelected() {
		//Limpio panel de tareas
		getTaskBoxPanel().cleanLayout();
		
		projectSelected = ProjectPanel.getInstance().getSelected();
		getFormTitles().hideItem("firstLevelTask");
		getFormTitles().hideItem("secondLevelTask");

		parentTaskIdForBackButton = null;
		parentTaskId = null;
		level =0;
		if (projectSelected != null) {
			taskTree.updateTaskTree();
			
			titleTask.setDefaultValue(projectSelected.getName());
			getFormTitles().showItem("titleTask");
			TaskServicesClient.Util.getInstance().getChildTask(projectSelected.getId(), null, new AsyncCallback<List<TaskDTO>>() {
				@Override
				public void onSuccess(List<TaskDTO> result) {
					createAllTasksLevel(result);
				}
				@Override
				public void onFailure(Throwable caught) {
					SC.say("Ha ocurrido un error al intentar recuperar el listado de las tareas del proyecto seleccionado");				
				}
			});
		}

	}



	/**
	 * Encargado de crear todas los BOX de las tareas 
	 * @param taskList Lista de tareas del mismo nivel
	 */
	public void createAllTasksLevel (List<TaskDTO> taskList){
		getTaskBoxPanel().cleanLayout();
		
		
		final TaskTabPanel thisPanel = this;
		for (final TaskDTO taskDTO : taskList) {
			//Horas cargadas a la tarea (sin las subhijas)
			TaskServicesClient.Util.getInstance().getTimeChargedToTask(taskDTO.getId(), new AsyncCallback<Long>() {
				@Override
				public void onSuccess(final Long taskHours) {
					//Horas cargada a las tareas hijas y subhijas del id de la tarea 
					TaskServicesClient.Util.getInstance().getTotalTimeChargedToChildsTask(taskDTO.getId(), new AsyncCallback<Long>() {
						@Override
						public void onSuccess(Long totalTaskHours) {
							//TASK BOX
							TaskBox newTaskBox = new TaskBox(thisPanel,taskDTO,taskHours,totalTaskHours);
							getTaskBoxPanel().addTask(newTaskBox);
						}
						@Override
						public void onFailure(Throwable caught) {
							SC.say("Ha ocurrido un error al intentar recuperar las horas totales cargadas a la tarea");
						}
					});
				}
				@Override
				public void onFailure(Throwable caught) {
					SC.say("Ha ocurrido un error al intentar recuperar las horas cargadas a la tarea");

				}
			});


		}
		this.redraw();
	}

	private ButtonItem createAddTaskButton() {
		final ButtonItem addTask = new ButtonItem("addPortlet", "Agregar Tarea");
		addTask.setIcon("../images/application_view_tile.png");
		addTask.setAutoFit(true);

		addTask.setStartRow(false);
		addTask.setEndRow(false);
		
		final TaskTabPanel thisPanel = this;
		addTask.addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {
			public void onClick(
					com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
				// Si el proyecto esta cerrado,cancelado o suspendido no se pueden crear nuevas tareas - Regla de negocio
				if (projectSelected.getProjectState().getId() != 4 && projectSelected.getProjectState().getId() != 5 && projectSelected.getProjectState().getId() != 6){
					if (projectSelected.getIsOwner() == 1 || (projectSelected.getIsOwner() == 0 && level > 0)){
						NewTaskModalWindow newTaskModalWin = new NewTaskModalWindow(thisPanel, addTask);
						newTaskModalWin.show();
					} else {
						SC.warn("No eres el due&ntildeo del proyecto para crear tareas primarias");
					}
				} else{
					SC.warn("No es posible crear nuevas tareas dado que el proyecto est&aacute; cerrado");
				}
			}
		});
		return addTask;
	}

	private void hideSection(){
		if (this.level == 1){
			this.getFormTitles().hideItem("firstLevelTask");
		} else {
			if (this.level == 2){
				this.getFormTitles().hideItem("secondLevelTask");
			}
		}
	}

	private SectionItem getSectionItem(){
		if (this.level == 1){
			this.getFormTitles().showItem("firstLevelTask");
			return this.firstLevelTask;
		} 
		this.getFormTitles().showItem("secondLevelTask");
		return secondLevelTask;
	}

	public void changeChildsTask(TaskDTO taskPartentDTO, List<TaskDTO> listTaskChild) {
		parentTaskId = taskPartentDTO.getId();
		if (level != 1){
			parentTaskIdForBackButton = taskPartentDTO.getId();;
		}
		++level;
		getSectionItem().setDefaultValue(taskPartentDTO.getName());
		createAllTasksLevel(listTaskChild);
	}
	
	
	/**
	 * CLASS: Boton para retroceder en el nivel de las tareas
	 */
	public class BackButton extends ImgButton {
		public BackButton() {
			setSrc("../images/png/task/arrow_up.png");
			setSize(32);
			setShowFocusedIcon(false);
			
			addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					if (level > 0){
						if (level == 1){
							parentTaskIdForBackButton = null;
							parentTaskId = null;
						}
						TaskServicesClient.Util.getInstance().getChildTask(projectSelected.getId(), parentTaskIdForBackButton, new AsyncCallback<List<TaskDTO>>() {
							@Override
							public void onSuccess(List<TaskDTO> result) {
								hideSection();
								--level;
								parentTaskIdForBackButton = result.get(0).getTaskId();
								parentTaskId = parentTaskIdForBackButton ;
								createAllTasksLevel(result);
							}
							@Override
							public void onFailure(Throwable caught) {
								SC.say("Ha ocurrido un error al intentar obtener las tareas del nivel superior");
							}
						});
					}
				}
			});
		}
	}



	/**
	 * CLASS
	 */


	/**
	 * CLASS TaskColumn class definition
	 */
	public class TaskColumn extends VStack {

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
	 * CLASS TaskLayout class definition
	 */
	public class TaskLayout extends HLayout {

		public TaskLayout(int numColumns) {
			setMembersMargin(6);
			for (int i = 0; i < numColumns; i++) {
				addMember(new TaskColumn());
			}
		}

		public TaskColumn addTask(TaskBox task) {
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

	
	
	public Integer getParentTaskIdForBackButton() {
		return parentTaskIdForBackButton;
	}
	public void setParentTaskIdForBackButton(Integer parentTaskIdForBackButton) {
		this.parentTaskIdForBackButton = parentTaskIdForBackButton;
	}
	public Integer getParentTaskId() {
		return parentTaskId;
	}
	public void setParentTaskId(Integer parentTaskId) {
		this.parentTaskId = parentTaskId;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}

	public TaskLayout getTaskBoxPanel() {
		return taskBoxPanel;
	}

	public void setTaskBoxPanel(TaskLayout taskBoxPanel) {
		this.taskBoxPanel = taskBoxPanel;
	}

	public DynamicForm getFormTitles() {
		return formTitles;
	}

	public void setFormTitles(DynamicForm formTitles) {
		this.formTitles = formTitles;
	}

	
}
