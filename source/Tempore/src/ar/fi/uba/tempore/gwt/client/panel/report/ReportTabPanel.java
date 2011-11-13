package ar.fi.uba.tempore.gwt.client.panel.report;

import java.util.LinkedHashMap;

import ar.fi.uba.tempore.gwt.client.panel.TabsPanelContainer;

import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.Gauge;
import com.google.gwt.visualization.client.visualizations.corechart.AreaChart;
import com.google.gwt.visualization.client.visualizations.corechart.ColumnChart;
import com.google.gwt.visualization.client.visualizations.corechart.CoreChart.Type;
import com.google.gwt.visualization.client.visualizations.corechart.LineChart;
import com.google.gwt.visualization.client.visualizations.corechart.Options;
import com.google.gwt.visualization.client.visualizations.corechart.PieChart;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.events.ResizedEvent;
import com.smartgwt.client.widgets.events.ResizedHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.layout.VLayout;

public class ReportTabPanel extends TabsPanelContainer{

	private VLayout vLayout = new VLayout();

	public ReportTabPanel() {
		super();

		vLayout.setHeight100();
		vLayout.setWidth100();

		final GenericGrafic gg = new GenericGrafic(GenericGrafic.AREA) {
			@Override
			public DataTable createTable() {
				DataTable data = DataTable.create();
				data.addColumn(ColumnType.STRING, "Tareas");
				data.addColumn(ColumnType.NUMBER, "Horas por dias");
				data.addRows(6);
				data.setValue(0, 0, "Trabajar");
				data.setValue(0, 1, 9);
				data.setValue(1, 0, "Dormir");
				data.setValue(1, 1, 7);
				data.setValue(2, 0, "Comer");
				data.setValue(2, 1, 2);
				data.setValue(3, 0, "Deportes");
				data.setValue(3, 1, 1);
				data.setValue(4, 0, "Viajar");
				data.setValue(4, 1, 2);
				data.setValue(5, 0, "Estudiar");
				data.setValue(5, 1, 3);
				return data;
			}
		};
		
		
		final DynamicForm form = new DynamicForm();
		final SelectItem selState = new SelectItem("Estado", "Tipo de Grafico");
		LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>(5);  
		valueMap.put("0", "Torta");
		valueMap.put("1", "Linea");
		valueMap.put("2", "Areas");
		valueMap.put("3", "Columnas");
//		valueMap.put("4", "Relojes");
		selState.setValueMap(valueMap);
		
		form.setFields(selState);
		
		vLayout.addMember(form);
		vLayout.addMember(gg);
		
		this.addChild(vLayout);
		
		
		selState.addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				int typeGrafic = new Integer((String)event.getValue());
				gg.setGraficType(typeGrafic);
				gg.draw();
			}
		});
	}


	@Override
	public void refreshPanel() {
		//NADA
	}

	@Override
	public void freePanel() {
		//NADA
	}
}
