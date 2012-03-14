package ar.fi.uba.tempore.gwt.client.panel.report;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import ar.fi.uba.tempore.dto.ProjectDTO;
import ar.fi.uba.tempore.dto.reports.TasksTimesDTO;
import ar.fi.uba.tempore.gwt.client.ProjectServicesClient;
import ar.fi.uba.tempore.gwt.client.ReportServicesClient;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;
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

public class ReportFilter3 extends VLayout {

	private final DynamicForm formFilter = new DynamicForm();
	private static final String DESDE_FIELD = "DesdeItem";
	private static final String HASTA_FIELD = "HastaItem";
	private static final String PROJECT_FIELD = "ProyectoItem";

	private VLayout parent;

	public ReportFilter3(){
		
	}
	
	public ReportFilter3(VLayout parent){
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

		
		Date day = new Date();
		CalendarUtil.addMonthsToDate(day, -1);
		final DateItem ini = new DateItem(DESDE_FIELD,"Desde");
		ini.setDisplayFormat(DateDisplayFormat.TOEUROPEANSHORTDATE);
		ini.setValue(day);
		final DateItem end = new DateItem(HASTA_FIELD, "Hasta");
		end.setDisplayFormat(DateDisplayFormat.TOEUROPEANSHORTDATE);


		formFilter.setFields(project, ini,end);

	    final HTMLFlow htmlFlow3 = new HTMLFlow("Reporte de tipo &aacute;rea que muestra las horas registradas a cada una de las Tareas de primer nivel del proyecto seleccionado.");  
	    htmlFlow3.setPadding(10);
	
		final ImgButton btnReporte3 = new ImgButton();
		btnReporte3.addClickHandler(onClickReport3);
		btnReporte3.setSrc("../images/report/area.png");
		btnReporte3.setSize(64);
		btnReporte3.setMargin(10);
		btnReporte3.setAltText("Generar reporte");
		
	    final HLayout hLayout3 = new HLayout();
	    hLayout3.setAlign(Alignment.CENTER);
	    hLayout3.addMember(btnReporte3);
	    
	    this.addMember(htmlFlow3);
	    this.addMember(formFilter);
	    this.addMember(hLayout3);
	}
	
	private ClickHandler onClickReport3 = new ClickHandler() {
		@Override
		public void onClick(ClickEvent event) {
			if (formFilter.validate()){
				final Date ini = (Date) formFilter.getValue(DESDE_FIELD);
				final Date end = (Date) formFilter.getValue(HASTA_FIELD);
				final Integer projectId = new Integer(formFilter.getValue(PROJECT_FIELD).toString());
				final String projectName = formFilter.getItem(PROJECT_FIELD).getDisplayValue().toString();
				
	//			limpio el recuadro del grafico
				Canvas[] oldGrafics = parent.getChildren();
				for (Canvas old : oldGrafics) {
					parent.removeChild(old);
				}
	
				draw(projectId, projectName, ini, end);						
			}
		}
	};
	
	public void draw(final Integer projectId, final String projectName, final Date ini, final Date end) {
		 
		ReportServicesClient.Util.getInstance().getPrimaryTaskTimes(projectId, ini, end,new AsyncCallback<List<TasksTimesDTO>>(){
			@Override
			public void onSuccess(final List<TasksTimesDTO> result) {
				DateTimeFormat fmt = DateTimeFormat.getFormat("dd-MM-yyyy");
				
				final GenericGrafic gg =  new GenericGrafic("Horas cargadas al Proyecto "+projectName+" [" + fmt.format(ini) + ", " + fmt.format(end) + "]",
						"Tareas Primarias del Proyecto " + projectName,
						"Horas Cargadas [Hs]",
						GenericGrafic.AREA) {
					@Override
					public DataTable createTable() {
						final DataTable data = DataTable.create();
						data.addColumn(ColumnType.STRING, "Tareas");
						data.addColumn(ColumnType.NUMBER, "Horas Cargadas");
				
						int i = 0;
						data.addRows(result.size());
						Collections.sort(result);
						for (TasksTimesDTO reg : result) {
							data.setValue(i, 0, reg.getTaskName());
							data.setValue(i, 1, new Float(reg.getHourCounted())/GenericGrafic.HORA);
							i++;
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
				SC.warn("Error al buscar datos para reporte - Horas a Tareas por proyecto");
			}
		});
	}

}
