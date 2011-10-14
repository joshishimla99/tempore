package ar.fi.uba.tempore.gwt.client.panel.time;

import ar.fi.uba.tempore.gwt.client.panel.project.ProjectPanel;
import ar.fi.uba.temporeutils.observer.ProjectObserver;

import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.types.DragDataAction;
import com.smartgwt.client.types.GroupStartOpen;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.DateChooser;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.DataChangedEvent;
import com.smartgwt.client.widgets.events.DataChangedHandler;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridEditorContext;
import com.smartgwt.client.widgets.grid.ListGridEditorCustomizer;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.events.RecordDropEvent;
import com.smartgwt.client.widgets.grid.events.RecordDropHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeGridField;

public class DragDropTimePanel extends Canvas implements ProjectObserver{

	public static final String COL_ID = "idTaskCol";
	public static final String COL_HOURS = "hoursCol";
	public static final String COL_COMMENTS = "commentsCol";
	public static final String COL_DATE = "dateCol";
	public static final String COL_NAME = "nameCol";
	
	public static final String COL_TASK_ID = "taskId";
	
	public static final String COL_DESCRIPTION = "descriptionCol";
	public static final String COL_PROJECT_NAME = "projectCol";
	public static final String COL_PROJECT_ID = "projectIdCol";	
	
	public static final String COL_PARENT_ID = "idParentCol";
	
	final TaskTimeDataSource tasksDataSource;
	final TreeGrid tasksTree;
	final ListGrid hoursCountGrid;
//	Integer taskDropped;

	public DragDropTimePanel(){
		super();
//		taskDropped = 0;

		final DateChooser dateChooser = new DateChooser();
		dateChooser.setHeight("190");
		
		final VLayout vAllPanel = new VLayout();
		vAllPanel.setHeight100();
		vAllPanel.setWidth100();
	
		//TITULO
		final Label title = new Label("Asignaci&oacute;n de horas trabajadas a tareas");
		title.setWidth(200);
		title.setHeight(30);

		final HLayout hDateTasks = new HLayout();
		hDateTasks.setHeight("197");
		hDateTasks.setWidth100();
		
		final HLayout hHoursCount = new HLayout();
		hHoursCount.setHeight("60%");
		hHoursCount.setWidth100();			

		tasksDataSource = new TaskTimeDataSource(); 
		HourCountDataSource dataSource = new HourCountDataSource(); 			

		hoursCountGrid = new ListGrid();
		hoursCountGrid.setAddDropValues(false);
		hoursCountGrid.setLoadingDataMessage("${loadingImage}&nbsp;Cargando...");
		hoursCountGrid.addRecordDropHandler(new RecordDropHandler() {
			public void onRecordDrop(RecordDropEvent event) {
				Integer horas = null;
				HourCountWindow win = new HourCountWindow();
				event.getDropRecords()[0].setAttribute(COL_DATE, dateChooser.getData());	
				win.showModal(hoursCountGrid, event.getDropRecords()[0]);
			}
		});

		hoursCountGrid.setDuplicateDragMessage("Esta tarea ya existe...");

		hoursCountGrid.setHeight100();
		hoursCountGrid.setDataSource(dataSource);  
		hoursCountGrid.setShowAllRecords(true);  
		hoursCountGrid.setEmptyMessage("Arrastr&aacute las tareas ac&aacute");  
		hoursCountGrid.setCanAcceptDroppedRecords(false);  
		hoursCountGrid.setDragDataAction(DragDataAction.MOVE);  
		hoursCountGrid.setCanEdit(true);
		hoursCountGrid.setCanRemoveRecords(true); 
//		hoursCountGrid.setPreventDuplicates(true);		
		hoursCountGrid.setAutoSaveEdits(true);		
		hoursCountGrid.setGroupByField(COL_PROJECT_NAME);  
		hoursCountGrid.setShowGridSummary(true);  
		hoursCountGrid.setShowGroupSummary(true);  	
		hoursCountGrid.setGroupStartOpen(GroupStartOpen.ALL);
		hoursCountGrid.setCanAcceptDrop(false);

		ListGridField lfProject = new ListGridField(COL_PROJECT_NAME);		
		
		ListGridField lfName = new ListGridField(COL_NAME);
		ListGridField lfDescription = new ListGridField(COL_DESCRIPTION);
		ListGridField lfDate = new ListGridField(COL_DATE);
		ListGridField lfHours = new ListGridField(COL_HOURS);
		ListGridField lfComments = new ListGridField(COL_COMMENTS);
		ListGridField lfProjectId = new ListGridField(COL_PROJECT_ID);
		ListGridField lfTaskId = new ListGridField(COL_TASK_ID);
		
		lfTaskId.setHidden(true);
		
		lfProject.setCanEdit(false);
		lfName.setCanEdit(false);
		lfDescription.setCanEdit(false);

		hoursCountGrid.setFields(lfTaskId, lfProject, lfName, lfDescription, lfDate, lfHours, lfComments, lfProjectId); 
		lfProjectId.setHidden(true);
		
		
		hoursCountGrid.setEditorCustomizer(new ListGridEditorCustomizer() {  
			public FormItem getEditor(ListGridEditorContext context) {  
				ListGridField field = context.getEditField();  
				if (field.getName().equals(COL_DATE)) {  
					return new DateItem();
				}
				if (field.getName().equals(COL_COMMENTS)) {  
					TextAreaItem textItem = new TextAreaItem();  
					textItem.setShowHint(true);  
					textItem.setShowHintInField(true);  
					textItem.setHint("comentarios...");  
					return textItem; 
				}
				return context.getDefaultProperties();}
		});
		
		hHoursCount.addMember(hoursCountGrid);
		
		dateChooser.addDataChangedHandler(new DataChangedHandler() {
			public void onDataChanged(DataChangedEvent event) {
				hoursCountGrid.fetchData(new Criteria(COL_DATE, "01/10/2011" ));  
			}
		});
		
		dateChooser.setShowTodayButton(false);

		tasksTree = new TreeGrid();
		tasksTree.setShowDropIcons(true);
		tasksTree.setHeight100();
		tasksTree.setWidth("80%");
		tasksTree.setHeight("100%");		
		tasksTree.setShowDragShadow(true);
		tasksTree.setAutoShowParent(true);
		tasksTree.setAutoFetchData(true);
		tasksTree.setAddDropValues(false);
		tasksTree.setLoadingDataMessage("${loadingImage}&nbsp;Cargando...");
		tasksTree.setDataSource(tasksDataSource);
		TreeGridField tfId = new TreeGridField();
		tfId.setHidden(true);
		TreeGridField tfName = new TreeGridField(COL_NAME);
		TreeGridField tfDescription = new TreeGridField(COL_DESCRIPTION);
		tasksTree.setShowAllRecords(true);  
		tasksTree.setCanReorderRecords(false);  
		tasksTree.setCanDragRecordsOut(true);  
		tasksTree.setCanAcceptDroppedRecords(false);  
		tasksTree.setDragDataAction(DragDataAction.COPY);
		tasksTree.setNodeIcon("../images/tasks.png");  
		tasksTree.setFolderIcon("../images/tasks.png");  
		tasksTree.setEmptyMessage("Seleccion&aacute un proyecto...");  
		tasksTree.setFields(tfId, tfName, tfDescription);  
		tasksTree.setShowConnectors(true);   
		hDateTasks.addMember(dateChooser);
		hDateTasks.addMember(tasksTree);
		//Agrego los Componentes al Panel
		vAllPanel.addMember(title);
		vAllPanel.addMember(hDateTasks);
		vAllPanel.addMember(hHoursCount);

		this.addChild(vAllPanel);		
	}


	@Override
	public void updateProjectSelected() {
		//		SC.say("Actualizo Proyecto: " + ProjectPanel.getInstance().getSelected().getId().toString());		
		if (ProjectPanel.getInstance().getSelected() != null){
			tasksDataSource.setId(ProjectPanel.getInstance().getSelected().getId());
			tasksTree.fetchData();
			hoursCountGrid.fetchData();
			//		SC.say("ID= " + ProjectPanel.getInstance().getSelected().getId().toString());
			//		hoursCountGrid.fetchData(new Criteria(COL_ID, "2"));
		}

	}  
	
	public void refreshSubTab(){
		ProjectPanel.getInstance().addObserver(this);
		updateProjectSelected();
	}

	public void freeSubTab() {
		ProjectPanel.getInstance().removeObserver(this);
	}
}
