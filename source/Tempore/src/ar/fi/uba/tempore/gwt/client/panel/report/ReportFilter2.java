package ar.fi.uba.tempore.gwt.client.panel.report;

import java.util.Date;
import java.util.List;

import ar.fi.uba.tempore.dto.reports.UsersTimesDTO;
import ar.fi.uba.tempore.gwt.client.ReportServicesClient;
import ar.fi.uba.tempore.gwt.client.panel.project.ProjectPanel;

import com.google.gwt.i18n.client.DateTimeFormat;
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

public class ReportFilter2 extends VLayout {

	private final DynamicForm formFilterDate = new DynamicForm();
	protected static final String DESDE_FIELD = "DesdeItem";
	protected static final String HASTA_FIELD = "HastaItem";
	private static final String PROJECT_FIELD = "ProyectoItem";
	private VLayout parent;

	public ReportFilter2(){
		
	}
	
	public ReportFilter2(VLayout parent){
		this.parent = parent;
		
		
		final SelectItem project = new SelectItem(PROJECT_FIELD, "Proyecto");
		
		
		//Filtro Fecha
		final DateItem ini = new DateItem(DESDE_FIELD,"Desde");
		ini.setDisplayFormat(DateDisplayFormat.TOEUROPEANSHORTDATE);
		ini.setValue(new Date(System.currentTimeMillis() + (3600000*24*30) ));
		final DateItem end = new DateItem(HASTA_FIELD, "Hasta");
		end.setDisplayFormat(DateDisplayFormat.TOEUROPEANSHORTDATE);

		formFilterDate.setFields(project, ini,end);



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
	    this.addMember(formFilterDate);
	    this.addMember(hLayout2);
	}
	
	private ClickHandler onClickReport2 = new ClickHandler() {
		@Override
		public void onClick(ClickEvent event) {
			final Date ini = (Date) formFilterDate.getValue(DESDE_FIELD);
			final Date end = (Date) formFilterDate.getValue(HASTA_FIELD);
			//TODO completar el Select para que se elijan bien
			final Integer projectId = ProjectPanel.getInstance().getSelected().getId();
			final String projectName = ProjectPanel.getInstance().getSelected().getName();
			
//			parent.removeMembers(parent.getMembers());
			Canvas[] childrens = parent.getChildren();
			for (Canvas old : childrens) {
				parent.removeChild(old);
			}
			draw(projectId, projectName, ini, end);			
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
