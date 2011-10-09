package ar.fi.uba.tempore.gwt.client.panel.time;

import ar.fi.uba.tempore.gwt.client.panel.configuration.HourCountDataSource;
import ar.fi.uba.tempore.gwt.client.panel.configuration.TaskTimeDataSource;
import ar.fi.uba.tempore.gwt.client.panel.project.ProjectPanel;
import ar.fi.uba.temporeutils.observer.ProjectObserver;

import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.types.DragDataAction;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.DateChooser;
import com.smartgwt.client.widgets.events.DataChangedEvent;
import com.smartgwt.client.widgets.events.DataChangedHandler;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridEditorContext;
import com.smartgwt.client.widgets.grid.ListGridEditorCustomizer;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeGridField;

public class DragDropTimePanel extends Canvas implements ProjectObserver{

	final TaskTimeDataSource tasksDataSource;
	final TreeGrid tasksTree;
	final ListGrid hoursCountGrid;

	public DragDropTimePanel(){
		super();

		ProjectPanel.getInstance().addObserver(this);

		final VLayout vAllPanel = new VLayout();
		vAllPanel.setHeight100();
		vAllPanel.setWidth100();

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

		//		gridHoursCount.addRecordDropHandler(new RecordDropHandler() {
		//			public void onRecordDrop(RecordDropEvent event) {
		//				ListGridRecord[] recs = event.getDropRecords();
		//				SC.say("" + recs[0].getAttribute(TaskTimeDataSource.COL_DESCRIPTION) );
		//			}
		//		});


		hoursCountGrid.setHeight100();
//		hoursCountGrid.setDataSource(dataSource);  
		hoursCountGrid.setShowAllRecords(true);  
		hoursCountGrid.setEmptyMessage("Arrastr&aacute las tareas ac&aacute");  
		hoursCountGrid.setCanReorderFields(true);  
		hoursCountGrid.setCanDragRecordsOut(true);  
		hoursCountGrid.setCanAcceptDroppedRecords(true);  
		hoursCountGrid.setDragDataAction(DragDataAction.MOVE);  
		hoursCountGrid.setCanEdit(true);
		hoursCountGrid.setCanRemoveRecords(true); 
		hoursCountGrid.setPreventDuplicates(true);		
		hoursCountGrid.setAutoSaveEdits(false);		

		ListGridField lfProject = new ListGridField(HourCountDataSource.COL_PROJECT_NAME);
		ListGridField lfName = new ListGridField(HourCountDataSource.COL_NAME);
		ListGridField lfDescription = new ListGridField(HourCountDataSource.COL_DESCRIPTION);
		ListGridField lfDate = new ListGridField(HourCountDataSource.COL_DATE);
		ListGridField lfHours = new ListGridField(HourCountDataSource.COL_HOURS);
		ListGridField lfComments = new ListGridField(HourCountDataSource.COL_COMMENTS);

		

		lfProject.setCanEdit(false);
		lfName.setCanEdit(false);
		lfDescription.setCanEdit(false);

		hoursCountGrid.setFields(lfProject, lfName, lfDescription, lfDate, lfHours, lfComments); 
		
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

				hoursCountGrid.fetchData(new Criteria(HourCountDataSource.COL_DATE, "20111001"));                 
			}
		});
		
		dateChooser.setShowTodayButton(false);

		tasksTree = new TreeGrid();
//		tasksTree.addMouseOverHandler(new MouseOverHandler() {
//			public void onMouseOver(MouseOverEvent event) {
//				tasksTree.getData().openAll();
//			}
//		});

		tasksTree.setShowDropIcons(true);
		tasksTree.setHeight100();
		tasksTree.setSize("80%", "100%");
		tasksTree.setDataSource(tasksDataSource);
		TreeGridField tfName = new TreeGridField(TaskTimeDataSource.COL_NAME);
		TreeGridField tfDescription = new TreeGridField(TaskTimeDataSource.COL_DESCRIPTION);
//		tfName.setName("Nombre de la Tarea");
//		tfDescription.setName("Descripci&oacute");
		tasksTree.setShowAllRecords(true);  
		tasksTree.setCanReorderRecords(false);  
		tasksTree.setCanDragRecordsOut(true);  
		tasksTree.setCanAcceptDroppedRecords(false);  
		tasksTree.setDragDataAction(DragDataAction.COPY);
		tasksTree.setNodeIcon("../images/tasks.png");  
		tasksTree.setFolderIcon("../images/tasks.png");  
		tasksTree.setEmptyMessage("Seleccion&aacute un proyecto...");  
		tasksTree.setFields(tfName, tfDescription);  
		

		
		hDateTasks.addMember(dateChooser);
		hDateTasks.addMember(tasksTree);
		vAllPanel.addMember(hDateTasks);
		vAllPanel.addMember(hHoursCount);

		this.addChild(vAllPanel);		
	}

	@Override
	public void destroy() {
		ProjectPanel.getInstance().removeObserver(this);
		super.destroy();
	}

	@Override
	public void updateProjectSelected() {
		// TODO Auto-generated method stub
		//		SC.say("Actualizo Proyecto: " + ProjectPanel.getInstance().getSelected().getId().toString());
		if (ProjectPanel.getInstance().getSelected() != null){
			tasksDataSource.setId(ProjectPanel.getInstance().getSelected().getId());
			tasksTree.fetchData();
			hoursCountGrid.fetchData();
		}
	}  
}
