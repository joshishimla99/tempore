package ar.fi.uba.tempore.gwt.client.panel.task;

import ar.fi.uba.tempore.dto.ProjectDTO;
import ar.fi.uba.tempore.gwt.client.panel.time.TaskTimeDataSource;

import com.smartgwt.client.types.DragDataAction;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeGridField;

/**
 * Arbol con las tareas del proyecto
 * @author Ludmila
 *
 */
public class TaskTreePanel extends TreeGrid{
	final TaskTimeDataSource tasksDataSource;
	
	public TaskTreePanel(){
		super();


		this.setShowDropIcons(true);
		this.setHeight100();
		this.setWidth("100%");
		this.setHeight("100%");

		tasksDataSource = TaskTimeDataSource.getInstance(); 

		
		this.addRecordClickHandler(new RecordClickHandler() {
			public void onRecordClick(RecordClickEvent event) {
			}
		});
		

		
		this.setDataSource(tasksDataSource);
//		TreeGridField tfName = new TreeGridField(TaskTimeDataSource.COL_NAME);
//		TreeGridField tfDescription = new TreeGridField(TaskTimeDataSource.COL_DESCRIPTION);
		this.setShowAllRecords(true);  
		this.setCanReorderRecords(false);  
		this.setCanDragRecordsOut(true);  
		this.setCanAcceptDroppedRecords(false);  
		this.setDragDataAction(DragDataAction.COPY);
		this.setNodeIcon("../images/tasks.png");  
		this.setFolderIcon("../images/tasks.png");  
		this.setEmptyMessage("Seleccion&aacute un proyecto...");  
//		this.setFields(tfName);  
		this.setShowConnectors(true);
	}
	
	public void updateTaskTree(ProjectDTO selectedProject){
		this.fetchData();
		this.redraw();
	}

}
