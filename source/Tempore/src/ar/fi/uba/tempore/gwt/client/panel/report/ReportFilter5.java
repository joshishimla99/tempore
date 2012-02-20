package ar.fi.uba.tempore.gwt.client.panel.report;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import ar.fi.uba.tempore.gwt.client.ReportServicesClient;
import ar.fi.uba.tempore.gwt.client.panel.project.ProjectPanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class ReportFilter5 extends VLayout {

	private final DynamicForm formFilterDate = new DynamicForm();
	protected static final String DESDE_FIELD = "DesdeItem";
	protected static final String HASTA_FIELD = "HastaItem";
	private static final String PROJECT_FIELD = "ProyectoItem";
	private VLayout parent;

	public ReportFilter5(){
		
	}
	
	public ReportFilter5(VLayout parent){
		this.parent = parent;
		//Filtro Fecha
		final SelectItem project = new SelectItem(PROJECT_FIELD, "Proyecto");
		
		final DateItem ini = new DateItem(DESDE_FIELD,"Desde");
		ini.setDisplayFormat(DateDisplayFormat.TOEUROPEANSHORTDATE);
		ini.setValue(new Date(System.currentTimeMillis() + (3600000*24*30) ));
		final DateItem end = new DateItem(HASTA_FIELD, "Hasta");
		end.setDisplayFormat(DateDisplayFormat.TOEUROPEANSHORTDATE);

		formFilterDate.setFields(project,ini,end);



		final HTMLFlow htmlFlow5 = new HTMLFlow("Reporte que grafica en columnas los usuario involucrados para todos los proyectos abiertos.");  
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
	    this.addMember(formFilterDate);
	    this.addMember(hLayout5);
	}
	
	private ClickHandler onClickReport5 = new ClickHandler() {
		@Override
		public void onClick(ClickEvent event) {
			final Date ini = (Date) formFilterDate.getValue(DESDE_FIELD);
			final Date end = (Date) formFilterDate.getValue(HASTA_FIELD);
			final Integer projectId = ProjectPanel.getInstance().getSelected().getId();
			final String projectName = ProjectPanel.getInstance().getSelected().getName();
			
			
			Canvas[] oldGrafics = parent.getChildren();
			for (Canvas old : oldGrafics) {
				parent.removeChild(old);
			}

			draw(projectId, projectName, ini, end);				
		}
	};
	
	public void draw(final Integer projectId, final String projectName, final Date ini, final Date end) {
		
		ReportServicesClient.Util.getInstance().getProjectTaskTypeByTime(projectId , ini, end, new AsyncCallback<Map<String, Map<Integer, Long>>>() {
			@Override
			public void onSuccess(final Map<String, Map<Integer, Long>> taskMap) {
				final GenericGrafic gg =  new GenericGrafic("Horas Cargadas a los Tipos de Tarea del Proyecto "+ProjectPanel.getInstance().getSelected().getName(),
						"Numero de Dia del Proyecto " + projectName,
						"Horas Cargadas",
						GenericGrafic.AREA) {
					@Override
					public DataTable createTable() {
						final DataTable data = DataTable.create();
						data.addColumn(ColumnType.NUMBER, "Numero de Dia del Proyecto");						
						for (String taskType : taskMap.keySet()){
							data.addColumn(ColumnType.NUMBER, taskType);
//							GWT.log("Columna: " + taskType);
						}
						
						Collection<Map<Integer, Long>> values = taskMap.values();
						data.addRows(values.iterator().next().size());
						
						//Columna de los Dias EJE X
						int columnIndex = 0; 
						String key = taskMap.keySet().iterator().next();
						Map<Integer, Long> map = taskMap.get(key);
						int rowIndex = 0;
						for (Integer day : map.keySet()){
							data.setValue(rowIndex, columnIndex, day);
//							GWT.log("["+rowIndex+","+columnIndex+","+day+"]");
							rowIndex++;
						}
						
						//Graficos del Eje Y para cada una de los tipos de tarea
						columnIndex = 1; 
						GWT.log("Cantidad de tipos de Tareas " + taskMap.keySet().size());
						for (String taskType : taskMap.keySet()){
							Map<Integer, Long> dayMap = taskMap.get(taskType);
							GWT.log("Columna: " + taskType);
							rowIndex = 0;
							Set<Integer> daySet = dayMap.keySet();
							for (Integer day : daySet) {
								Long hourCharged = dayMap.get(day);
								
								data.setValue(rowIndex, columnIndex, hourCharged/HORA);
								GWT.log("["+rowIndex+","+columnIndex+","+hourCharged/HORA+"]");
								rowIndex++;
							}
							columnIndex++;
						}

						return data;
					}
				};
//				gg.setGraficType(GenericGrafic.AREA);
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
