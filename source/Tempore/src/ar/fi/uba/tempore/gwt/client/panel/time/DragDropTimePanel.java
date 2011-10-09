package ar.fi.uba.tempore.gwt.client.panel.time;

import ar.fi.uba.tempore.gwt.client.panel.project.ProjectPanel;
import ar.fi.uba.temporeutils.observer.ProjectObserver;

import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.types.DragDataAction;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.DateChooser;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.DataChangedEvent;
import com.smartgwt.client.widgets.events.DataChangedHandler;
import com.smartgwt.client.widgets.events.DropHandler;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridEditorContext;
import com.smartgwt.client.widgets.grid.ListGridEditorCustomizer;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordDropEvent;
import com.smartgwt.client.widgets.grid.events.RecordDropHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeGridField;
import com.smartgwt.client.widgets.events.DragStopHandler;
import com.smartgwt.client.widgets.events.DragStopEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.events.DragMoveHandler;
import com.smartgwt.client.widgets.events.DragMoveEvent;

public class DragDropTimePanel extends Canvas implements ProjectObserver{

	final TaskTimeDataSource tasksDataSource;
	final TreeGrid tasksTree;
	final ListGrid hoursCountGrid;
	Integer taskDropped;

	public DragDropTimePanel(){
		super();
		taskDropped = 0;

		final VLayout vAllPanel = new VLayout();
		vAllPanel.setHeight100();
		vAllPanel.setWidth100();
		
	
		//TITULO
		final Label title = new Label("Asignaci&oacute;n de horas trabajadas a tareas");
		title.setWidth(200);
		title.setHeight(30);

		final HLayout hDateTasks = new HLayout();
		hDateTasks.setHeight("40%");
		hDateTasks.setWidth100();
		
		final HLayout hHoursCount = new HLayout();
		hHoursCount.setHeight("60%");
		hHoursCount.setWidth100();			

		tasksDataSource = new TaskTimeDataSource(); 
		HourCountDataSource dataSource = new HourCountDataSource(); 			

		hoursCountGrid = new ListGrid();

		hoursCountGrid.setDuplicateDragMessage("Esta tarea ya existe...");

		hoursCountGrid.addRecordDropHandler(new RecordDropHandler() {
			public void onRecordDrop(RecordDropEvent event) {
				ListGridRecord[] recs = event.getDropRecords();
				SC.say("MOVIDA" + taskDropped );
				recs[0].setAttribute(HourCountDataSource.COL_TASK_ID, taskDropped);
			}
		});
		

		hoursCountGrid.setHeight100();
		hoursCountGrid.setDataSource(dataSource);  
		hoursCountGrid.setShowAllRecords(true);  
		hoursCountGrid.setEmptyMessage("Arrastr&aacute las tareas ac&aacute");  
		hoursCountGrid.setCanReorderFields(true);  
		hoursCountGrid.setCanDragRecordsOut(true);  
		hoursCountGrid.setCanAcceptDroppedRecords(true);  
		hoursCountGrid.setDragDataAction(DragDataAction.MOVE);  
		hoursCountGrid.setCanEdit(true);
		hoursCountGrid.setCanRemoveRecords(true); 
		hoursCountGrid.setPreventDuplicates(false);		
		hoursCountGrid.setAutoSaveEdits(true);		

		ListGridField lfProject = new ListGridField(HourCountDataSource.COL_PROJECT_NAME);
		ListGridField lfName = new ListGridField(HourCountDataSource.COL_NAME);
		ListGridField lfDescription = new ListGridField(HourCountDataSource.COL_DESCRIPTION);
		ListGridField lfDate = new ListGridField(HourCountDataSource.COL_DATE);
		ListGridField lfHours = new ListGridField(HourCountDataSource.COL_HOURS);
		ListGridField lfComments = new ListGridField(HourCountDataSource.COL_COMMENTS);
		ListGridField lfProjectId = new ListGridField(HourCountDataSource.COL_PROJECT_ID);
//		lfProjectId.setHidden(true);

		

		lfProject.setCanEdit(false);
		lfName.setCanEdit(false);
		lfDescription.setCanEdit(false);

		hoursCountGrid.setFields(lfProject, lfName, lfDescription, lfDate, lfHours, lfComments, lfProjectId); 
		
		hoursCountGrid.setEditorCustomizer(new ListGridEditorCustomizer() {  
			public FormItem getEditor(ListGridEditorContext context) {  
				ListGridField field = context.getEditField();  
				if (field.getName().equals(HourCountDataSource.COL_DATE)) {  
					return new DateItem();
				}
				if (field.getName().equals(HourCountDataSource.COL_COMMENTS)) {  
					TextAreaItem textItem = new TextAreaItem();  
					textItem.setShowHint(true);  
					textItem.setShowHintInField(true);  
					textItem.setHint("comentarios...");  
					return textItem; 
				}
				return context.getDefaultProperties();}
		});
		
		hHoursCount.addMember(hoursCountGrid);

		final DateChooser dateChooser = new DateChooser();
		
		dateChooser.addDataChangedHandler(new DataChangedHandler() {
			public void onDataChanged(DataChangedEvent event) {
//				Integer anio;
//				Integer mes;
//				Integer dia;
				
				
//				anio = dateChooser.getData().getYear();
//				mes = dateChooser.getData().getMonth();
//				dia = dateChooser.getData().getDay();
//				
//				String fecha = new String(anio.toString() + mes.toString() + dia.toString());

//				+ dateChooser.getData().getMonth() + dateChooser.getData().getDate();
//				SC.say("Change Data: " + dateChooser.getData(). );		

//				hoursCountGrid.fetchData(new Criteria(HourCountDataSource.COL_DATE, "20111001"));   
				hoursCountGrid.fetchData(new Criteria(HourCountDataSource.COL_HOURS, "2" ));  
			}
		});
		
		dateChooser.setShowTodayButton(false);

		tasksTree = new TreeGrid();


		tasksTree.setShowDropIcons(true);
		tasksTree.setHeight100();
		tasksTree.setWidth("80%");
		tasksTree.setHeight("100%");

		

		
		tasksTree.addRecordClickHandler(new RecordClickHandler() {
			public void onRecordClick(RecordClickEvent event) {
				taskDropped = event.getRecord().getAttributeAsInt(TaskTimeDataSource.COL_ID);
				SC.say("TASK IID" + event.getRecord().getAttributeAsInt(TaskTimeDataSource.COL_ID) );
			}
		});
		

		
		tasksTree.setDataSource(tasksDataSource);
		TreeGridField tfName = new TreeGridField(TaskTimeDataSource.COL_NAME);
		TreeGridField tfDescription = new TreeGridField(TaskTimeDataSource.COL_DESCRIPTION);
		tasksTree.setShowAllRecords(true);  
		tasksTree.setCanReorderRecords(false);  
		tasksTree.setCanDragRecordsOut(true);  
		tasksTree.setCanAcceptDroppedRecords(false);  
		tasksTree.setDragDataAction(DragDataAction.COPY);
		tasksTree.setNodeIcon("../images/tasks.png");  
		tasksTree.setFolderIcon("../images/tasks.png");  
		tasksTree.setEmptyMessage("Seleccion&aacute un proyecto...");  
		tasksTree.setFields(tfName, tfDescription);  
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
		// TODO Auto-generated method stub
		//		SC.say("Actualizo Proyecto: " + ProjectPanel.getInstance().getSelected().getId().toString());		
		if (ProjectPanel.getInstance().getSelected() != null){
			tasksDataSource.setId(ProjectPanel.getInstance().getSelected().getId());
			tasksTree.fetchData();
			//		hoursCountGrid.fetchData();
			//		SC.say("ID= " + ProjectPanel.getInstance().getSelected().getId().toString());
			//		hoursCountGrid.fetchData(new Criteria(HourCountDataSource.COL_ID, "2"));
		}

	}  
	
	public void refreshSubTab(){
		ProjectPanel.getInstance().addObserver(this);
		updateProjectSelected();
	}

	public void freeSubTab() {
		// TODO Auto-generated method stub
		ProjectPanel.getInstance().removeObserver(this);
	}
}
