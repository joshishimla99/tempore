package ar.fi.uba.tempore.gwt.client.panel.report;

import java.util.Date;
import java.util.List;

import ar.fi.uba.tempore.dto.reports.ProjectsTimesDTO;
import ar.fi.uba.tempore.gwt.client.ReportServicesClient;
import ar.fi.uba.tempore.gwt.client.login.SessionUser;

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

public class ReportFilter4 extends VLayout {

	private final DynamicForm formFilterDate = new DynamicForm();
	protected static final String DESDE_FIELD = "DesdeItem";
	protected static final String HASTA_FIELD = "HastaItem";
	private static final String USER_FIELD = "UserItem";

	private VLayout parent;

	public ReportFilter4(){

	}

	public ReportFilter4(VLayout parent){
		this.parent = parent;

		//Filtro Fecha
		final SelectItem user = new SelectItem(USER_FIELD, "Usuario");

		final DateItem ini = new DateItem(DESDE_FIELD,"Desde");
		ini.setDisplayFormat(DateDisplayFormat.TOEUROPEANSHORTDATE);
		ini.setValue(new Date(System.currentTimeMillis() + (3600000*24*30) ));
		final DateItem end = new DateItem(HASTA_FIELD, "Hasta");
		end.setDisplayFormat(DateDisplayFormat.TOEUROPEANSHORTDATE);

		formFilterDate.setFields(user,ini,end);

		final HTMLFlow htmlFlow4 = new HTMLFlow("Reporte que grafica en Torta el involucramiento en los Proyectos del Usuario seleccionado.");  
		htmlFlow4.setPadding(10);

		final ImgButton btnReporte4 = new ImgButton();
		btnReporte4.addClickHandler(onClickReport4);
		btnReporte4.setSrc("../images/report/pie.png");
		btnReporte4.setSize(64);
		btnReporte4.setMargin(10);
		btnReporte4.setAltText("Generar reporte");

		final HLayout hLayout4 = new HLayout();
		hLayout4.setAlign(Alignment.CENTER);
		hLayout4.addMember(btnReporte4);

		this.addMember(htmlFlow4);
		this.addMember(formFilterDate);
		this.addMember(hLayout4);
	}

	private ClickHandler onClickReport4 = new ClickHandler() {
		@Override
		public void onClick(ClickEvent event) {
			final Date ini = (Date) formFilterDate.getValue(DESDE_FIELD);
			final Date end = (Date) formFilterDate.getValue(HASTA_FIELD);
			final Integer userId = SessionUser.getInstance().getUser().getId();
			final String userName = SessionUser.getInstance().getUser().getUserName();

//			 parent.removeMembers(parent.getMembers());
			Canvas[] oldGrafics = parent.getChildren();
			for (Canvas old : oldGrafics) {
				parent.removeChild(old);
			}

			draw(userId, userName, ini, end);						
		}
	};

	public void draw(final Integer userId, final String userName, final Date ini, final Date end) {
		ReportServicesClient.Util.getInstance().getUserActivity(userId, ini, end, new AsyncCallback<List<ProjectsTimesDTO>>() {
			@Override
			public void onSuccess(final List<ProjectsTimesDTO> result) {
				DateTimeFormat fmt = DateTimeFormat.getFormat("dd-MMMM-yyyy");

				final GenericGrafic gg =  new GenericGrafic("Asignacion en Proyectos del Usuario "+userName+" [" + fmt.format(ini) + ", " + fmt.format(end) + "]",
						"",
						"",
						GenericGrafic.PIE) {
					@Override
					public DataTable createTable() {
						final DataTable data = DataTable.create();
						data.addColumn(ColumnType.STRING, "Proyecto");
						data.addColumn(ColumnType.NUMBER, "Horas Cargadas");

						int i = 0;
						data.addRows(result.size());
						for (ProjectsTimesDTO reg : result) {
							data.setValue(i, 0, reg.getProjectName());
							data.setValue(i, 1, new Float(reg.getHourCounted())/GenericGrafic.HORA);
							i++;
						}
						return data;
					}
				};
				//				gg.setGraficType(GenericGrafic.PIE);
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
