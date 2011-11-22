package ar.fi.uba.tempore.gwt.client.panel.report;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import ar.fi.uba.tempore.dto.reports.ProjectsTimesDTO;
import ar.fi.uba.tempore.gwt.client.ReportServicesClient;
import ar.fi.uba.tempore.gwt.client.panel.TabsPanelContainer;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;
import com.smartgwt.client.types.AnimationEffect;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.events.CloseClientEvent;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;

public class ReportTabPanel extends TabsPanelContainer{

	protected static final String DESDE_FIELD = "DesdeItem";
	protected static final String HASTA_FIELD = "HastaItem";
	private VLayout vLayout = new VLayout();
	private VLayout reportLayout = new VLayout();
//	private GenericGrafic gg = null;
	private final DynamicForm form = new DynamicForm();
	
	public ReportTabPanel() {
		super();

		vLayout.setHeight100();
		vLayout.setWidth100();
		
		
		final SelectItem selState = new SelectItem("Estado", "Tipo de Grafico");
		LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>(4);  
		valueMap.put("0", "Torta");
		valueMap.put("1", "Linea");
		valueMap.put("2", "Areas");
		valueMap.put("3", "Columnas");
		selState.setValueMap(valueMap);
		
		final DateItem ini = new DateItem(DESDE_FIELD,"Desde");
		ini.setValue(new Date(System.currentTimeMillis() + (3600000*24*30) ));
		final DateItem end = new DateItem(HASTA_FIELD, "Hasta");
		
		final ButtonItem btnReporte1 = new ButtonItem("Proyectos-Horas");
		btnReporte1.addClickHandler(onClickReport1);
		
		final ButtonItem btnReporte2 = new ButtonItem("Usuarios-Horas");
		btnReporte2.addClickHandler(onClickReport2);
		
		final ButtonItem btnReporte3 = new ButtonItem("Tareas-Horas");
		btnReporte3.addClickHandler(onClickReport3);
		
		final ButtonItem btnReporte4 = new ButtonItem("Usuario-Proyecto");
		btnReporte4.addClickHandler(onClickReport4);
		
		form.setFields(
					ini,
					end,
					//selState,
					btnReporte1,
					btnReporte2,
					btnReporte3,
					btnReporte4);
		
		vLayout.addMember(form);
		vLayout.addMember(reportLayout);
		
		this.addChild(vLayout);
	}

	private ClickHandler onClickReport1 = new ClickHandler() {
		@Override
		public void onClick(ClickEvent event) {
			final Date ini = (Date) form.getValue(DESDE_FIELD);
			final Date end = (Date) form.getValue(HASTA_FIELD);
			
			new Report1().draw(ini, end);			
		}
	};

	private ClickHandler onClickReport2 = new ClickHandler() {
		@Override
		public void onClick(ClickEvent event) {
			final Date ini = (Date) form.getValue(DESDE_FIELD);
			final Date end = (Date) form.getValue(HASTA_FIELD);
			
			new Report2().draw(ini, end);			
		}
	};

	private ClickHandler onClickReport3 = new ClickHandler() {
		@Override
		public void onClick(ClickEvent event) {
			final Date ini = (Date) form.getValue(DESDE_FIELD);
			final Date end = (Date) form.getValue(HASTA_FIELD);
			
			new Report3().draw(ini, end);			
		}
	};
	
	private ClickHandler onClickReport4 = new ClickHandler() {
		@Override
		public void onClick(ClickEvent event) {
			final Date ini = (Date) form.getValue(DESDE_FIELD);
			final Date end = (Date) form.getValue(HASTA_FIELD);
			
			new Report4().draw(ini, end);			
		}
	};
	
	
	
	
	
	
	
	@Override
	public void refreshPanel() {
		//NADA
	}

	@Override
	public void freePanel() {
		//NADA
	}
}
