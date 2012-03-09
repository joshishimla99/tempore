package ar.fi.uba.tempore.gwt.client.panel.time;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ar.fi.uba.tempore.dto.TaskTimeDTO;
import ar.fi.uba.tempore.dto.TimeFilterDTO;
import ar.fi.uba.tempore.gwt.client.TaskTimeServicesClient;
import ar.fi.uba.tempore.gwt.client.login.SessionUser;
import ar.fi.uba.tempore.gwt.client.panel.TabsPanelContainer;
import ar.fi.uba.tempore.gwt.client.panel.counter.CounterTimePanel;
import ar.fi.uba.tempore.gwt.client.panel.project.ProjectPanel;
import ar.fi.uba.tempore.gwt.client.panel.report.ResumeReportHoursWeek;
import ar.fi.uba.temporeutils.observer.ProjectObserver;
import ar.fi.uba.temporeutils.observer.TimeCounterObserver;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.DragDataAction;
import com.smartgwt.client.types.GroupStartOpen;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.SelectionType;
import com.smartgwt.client.types.TimeDisplayFormat;
import com.smartgwt.client.types.TreeModelType;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.DateChooser;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.DataChangedEvent;
import com.smartgwt.client.widgets.events.DataChangedHandler;
import com.smartgwt.client.widgets.events.DragMoveEvent;
import com.smartgwt.client.widgets.events.DragMoveHandler;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TimeItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridEditorContext;
import com.smartgwt.client.widgets.grid.ListGridEditorCustomizer;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.SummaryFunction;
import com.smartgwt.client.widgets.grid.events.DataArrivedEvent;
import com.smartgwt.client.widgets.grid.events.DataArrivedHandler;
import com.smartgwt.client.widgets.grid.events.RecordDropEvent;
import com.smartgwt.client.widgets.grid.events.RecordDropHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tree.Tree;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeGridField;
import com.smartgwt.client.widgets.tree.TreeNode;

public class DragDropTimePanel extends TabsPanelContainer implements ProjectObserver, TimeCounterObserver{

	public static final String COL_ID = "idTaskCol";
	public static final String COL_HOURS = "hoursCol";
	public static final String COL_COMMENTS = "commentsCol";
	public static final String COL_DATE = "dateCol";
	public static final String COL_NAME = "nameCol";
	public static final String COL_DESCRIPTION = "descriptionCol";

	public static final String COL_TASK_ID = "taskId";

	public static final String COL_PROJECT_NAME = "projectCol";
	public static final String COL_PROJECT_ID = "projectIdCol";	

	public static final String COL_PARENT_ID = "ReportsTo";

	private final TreeGrid tasksTree = new TreeGrid();
	private final ListGrid hoursCountGrid = new ListGrid();
	private final DateChooser dateChooser = new DateChooser();
	private final ResumeReportHoursWeek draw = new ResumeReportHoursWeek();
	private Boolean treeClosed = true;
	private static final String HELPTEXT = "<br><b>Asignaci&oacute;n de horas trabajadas a tareas</b><br>Esta p&aacute;gina le permitir&aacute; administrar las horas cargadas." +
			"<br> La informaci&oacute;n que se maneja para cada tarea cargada es la siguiente:" +
			"<br><b>Usuario: </b>Nombre del usuario a ser utilizado para ingresar al sistema." +
			"<br><b>Nombre: </b>Nombre del usuario." +
			"<br><b>Cantidad de Horas: </b>Representa la cantidad de horas cargadas para la tarea seleccionada." +
			"<br><br><b>Carga de Horas</b><br>Para cargar horas a una tarea se debe seleccionar la tarea de la parte superior, arrastre esa tarea al recuadro inferior donde podr&aacute; asignar las horas deseadas a la tarea. " +  
			"<br><br><b>Modificaci&oacute;n de Horas Cargadas</b><br>Seleccione la tarea que desea modificar en el recuadro inferior y autom&aacute;ticamente el campo Horas ser&aacute; editable." +  
			"<br><br><b>Eliminaci&oacute;n de Horas Cargadas</b><br>Seleccione la l&iacute;nea con las tareas que desee eliminar y presione el bot&oacute;n Menos ubicado sobre el sector derecho de la l&iacute;nea.";

	public DragDropTimePanel(){
		super();

		//TITULO
		final Label title = new Label("Asignaci&oacute;n de horas trabajadas a tareas");
		title.setWidth100();
		title.setHeight(30);
		title.setIcon("[SKIN]/actions/help.png");
		title.setStyleName("titleStyleInformal");
		title.setIconOrientation("right");
		title.addIconClickHandler(new com.smartgwt.client.widgets.events.IconClickHandler() {
			@Override
			public void onIconClick(com.smartgwt.client.widgets.events.IconClickEvent event) {
				SC.say(HELPTEXT);
			}
		});

		//FECHA PARA CARGA DE HORAS		
		dateChooser.setHeight(50);
		dateChooser.setWidth("50%");
		dateChooser.setShowTodayButton(false);
		dateChooser.addDataChangedHandler(new DataChangedHandler() {
			public void onDataChanged(DataChangedEvent event) {
				refreshTimeGrid();
			}
		});

		//LISTADO DE TAREAS
		tasksTree.setShowDropIcons(true);
		tasksTree.setHeight100();
		tasksTree.setWidth100();
		tasksTree.setShowDragShadow(true);
		tasksTree.setAddDropValues(false);
		tasksTree.setLoadingDataMessage("${loadingImage}&nbsp;Cargando...");
		TreeGridField tfId = new TreeGridField(COL_TASK_ID, "N&uacute;mero");
		tfId.setHidden(true);
		TreeGridField tfName = new TreeGridField(COL_NAME, "Nombre");
		TreeGridField tfDescription = new TreeGridField(COL_DESCRIPTION, "Descripci&oacute;n");
		tasksTree.setCanReorderRecords(false);  
		tasksTree.setCanDragRecordsOut(true);  
		tasksTree.setCanAcceptDroppedRecords(false);  
		tasksTree.setDragDataAction(DragDataAction.COPY);
		tasksTree.setNodeIcon("../images/tasks.png");  
		tasksTree.setFolderIcon("../images/folder.png");  
		tasksTree.setEmptyMessage("El Proyecto seleccionado no posee Tareas cargadas.");  
		tasksTree.setShowConnectors(true);
		tasksTree.setFields(tfId, tfName, tfDescription);  
		tasksTree.setShowAllRecords(true);  
		tasksTree.setAutoFetchData(false);
		tasksTree.addDragMoveHandler(new DragMoveHandler() {
			@Override
			public void onDragMove(DragMoveEvent event) {
				Integer idState = ProjectPanel.getInstance().getSelected().getProjectState().getId();
				String nameState = ProjectPanel.getInstance().getSelected().getProjectState().getName();
				if (idState == 4 || idState == 5 || idState == 6){
					SC.say("El proyecto est&aacute; en el estado "+nameState+". Y los proyectos en este estado, no se permite la cargar horas");
				}
			}
		});
		
        
		//TABLA DE CARGA DE HORAS
		hoursCountGrid.setAddDropValues(false);
		hoursCountGrid.setLoadingDataMessage("${loadingImage}&nbsp;Cargando...");
		hoursCountGrid.addRecordDropHandler(new RecordDropHandler() {
			public void onRecordDrop(RecordDropEvent event) {
				event.getDropRecords()[0].setAttribute(COL_DATE, dateChooser.getData());	
			}
		});

		hoursCountGrid.setDuplicateDragMessage("Esta tarea ya existe...");
		hoursCountGrid.setPreventDuplicates(false);		

		hoursCountGrid.setHeight100();
		hoursCountGrid.setWidth100();  
		hoursCountGrid.setDataSource(HourCountDataSource.getInstance());  
		hoursCountGrid.setShowAllRecords(true);  
		hoursCountGrid.setEmptyMessage("Arrastr&aacute las tareas ac&aacute");  
		hoursCountGrid.setCanAcceptDroppedRecords(true);  
		hoursCountGrid.setDragDataAction(DragDataAction.MOVE);  
		hoursCountGrid.setCanEdit(true);
		hoursCountGrid.setCanRemoveRecords(true); 
		hoursCountGrid.setCanAcceptDrop(true);
		hoursCountGrid.setAutoSaveEdits(true);		
		hoursCountGrid.setGroupByField(COL_PROJECT_NAME); 
		hoursCountGrid.setShowGridSummary(true);
		hoursCountGrid.setShowGroupSummary(true);
		hoursCountGrid.setShowGroupSummaryInHeader(true); 
		hoursCountGrid.setGroupStartOpen(GroupStartOpen.ALL);
		hoursCountGrid.addDataArrivedHandler(new DataArrivedHandler() {
			@Override
			public void onDataArrived(DataArrivedEvent event) {
				draw.drawReport(dateChooser.getData());				
			}
		});

		final ListGridField lfProject = new ListGridField(COL_PROJECT_NAME, "Proyecto");
		lfProject.setWidth("10%");
		lfProject.setCanEdit(false);
		lfProject.setHidden(true);
		final ListGridField lfName = new ListGridField(COL_NAME, "Nombre Tarea");
		lfName.setWidth("25%");
		lfName.setCanEdit(false);
		lfName.setSummaryFunction(new SummaryFunction() {  
			public Object getSummaryValue(Record[] records, ListGridField field) {  
				Set<String> uniqueCategories = new HashSet<String>();  

				for (int i = 0; i < records.length; i++) {  
					Record record = records[i];  
					uniqueCategories.add(record.getAttribute(COL_NAME)); 
				}  
				return uniqueCategories.size() + " Tareas";  
			}  
		});
		final ListGridField lfDescription = new ListGridField(COL_DESCRIPTION, "Descripcion");
		lfDescription.setCanEdit(false);
		final ListGridField lfDate = new ListGridField(COL_DATE, "Fecha");
		lfDate.setType(ListGridFieldType.DATE);
		lfDate.setWidth("60");
		lfDate.setCanEdit(false);
		//Ver si vale la pena
		lfDate.setHidden(true);
		final ListGridField lfHours = new ListGridField(COL_HOURS, "Horas");
		lfHours.setWidth("60");
		
		lfHours.setIncludeInRecordSummary(false);
		lfHours.setType(ListGridFieldType.TIME);
		lfHours.setTimeFormatter(TimeDisplayFormat.TOSHORT24HOURTIME);
		lfHours.setSummaryFunction(new SummaryFunction() {  
			public Object getSummaryValue(Record[] records, ListGridField field) {  
				Long acum = 0L;
				for (int i = 0; i < records.length; i++) {  
					Record record = records[i];  
					acum += record.getAttributeAsDate(COL_HOURS).getTime(); 
				}
				return DateTimeFormat.getFormat(PredefinedFormat.HOUR_MINUTE).format(new Date(acum - ((records.length-1) * 10800000)));
			}   
		});



		final ListGridField lfComments = new ListGridField(COL_COMMENTS,"Comentarios");
		lfComments.setWidth("40%");
		final ListGridField lfProjectId = new ListGridField(COL_PROJECT_ID);
		lfProjectId.setHidden(true);
		final ListGridField lfTaskId = new ListGridField(COL_TASK_ID);		
		lfTaskId.setHidden(true);

//		hoursCountGrid.setFields(lfTaskId, lfProject, lfName, lfDescription, lfDate, lfHours, lfComments, lfProjectId);
		hoursCountGrid.setFields(lfTaskId, lfProject, lfName, lfDate, lfHours, lfComments, lfProjectId);

		hoursCountGrid.setEditorCustomizer(new ListGridEditorCustomizer() {  
			public FormItem getEditor(ListGridEditorContext context) {  
				ListGridField field = context.getEditField();  
				if (field.getName().equals(COL_DATE)) {  
					return new DateItem();
				}
				if (field.getName().equals(COL_HOURS)) {
					TimeItem time = new TimeItem(COL_HOURS);
					time.setTimeFormatter(TimeDisplayFormat.TOSHORT24HOURTIME);
					time.setUseMask(true);
				}
				if (field.getName().equals(COL_COMMENTS)) {  
					TextAreaItem textItem = new TextAreaItem(COL_COMMENTS);  
					textItem.setShowHint(true);  
					textItem.setShowHintInField(true);  
					textItem.setHint("comentarios...");  
					return textItem; 
				}
				return context.getDefaultProperties();}
		});

		draw.setHeight(170);
		draw.setWidth(270);
		
		final ImgButton expand = new ImgButton();
		expand.setShowRollOver(false);
		expand.setActionType(SelectionType.CHECKBOX);
		expand.setSize(16);
		expand.setSrc("../images/png/time/expandCollapse.png");
		   
		expand.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (treeClosed){
					tasksTree.getTree().openAll();
					treeClosed = false;
				} else {
					tasksTree.getTree().closeAll();
					treeClosed = true;
				}
			}
		});
		
		
		//LAYOUTs
		final HLayout hort0 = new HLayout();
		hort0.setWidth100();
		hort0.addMember(dateChooser);
		hort0.addMember(draw);
		
		
		final VLayout vert1 = new VLayout();
		vert1.setHeight100();
		vert1.addMember(hort0);
		vert1.addMember(hoursCountGrid);
		
		final VLayout vert2 = new VLayout();
		vert2.setWidth("50%");
		vert2.setHeight100();
		vert2.setEdgeImage("../edges/blue/sharpframe_10.png");
//		vert2.setDragAppearance(DragAppearance.TARGET);  
		vert2.setShowResizeBar(true);
		vert2.setCanDragResize(true);  
		vert2.setResizeFrom("R");
		vert2.setMinWidth(200);  
		vert2.addMember(expand);
		vert2.addMember(tasksTree);
		
		
		
		//Agrego los Componentes al Panel
		final HLayout horizontal = new HLayout();
		horizontal.setHeight100();
		horizontal.setWidth100();
		horizontal.addMember(vert2);
		horizontal.addMember(vert1);
		
		final VLayout main = new VLayout();
		main.setHeight100();
		main.setWidth100();
		main.addMember(title);
		main.addMember(horizontal);

		this.addChild(main);		

		//refresco panel de Horas
		refreshTimeGrid();
		refreshPanel();
	}

	/**
	 * Va ha buscar la informacion de la grilla de las horas con los filtros elegidos en la pantalla
	 * Los filtros son la fecha y el usuaior logueado
	 */
	private void refreshTimeGrid() {
		draw.drawReport(dateChooser.getData());

		TimeFilterDTO filter = new TimeFilterDTO();
		filter.setDateFilter(dateChooser.getData());
		filter.setUserId(SessionUser.getInstance().getUser().getId());

		HourCountDataSource.getInstance().setId(filter);
		
		hoursCountGrid.invalidateCache();
		hoursCountGrid.fetchData();
	}


	@Override
	public void updateProjectSelected() {
		if (ProjectPanel.getInstance().getSelected() != null){
//			tasksTree.fetchData();
			TaskTimeServicesClient.Util.getInstance().fetch(ProjectPanel.getInstance().getSelected().getId(), new AsyncCallback<List<TaskTimeDTO>>() {
				@Override
				public void onSuccess(List<TaskTimeDTO> listTask) {

					final Tree dataTaskTree = new Tree();
			        dataTaskTree.setModelType(TreeModelType.PARENT);  
			        dataTaskTree.setRootValue(0);  
			        dataTaskTree.setNameProperty(COL_NAME);
			        dataTaskTree.setNameProperty(COL_DESCRIPTION);
			        dataTaskTree.setIdField(COL_TASK_ID);  
			        dataTaskTree.setParentIdField(COL_PARENT_ID);  
			        dataTaskTree.setOpenProperty("isOpen");  
					
					List<TreeNode> treeRecords = new ArrayList<TreeNode>();
					for (TaskTimeDTO taskTimeDTO : listTask){
						TaskTreeNode taskTreeNode = new TaskTreeNode(taskTimeDTO);
						treeRecords.add(taskTreeNode);
					}
					dataTaskTree.setData(treeRecords.toArray(new TaskTreeNode[0]));
					tasksTree.setData(dataTaskTree);
				}
				@Override
				public void onFailure(Throwable caught) {
					SC.warn("Error al cargar las cargas Tareas");
				}
			});
		}
	}  

	@Override
	public void refreshPanel() {
		ProjectPanel.getInstance().addObserver(this);
		updateProjectSelected();

		CounterTimePanel.getInstance().addObserver(this);
	}


	@Override
	public void freePanel() {
		ProjectPanel.getInstance().removeObserver(this);

		CounterTimePanel.getInstance().removeObserver(this);
	}


	@Override
	public void updateTimesCounted() {
		refreshTimeGrid();
	}
	
	
	/**
	 * Nodo de tareas
	 * @author Nicolas
	 *
	 */
	 public static class TaskTreeNode extends TreeNode {  
	        public TaskTreeNode(TaskTimeDTO taskTimeDTO) {  
	            setAttribute(COL_TASK_ID, taskTimeDTO.getId());  
	            setAttribute(COL_PARENT_ID, taskTimeDTO.getTaskId());  
	            setAttribute(COL_NAME, taskTimeDTO.getName());  
	            setAttribute(COL_DESCRIPTION, taskTimeDTO.getDescription());
	            setAttribute("isOpen", false);  
	        }  
	    }  
}
