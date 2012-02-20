package ar.fi.uba.tempore.gwt.client.panel.report;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import ar.fi.uba.tempore.dto.ProjectDTO;
import ar.fi.uba.tempore.dto.reports.UsersTimesDTO;
import ar.fi.uba.tempore.gwt.client.ProjectServicesClient;
import ar.fi.uba.tempore.gwt.client.ReportServicesClient;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
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

public class ReportFilter2 extends VLayout {

	private final DynamicForm formFilter = new DynamicForm();
	protected static final String DESDE_FIELD = "DesdeItem";
	protected static final String HASTA_FIELD = "HastaItem";
	private static final String PROJECT_FIELD = "ProyectoItem";
	private VLayout parent;

	public ReportFilter2(){
		
	}
	
	public ReportFilter2(VLayout parent){
		this.parent = parent;
		
		
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
		
		//Filtro Fecha
		final DateItem ini = new DateItem(DESDE_FIELD,"Desde");
		ini.setRequired(true);
		ini.setDisplayFormat(DateDisplayFormat.TOEUROPEANSHORTDATE);
		ini.setValue(new Date(System.currentTimeMillis() + (3600000*24*30) ));
		final DateItem end = new DateItem(HASTA_FIELD, "Hasta");
		end.setRequired(true);
		end.setDisplayFormat(DateDisplayFormat.TOEUROPEANSHORTDATE);

		formFilter.setFields(project, ini,end);



	    final ImgButton btnReporte2 = new ImgButton();
		btnReporte2.addClickHandler(onClickReport2);
		btnReporte2.setSrc("../images/report/bar.png");
		btnReporte2.setSize(64);
		btnReporte2.setMargin(10);
		btnReporte2.setAltText("Generar reporte");
	    
	    final HTMLFlow htmlFlow2 = new HTMLFlow("Reporte que grafica por medio de Barras la cantidad de horas cargadas por cada usuario en el proyecto seleccionado.");  
	    htmlFlow2.setPadding(10);
	    
	    final HLayout hLayout2 = new HLayout();
	    hLayout2.setAlign(Alignment.CENTER);
	    hLayout2.addMember(btnReporte2);
	    
	    this.addMember(htmlFlow2);
	    this.addMember(formFilter);
	    this.addMember(hLayout2);
	}
	
	private ClickHandler onClickReport2 = new ClickHandler() {
		@Override
		public void onClick(ClickEvent event) {
			if (formFilter.validate()) {
				final Date ini = (Date) formFilter.getValue(DESDE_FIELD);
				final Date end = (Date) formFilter.getValue(HASTA_FIELD);
				final Integer projectId = new Integer(formFilter.getValue(PROJECT_FIELD).toString());
				final String projectName = formFilter.getItem(PROJECT_FIELD).getDisplayValue().toString();
				
				//Limpio el cuadro de grafica
				Canvas[] childrens = parent.getChildren();
				for (Canvas old : childrens) {
					parent.removeChild(old);
				}
				draw(projectId, projectName, ini, end);
			}
		}
	};
	
	public void draw(final Integer projectId, final String projectName, final Date ini, final Date end) {
		
		ReportServicesClient.Util.getInstance().getUsersTimes(projectId, ini, end, new AsyncCallback<List<UsersTimesDTO>>() {
			@Override
			public void onSuccess(final List<UsersTimesDTO> result) {
				 DateTimeFormat fmt = DateTimeFormat.getFormat("dd-MM-yyyy");
				
				final GenericGrafic gg =  new GenericGrafic("Horas Cargadas por Usuario en el Proyecto "+projectName+"  [" + fmt.format(ini) + ", " + fmt.format(end) + "]",
						"Usuarios del Proyecto " + projectName,
						"Horas Cargadas [Hs]",
						GenericGrafic.COLUMNS) {
					@Override
					public DataTable createTable() {
						final DataTable data = DataTable.create();
						data.addColumn(ColumnType.STRING, "Usuario");
						data.addColumn(ColumnType.NUMBER, "Horas Cargadas");
				
						int i = 0;
						data.addRows(result.size());
						for (UsersTimesDTO reg : result) {
							data.setValue(i, 0, reg.getUserName());
//							data.setValue(i, 1, reg.getHourCounted());
							data.setValue(i, 1, new Float(reg.getHourCounted())/GenericGrafic.HORA);
							i++;
						}
						return data;
					}
				};
//				gg.setGraficType(GenericGrafic.COLUMNS);
				parent.addChild(gg);

				gg.draw();
				show(); 
			}
			@Override
			public void onFailure(Throwable caught) {
				SC.warn("Error al buscar datos para reporte - Usuarios/HorasCargadas");
			}
		});	
	}

}
