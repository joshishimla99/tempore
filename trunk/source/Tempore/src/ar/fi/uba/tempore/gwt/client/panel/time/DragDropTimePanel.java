package ar.fi.uba.tempore.gwt.client.panel.time;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import ar.fi.uba.tempore.dto.TimeFilterDTO;
import ar.fi.uba.tempore.gwt.client.login.SessionUser;
import ar.fi.uba.tempore.gwt.client.panel.TabsPanelContainer;
import ar.fi.uba.tempore.gwt.client.panel.counter.CounterTimePanel;
import ar.fi.uba.tempore.gwt.client.panel.project.ProjectPanel;
import ar.fi.uba.temporeutils.observer.ProjectObserver;
import ar.fi.uba.temporeutils.observer.TimeCounterObserver;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.DragDataAction;
import com.smartgwt.client.types.GroupStartOpen;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.TimeDisplayFormat;
import com.smartgwt.client.widgets.DateChooser;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.DataChangedEvent;
import com.smartgwt.client.widgets.events.DataChangedHandler;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TimeItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridEditorContext;
import com.smartgwt.client.widgets.grid.ListGridEditorCustomizer;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.SummaryFunction;
import com.smartgwt.client.widgets.grid.events.RecordDropEvent;
import com.smartgwt.client.widgets.grid.events.RecordDropHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeGridField;

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

	public DragDropTimePanel(){
		super();

		//TITULO
		final Label title = new Label("Asignaci&oacute;n de horas trabajadas a tareas");
		title.setWidth(200);
		title.setHeight(30);
		
		//FECHA PARA CARGA DE HORAS		
		dateChooser.setHeight("190");
		dateChooser.setShowTodayButton(false);
		dateChooser.addDataChangedHandler(new DataChangedHandler() {
			public void onDataChanged(DataChangedEvent event) {
				refreshTimeGrid();
			}
		});
		
			
		//LISTADO DE TAREAS
		tasksTree.setShowDropIcons(true);
		tasksTree.setHeight100();
		tasksTree.setWidth("80%");
		tasksTree.setHeight("100%");		
		tasksTree.setShowDragShadow(true);
		tasksTree.setAutoShowParent(true);
		tasksTree.setAutoFetchData(true);
		tasksTree.setAddDropValues(false);
		tasksTree.setLoadingDataMessage("${loadingImage}&nbsp;Cargando...");
		tasksTree.setDataSource(TaskTimeDataSource.getInstance());
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

		final ListGridField lfProject = new ListGridField(COL_PROJECT_NAME);		
		lfProject.setCanEdit(false);
		lfProject.setHidden(true);
		final ListGridField lfName = new ListGridField(COL_NAME);
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
		final ListGridField lfDescription = new ListGridField(COL_DESCRIPTION);
		lfDescription.setCanEdit(false);
		final ListGridField lfDate = new ListGridField(COL_DATE);
		
		final ListGridField lfHours = new ListGridField(COL_HOURS);
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
//		lfHours.setCellFormatter(new CellFormatter() {  
//            public String format(Object value, ListGridRecord record, int rowNum, int colNum) {  
//                if (value == null) return null;  
//                try {  
//                    NumberFormat nf = NumberFormat.getFormat("#,##0.0");  
//                    return  nf.format(((Number) value).doubleValue()) + " hs.";  
//                } catch (Exception e) {  
//                    return value.toString();  
//                }  
//            }
//        });
		
		
		final ListGridField lfComments = new ListGridField(COL_COMMENTS);
		final ListGridField lfProjectId = new ListGridField(COL_PROJECT_ID);
		lfProjectId.setHidden(true);
		final ListGridField lfTaskId = new ListGridField(COL_TASK_ID);		
		lfTaskId.setHidden(true);

		hoursCountGrid.setFields(lfTaskId, lfProject, lfName, lfDescription, lfDate, lfHours, lfComments, lfProjectId); 
		
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
		
		//LAYOUTs
		
		final HLayout hHoursCount = new HLayout();
		hHoursCount.setHeight("60%");
		hHoursCount.setWidth100();	
		hHoursCount.addMember(hoursCountGrid);
		
		final HLayout hDateTasks = new HLayout();
		hDateTasks.setHeight("197");
		hDateTasks.setWidth100();
		hDateTasks.addMember(dateChooser);
		hDateTasks.addMember(tasksTree);
		
		//Agrego los Componentes al Panel
		final VLayout vAllPanel = new VLayout();
		vAllPanel.setHeight100();
		vAllPanel.setWidth100();
		vAllPanel.addMember(title);
		vAllPanel.addMember(hDateTasks);
		vAllPanel.addMember(hHoursCount);

		//refresco panel de Horas
		refreshTimeGrid();
		this.addChild(vAllPanel);		
	}


	/**
	 * Va ha buscar la informacion de la grilla de las horas con los filtros elegidos en la pantalla
	 * Los filtros son la fecha y el usuaior logueado
	 */
	private void refreshTimeGrid() {
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
			tasksTree.fetchData();
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
}
