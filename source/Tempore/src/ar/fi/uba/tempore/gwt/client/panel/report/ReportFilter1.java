package ar.fi.uba.tempore.gwt.client.panel.report;

import java.util.Date;
import java.util.List;

import ar.fi.uba.tempore.dto.reports.ProjectsTimesDTO;
import ar.fi.uba.tempore.gwt.client.ReportServicesClient;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.AnimationEffect;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class ReportFilter1 extends VLayout {

	private final DynamicForm formFilterDate = new DynamicForm();
	private VLayout parent;
	protected static final String DESDE_FIELD = "DesdeItem";
	protected static final String HASTA_FIELD = "HastaItem";

	public ReportFilter1(){

	}

	public ReportFilter1(VLayout parent){
		this.parent = parent;

		//Filtro Fecha
		final DateItem ini = new DateItem(DESDE_FIELD,"Desde");
		ini.setDisplayFormat(DateDisplayFormat.TOEUROPEANSHORTDATE);
		ini.setValue(new Date(System.currentTimeMillis() + (3600000*24*30) ));
		final DateItem end = new DateItem(HASTA_FIELD, "Hasta");
		end.setDisplayFormat(DateDisplayFormat.TOEUROPEANSHORTDATE);

		formFilterDate.setFields(ini,end);


		final HTMLFlow htmlFlow1 = new HTMLFlow("Reporte encargado de graficar en forma de barras la cantidad de horas totales reportadas para cada uno de los proyectos existentes en el sistema.");  
		htmlFlow1.setPadding(10);

		final ImgButton btnReporte1 = new ImgButton();
		btnReporte1.addClickHandler(onClickReport1);
		btnReporte1.setBorder("1px Solid orange");
		btnReporte1.setMargin(10);
		btnReporte1.setSrc("../images/report/bar.png");
		btnReporte1.setSize(64);
		btnReporte1.setAltText("Generar reporte");

		final HLayout hLayout1 = new HLayout();
		hLayout1.setAlign(Alignment.CENTER);
		hLayout1.addMember(btnReporte1);


		this.animateShow(AnimationEffect.FADE);	
		this.setAnimateShowTime(2000);
		this.addMember(htmlFlow1);
		this.addMember(formFilterDate);
		this.addMember(hLayout1);
	}

	private ClickHandler onClickReport1 = new ClickHandler() {
		@Override
		public void onClick(ClickEvent event) {
			final Date ini = (Date) formFilterDate.getValue(DESDE_FIELD);
			final Date end = (Date) formFilterDate.getValue(HASTA_FIELD);

//			parent.removeMembers(parent.getMembers());

			Canvas[] childrens = parent.getChildren();
			for (Canvas old : childrens) {
				parent.removeChild(old);
			}
			
			
			draw(ini, end);
		}
	};

	public void draw(final Date ini, final Date end) {
		ReportServicesClient.Util.getInstance().getProjectsTimes(ini, end, new AsyncCallback<List<ProjectsTimesDTO>>() {
			@Override
			public void onSuccess(final List<ProjectsTimesDTO> result) {
				DateTimeFormat fmt = DateTimeFormat.getFormat("dd-MM-yyyy");

				final GenericGrafic gg =  new GenericGrafic("Horas Cargadas a Proyectos [ " + fmt.format(ini) + ", " + fmt.format(end) + "]",
						"Proyectos",
						"Horas Acumuladas [Hs]",
						GenericGrafic.COLUMNS) {
					@Override
					public DataTable createTable() {
						final DataTable data = DataTable.create();
						data.addColumn(ColumnType.STRING, "Proyectos");
						data.addColumn(ColumnType.NUMBER, "Horas Cargadas");

						int i = 0;
						data.addRows(result.size());
						for (ProjectsTimesDTO reg : result) {
							data.setValue(i, 0, (String)reg.getProjectName());
							data.setValue(i, 1, new Float(reg.getHourCounted())/GenericGrafic.HORA);
							i++;
						}
						return data;
					}
				};
				GWT.log("Hasta aca Llega");
				parent.addChild(gg);
				

				gg.draw();
//				show(); 
				GWT.log("Hasta aca no llega --111-");
			}
			@Override
			public void onFailure(Throwable caught) {
				SC.warn("Error al buscar datos para reporte - Proyectos/HorasCargadas");
			}
		});	
	}
}
