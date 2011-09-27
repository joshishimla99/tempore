package ar.fi.uba.tempore.gwt.client.panel.time;

import ar.fi.uba.tempore.gwt.client.panel.configuration.HourCountDataSource;
import ar.fi.uba.tempore.gwt.client.panel.configuration.TaskTimeDataSource;
import ar.fi.uba.tempore.gwt.client.panel.project.ProjectPanel;
import ar.fi.uba.temporeutils.observer.ProjectObserver;

import com.google.gwt.user.client.Window;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DragDataAction;
import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.types.RowEndEditAction;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.DateChooser;
import com.smartgwt.client.widgets.EdgedCanvas;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.DropEvent;
import com.smartgwt.client.widgets.events.DropHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tree.TreeGrid;

public class DragDropTimePanel extends Canvas implements ProjectObserver{

	final TaskTimeDataSource dataSource2;
	final TreeGrid columnTree;
	
	public DragDropTimePanel(){
		super();
		
		ProjectPanel.getInstance().addObserver(this);

		final VLayout vLayout = new VLayout();
		vLayout.setHeight100();
		vLayout.setWidth100();

		final HLayout hDateTasks = new HLayout();
		hDateTasks.setHeight("50%");
		hDateTasks.setWidth100();
		 
		
		columnTree = new TreeGrid();
		columnTree.setShowDropIcons(true);
//		treeTasks.setHeight("50%");
		columnTree.setWidth100();
		
		dataSource2 = new TaskTimeDataSource(); 		
		columnTree.setDataSource(dataSource2);
		columnTree.setAutoFetchData(true);
		ListGridField fields = new ListGridField(TaskTimeDataSource.COL_NAME);
		columnTree.setFields(fields);
		columnTree.setCanAcceptDrop(true);
		columnTree.setCanDrag(true);
		columnTree.setCanDrop(false); 
		columnTree.setDragDataAction(DragDataAction.MOVE);

		HourCountDataSource dataSource = new HourCountDataSource(); 		

		final DateChooser dateChooser = new DateChooser();

		final ListGrid gridHoursCount = new ListGrid();
		gridHoursCount.setHeight100();
		gridHoursCount.setDataSource(dataSource);
		gridHoursCount.setCanEdit(true);
		gridHoursCount.setEditEvent(ListGridEditEvent.CLICK);		
		gridHoursCount.setListEndEditAction(RowEndEditAction.NEXT);
		gridHoursCount.setAutoSaveEdits(true);
		gridHoursCount.setShowAllRecords(true);  
		gridHoursCount.setEmptyMessage("Move las tareas ACA!");  
		gridHoursCount.setCanReorderFields(true);  
		gridHoursCount.setCanDragRecordsOut(true);  
		gridHoursCount.setCanAcceptDroppedRecords(true);  
		gridHoursCount.setDragDataAction(DragDataAction.MOVE);

		gridHoursCount.addDropHandler(new DropHandler() {
			public void onDrop(DropEvent event) {
				Window.alert("detecto el drop");}
		});

		final EdgedCanvas edgedCanvas = new EdgedCanvas();
		edgedCanvas.setWidth("10%");

		final Label lblNewLabel = new Label("14");
		lblNewLabel.setBackgroundColor("#D6E8FF");
		lblNewLabel.setAutoFit(true);
		edgedCanvas.addChild(lblNewLabel);
		lblNewLabel.setAlign(Alignment.CENTER);
		lblNewLabel.setTitle("horas");
		
		hDateTasks.addMember(dateChooser);
		hDateTasks.addMember(gridHoursCount);
		hDateTasks.addMember(edgedCanvas);
		
		vLayout.addMember(columnTree);		
		vLayout.addMember(hDateTasks);
		
		this.addChild(vLayout);		
	}

	@Override
	public void destroy() {
		ProjectPanel.getInstance().removeObserver(this);
		super.destroy();
	}
	
	@Override
	public void updateProjectSelected() {
		TaskTimeDataSource dataSource3 = new TaskTimeDataSource(); 
		// TODO Auto-generated method stub
//		SC.say("Actualizo Proyecto: " + ProjectPanel.getInstance().getSelected().getId().toString());		
		dataSource2.setId(ProjectPanel.getInstance().getSelected().getId());
		columnTree.fetchData();
	}  
}
