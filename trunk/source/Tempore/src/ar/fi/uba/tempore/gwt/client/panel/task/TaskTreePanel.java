package ar.fi.uba.tempore.gwt.client.panel.task;

import java.util.List;

import ar.fi.uba.tempore.dto.TaskDTO;
import ar.fi.uba.tempore.gwt.client.TaskServicesClient;
import ar.fi.uba.tempore.gwt.client.panel.project.ProjectPanel;
import ar.fi.uba.tempore.gwt.client.panel.time.DragDropTimePanel;
import ar.fi.uba.tempore.gwt.client.panel.time.TaskTimeDataSource;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.types.ExpansionMode;
import com.smartgwt.client.util.SC;
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


	public TaskTreePanel(TaskTabPanel taskTabPanel){
		super();

		final TaskTabPanel taskPanel = taskTabPanel;

		this.setShowDropIcons(true);
		this.setHeight100();
		this.setTitle("Tempore");


		this.addRecordClickHandler(new RecordClickHandler() {
			public void onRecordClick(RecordClickEvent event) {
				//Tomo el record elegido y conformo un task DTO
				final TaskDTO taskParentDTO = new TaskDTO();
				taskParentDTO.setId(new Integer(event.getRecord().getAttribute(DragDropTimePanel.COL_TASK_ID)));
				taskParentDTO.setTaskId(new Integer(event.getRecord().getAttribute(DragDropTimePanel.COL_PARENT_ID)));
				taskParentDTO.setName(event.getRecord().getAttribute(DragDropTimePanel.COL_NAME));

				TaskServicesClient.Util.getInstance().getChildTask(ProjectPanel.getInstance().getSelected().getId(), taskParentDTO.getId(), new AsyncCallback<List<TaskDTO>>(){						
					@Override
					public void onSuccess(List<TaskDTO> result) {
						taskPanel.changeChildsTask(taskParentDTO, result);
					}
					@Override
					public void onFailure(Throwable caught) {
						SC.warn("Error al traer las tareas hijas de las Tarea elegida en el Arbol");
					}					
				});
			}
		});

		
		
		this.setExpansionMode(ExpansionMode.DETAIL_FIELD);
		this.setDataSource(TaskTimeDataSource.getInstance());
		this.setAutoFetchData(true);
		this.setShowAllRecords(true);  
		this.setCanReorderRecords(false);  
		this.setNodeIcon("../images/tasks.png");  
		this.setFolderIcon("../images/folder.png");  
		this.setEmptyMessage("No Existen tareas en este proyecto...");  
		this.setShowConnectors(false);

		
//		this.addDataArrivedHandler(new DataArrivedHandler() {
//			@Override
//			public void onDataArrived(DataArrivedEvent event) {
//				getData().openAll();
//			}
//		});
		final TreeGridField tfId = new TreeGridField(DragDropTimePanel.COL_TASK_ID);
		tfId.setHidden(true);
		final TreeGridField tfName = new TreeGridField(DragDropTimePanel.COL_NAME, "Tareas del Proyecto");

		this.setFields(tfId,tfName);  
	}

	public void updateTaskTree(){
		this.fetchData();
		this.redraw();
	}
}
