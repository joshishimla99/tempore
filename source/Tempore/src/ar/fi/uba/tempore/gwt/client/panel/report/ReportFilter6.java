package ar.fi.uba.tempore.gwt.client.panel.report;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ar.fi.uba.tempore.dto.UserDTO;
import ar.fi.uba.tempore.dto.reports.TaskTypesTimesDTO;
import ar.fi.uba.tempore.gwt.client.ReportServicesClient;
import ar.fi.uba.tempore.gwt.client.UserServicesClient;

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

public class ReportFilter6 extends VLayout {

	private final DynamicForm formFilter = new DynamicForm();
	private static final String DESDE_FIELD = "DesdeItem";
	private static final String USER_FIELD = "UserItem";
	private VLayout parent;

	public ReportFilter6(){}
	
	public ReportFilter6(VLayout parent){
		this.parent = parent;
		
		//Filtro Fecha
		final SelectItem user = new SelectItem(USER_FIELD, "Usuario");
		user.setRequired(true);
		UserServicesClient.Util.getInstance().fetch(new AsyncCallback<List<UserDTO>>() {
			@Override
			public void onSuccess(List<UserDTO> result) {
				LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();  
				for (UserDTO dto : result) {
					valueMap.put(dto.getId().toString(), dto.getName());
				}
				user.setValueMap(valueMap);	
			}
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error al cargar los Proyecto para Filtro de Reporte");
			}
		});

		final DateItem ini = new DateItem(DESDE_FIELD,"Semana");
		ini.setDisplayFormat(DateDisplayFormat.TOEUROPEANSHORTDATE);
		

		formFilter.setFields(user, ini);

		final HTMLFlow htmlFlow6 = new HTMLFlow("Reporte que grafica en columnas las horas cargadas por el usuario seleccionado en la semana filtrada.");  
	    htmlFlow6.setPadding(10);

		final ImgButton btnReporte6 = new ImgButton();
		btnReporte6.addClickHandler(onClickReport6);
		btnReporte6.setSrc("../images/report/bar.png");
		btnReporte6.setSize(64);
		btnReporte6.setMargin(10);
		btnReporte6.setAltText("Generar reporte");
	    
	    final HLayout hLayout6 = new HLayout();
	    hLayout6.setAlign(Alignment.CENTER);
	    hLayout6.addMember(btnReporte6);
	    
	    this.addMember(htmlFlow6);
	    this.addMember(formFilter);
	    this.addMember(hLayout6);
	}
	
	private ClickHandler onClickReport6 = new ClickHandler() {
		@Override
		public void onClick(ClickEvent event) {
			if (formFilter.validate()){
				final Date ini = (Date) formFilter.getValue(DESDE_FIELD);
				final Integer userId = new Integer(formFilter.getValue(USER_FIELD).toString());
				final String userName = formFilter.getItem(USER_FIELD).getDisplayValue().toString();
	
				Canvas[] oldGrafics = parent.getChildren();
				for (Canvas old : oldGrafics) {
					parent.removeChild(old);
				}
	
				draw(userId, userName, ini);
			}
		}
	};

	public void draw(Integer userId, final String userName, Date date) {
		ReportServicesClient.Util.getInstance().getUserTimesByWeek(userId, date, new AsyncCallback<Map<Integer,TaskTypesTimesDTO>>() {
			@Override
			public void onSuccess(final Map<Integer,TaskTypesTimesDTO> result) {
				final DateTimeFormat fmt = DateTimeFormat.getFormat("EEE dd-MMM");

				final GenericGrafic gg =  new GenericGrafic("Horas Cargadas por el  Usuario "+userName+" en la semana",
						"Dia de la Semana",
						"Horas Cargadas",
						GenericGrafic.COLUMNS) {
					@Override
					public DataTable createTable() {
						final DataTable data = DataTable.create();
						data.addColumn(ColumnType.STRING, "Dia de la Semana");
						data.addColumn(ColumnType.NUMBER, "Horas Cargadas");

						int i = 0;
						data.addRows(result.size());
						Set<Integer> keySet = result.keySet();
						for (Integer key : keySet) {
							data.setValue(i, 0, fmt.format(result.get(key).getDate()));
							data.setValue(i, 1, new Float(result.get(key).getHourCounted())/GenericGrafic.HORA);
							i++;
						}

						return data;
					}
				};
				parent.addChild(gg);
				gg.draw();
				show(); 
			}

			@Override
			public void onFailure(Throwable caught) {
				SC.warn("Error al buscar datos para reporte - Horas de Usuario en la semanao");
			}
		});		
	}
}
