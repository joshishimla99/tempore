package ar.fi.uba.tempore.gwt.client.panel.report;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ar.fi.uba.tempore.dto.ProjectDTO;
import ar.fi.uba.tempore.gwt.client.ProjectServicesClient;
import ar.fi.uba.tempore.gwt.client.ReportServicesClient;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class ReportFilter5 extends VLayout {

	private final DynamicForm formFilter = new DynamicForm();
//	protected static final String DESDE_FIELD = "DesdeItem";
//	protected static final String HASTA_FIELD = "HastaItem";
	private static final String PROJECT_FIELD = "ProyectoItem";
	private VLayout parent;

	public ReportFilter5(){
		
	}
	
	public ReportFilter5(VLayout parent){
		this.parent = parent;
		//Filtro Fecha
		final SelectItem project = new SelectItem(PROJECT_FIELD, "Proyecto");
		project.setRequired(true);
		ProjectServicesClient.Util.getInstance().fetch(new AsyncCallback<List<ProjectDTO>>() {
			@Override
			public void onSuccess(List<ProjectDTO> result) {
				LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();  
				for (ProjectDTO dto : result) {
					valueMap.put(dto.getId().toString(), dto.getName());
				}
				project.setValueMap(valueMap);	
			}
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error al cargar los Proyecto para Filtro de Reporte");
			}
		});

		
//		Date day = new Date();
//		CalendarUtil.addMonthsToDate(day, -1);
//		final DateItem ini = new DateItem(DESDE_FIELD,"Desde");
//		ini.setDisplayFormat(DateDisplayFormat.TOEUROPEANSHORTDATE);
//		ini.setValue(day);
//		final DateItem end = new DateItem(HASTA_FIELD, "Hasta");
//		end.setDisplayFormat(DateDisplayFormat.TOEUROPEANSHORTDATE);


		formFilter.setFields(project);//,ini,end);



		final HTMLFlow htmlFlow5 = new HTMLFlow("Reporte en gr&aacute;fico de &aacute;rea los usuarios involucrados para todos los proyectos activos.");  
	    htmlFlow5.setPadding(10);

		final ImgButton btnReporte5 = new ImgButton();
		btnReporte5.addClickHandler(onClickReport5);
		btnReporte5.setSrc("../images/report/area.png");
		btnReporte5.setSize(64);
		btnReporte5.setMargin(10);
		btnReporte5.setAltText("Generar reporte");
	    
	    final HLayout hLayout5 = new HLayout();
	    hLayout5.setAlign(Alignment.CENTER);
	    hLayout5.addMember(btnReporte5);
	    
	    this.addMember(htmlFlow5);
	    this.addMember(formFilter);
	    this.addMember(hLayout5);
	}
	
	private ClickHandler onClickReport5 = new ClickHandler() {
		@Override
		public void onClick(ClickEvent event) {
			if (formFilter.validate()){
//				final Date ini = (Date) formFilter.getValue(DESDE_FIELD);
//				final Date end = (Date) formFilter.getValue(HASTA_FIELD);
				final Integer projectId = new Integer(formFilter.getValue(PROJECT_FIELD).toString());
				final String projectName = formFilter.getItem(PROJECT_FIELD).getDisplayValue().toString();
				
				
				Canvas[] oldGrafics = parent.getChildren();
				for (Canvas old : oldGrafics) {
					parent.removeChild(old);
				}
	
				draw(projectId, projectName);
			}
		}
	};
	
	public void draw(final Integer projectId, final String projectName) {
		
		ReportServicesClient.Util.getInstance().getProjectTaskTypeByTime(projectId , null, null, new AsyncCallback<Map<String, Map<Integer, Long>>>() {
			@Override
			public void onSuccess(final Map<String, Map<Integer, Long>> taskMap) {
				final GenericGrafic gg =  new GenericGrafic("Horas a los Tipos de Tarea del Proyecto "+projectName,
						"Numero de Dia del Proyecto " + projectName,
						"Horas Cargadas",
						GenericGrafic.AREA) {
					@Override
					public DataTable createTable() {
						final DataTable data = DataTable.create();
						
						//COLUMNAS
						data.addColumn(ColumnType.NUMBER, "Numero de Dia del Proyecto");						
						
						if (taskMap.isEmpty()){
							data.addColumn(ColumnType.NUMBER, "No hay Info");
//							data.addRows(0);
						} else {
							//Agrego las columnas de los tipos de Tarea
							for (String taskType : taskMap.keySet()){
								data.addColumn(ColumnType.NUMBER, taskType);
							}
							
							
							//DATOS
							//Cantidad de registros a insertar
							Collection<Map<Integer, Long>> values = taskMap.values();
							data.addRows(values.iterator().next().size());
							
							//Columna de los Dias EJE X
							int columnIndex = 0; 
							String key = taskMap.keySet().iterator().next();
							Map<Integer, Long> map = taskMap.get(key);
							int rowIndex = 0;
							GWT.log("Dias");
							List<Integer> orderDays = orderList(map.keySet());
							for (Integer day : orderDays){
								data.setValue(rowIndex, columnIndex, day);
								GWT.log("["+rowIndex+","+columnIndex+","+day+"]");
								rowIndex++;
							}
							
							//Graficos del Eje Y para cada una de los tipos de tarea
							columnIndex = 1; 
							GWT.log("Cantidad de tipos de Tareas " + taskMap.keySet().size());
							for (String taskType : taskMap.keySet()){
								Map<Integer, Long> dayMap = taskMap.get(taskType);
								GWT.log("Columna: " + taskType);
								rowIndex = 0;
								//Set<Integer> daySet = dayMap.keySet();
								for (Integer day : orderDays) {
									Long hourCharged = dayMap.get(day);
									
									data.setValue(rowIndex, columnIndex, hourCharged/HORA);
									GWT.log("["+rowIndex+","+columnIndex+","+hourCharged/HORA+"]");
									rowIndex++;
								}
								columnIndex++;
							}
						}
						return data;
					}

					private List<Integer> orderList(Set<Integer> listDay) {
//						List<Integer> result = new ArrayList<Integer>();
						List<Integer> asList = Arrays.asList(listDay.toArray(new Integer[0]));
						
						Collections.sort(asList);
						return asList;
					}
				};
				
				parent.addChild(gg);

				gg.draw();
				show(); 
			}
			
			@Override
			public void onFailure(Throwable caught) {
				SC.warn("Error al buscar datos para reporte");
			}
		});
	}
}
