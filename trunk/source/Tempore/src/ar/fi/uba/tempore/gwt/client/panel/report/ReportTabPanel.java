package ar.fi.uba.tempore.gwt.client.panel.report;

import java.util.LinkedHashMap;

import ar.fi.uba.tempore.dto.ProjectStateDTO;
import ar.fi.uba.tempore.gwt.client.panel.TabsPanelContainer;

import com.google.gwt.user.client.Window;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.Gauge;
import com.google.gwt.visualization.client.visualizations.corechart.AreaChart;
import com.google.gwt.visualization.client.visualizations.corechart.ColumnChart;
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
	private static final String CONTEXT_AREA_WIDTH = "*";
	private static final int PIE = 0;
	private static final int LINE = 1;
	private static final int AREA = 2;
	private static final int COLUMN = 3;
	private static final int GAUGE = 4;

	private static String html = "<div id=\"chart_nested_div\" style=\"position: absolute; z-index: 1000000\"> </div>\n"; 

	private HTMLFlow htmlFlow;

	public ReportTabPanel() {
		super();

		vLayout.setHeight100();
		vLayout.setWidth100();
		vLayout.setOverflow(Overflow.AUTO);
		vLayout.setMargin(5);

		htmlFlow = new HTMLFlow(html); 
		final DynamicForm form = new DynamicForm();
		final SelectItem selState = new SelectItem("Estado", "Tipo de Grafico");
		LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>(5);  
		valueMap.put("0", "Torta");
		valueMap.put("1", "Linea");
		valueMap.put("2", "Areas");
		valueMap.put("3", "Columnas");
		valueMap.put("4", "Relojes");
		selState.setValueMap(valueMap);
		
		
		
		form.setFields(selState);
		
		vLayout.addMember(form);
		vLayout.addMember(htmlFlow);

		this.addChild(vLayout);
		
		final int typeGrafic = GAUGE;
		
		selState.addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				int typeGrafic = new Integer((String)event.getValue());
				loadVisualizationApi(typeGrafic);
			}
		});
		
		
		this.addResizedHandler(new ResizedHandler() {
			@Override
			public void onResized(ResizedEvent event) {
				loadVisualizationApi(typeGrafic);
			}
		});
	}

	private void loadVisualizationApi(int type) {
		Runnable onLoadPieCallback = new Runnable() {
			public void run() {
				drawPieChart(createTable(), createOptions());
			}
		};
		Runnable onLoadLineCallback = new Runnable() {
			public void run() {
				drawLineChart(createTable(), createOptions());
			}
		};
		Runnable onLoadAreaCallback = new Runnable() {
			public void run() {
				drawAreaChart(createTable(), createOptions());
			}
		};
		Runnable onLoadColumnCallback = new Runnable() {
			public void run() {
				drawColumnChart(createTable(), createOptions());
			}
		};
		Runnable onLoadGaugeCallback = new Runnable() {
			public void run() {
				drawGaugeChart(createTable(), createGaugeOptions());
			}
		};
		
		
		// Load the visualization api, passing the onLoadCallback to be called when loading is complete.
		switch (type){
		case PIE:
			VisualizationUtils.loadVisualizationApi(onLoadPieCallback, PieChart.PACKAGE);
			break;
		case LINE:
			VisualizationUtils.loadVisualizationApi(onLoadLineCallback, LineChart.PACKAGE);
			break;
		case AREA:
			VisualizationUtils.loadVisualizationApi(onLoadAreaCallback, AreaChart.PACKAGE);
			break;
		case COLUMN:
			VisualizationUtils.loadVisualizationApi(onLoadColumnCallback, ColumnChart.PACKAGE);
			break;
		case GAUGE:
			VisualizationUtils.loadVisualizationApi(onLoadGaugeCallback, Gauge.PACKAGE);
			break;
		default:
			break;
		} 
	}
		

	/**
	 * Datos del grafico
	 * @return
	 */
	private DataTable createTable() {
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

	/**
	 * Opciones del grafico
	 * @return
	 */
	private Options createOptions() {
		Options options = Options.create();
		options.setWidth(700);
		options.setHeight(400);
//		options.set3D(false);
		options.setTitle("Mis Tareas Diarias");
		return options;
	}

	/**
	 * Opciones del grafico
	 * @return
	 */
	private com.google.gwt.visualization.client.visualizations.Gauge.Options createGaugeOptions() {
		com.google.gwt.visualization.client.visualizations.Gauge.Options o = com.google.gwt.visualization.client.visualizations.Gauge.Options.create();
		o.setWidth(700);
		o.setHeight(400);
		o.setRedRange(8, 10);
		o.setYellowRange(6, 8);
//		o.setGreenRange(0, 6);
		o.setGaugeRange(0, 10);
		return o;
	}

	/**
	 * Metodo nativo de java para dibujar el componente
	 * @param data Datos de la tabla
	 * @param options opciones de la tabla
	 */
	private native void drawPieChart(DataTable data, Options options) /*-{
	    var chart = new $wnd.google.visualization.PieChart($doc.getElementById('chart_nested_div'));
		chart.draw(data, options);
	  }-*/;
	
	/**
	 * Metodo nativo de java para dibujar el componente
	 * @param data Datos de la tabla
	 * @param options opciones de la tabla
	 */
	private native void drawLineChart(DataTable data, Options options) /*-{
	    var chart = new $wnd.google.visualization.LineChart($doc.getElementById('chart_nested_div'));
		chart.draw(data, options);
	  }-*/;

	/**
	 * Metodo nativo de java para dibujar el componente
	 * @param data Datos de la tabla
	 * @param options opciones de la tabla
	 */
	private native void drawAreaChart(DataTable data, Options options) /*-{
	    var chart = new $wnd.google.visualization.AreaChart($doc.getElementById('chart_nested_div'));
		chart.draw(data, options);
	  }-*/;
	
	/**
	 * Metodo nativo de java para dibujar el componente
	 * @param data Datos de la tabla
	 * @param options opciones de la tabla
	 */
	private native void drawColumnChart(DataTable data, Options options) /*-{
	    var chart = new $wnd.google.visualization.ColumnChart($doc.getElementById('chart_nested_div'));
		chart.draw(data, options);
	  }-*/;
	
	/**
	 * Metodo nativo de java para dibujar el componente
	 * @param data Datos de la tabla
	 * @param options opciones de la tabla
	 */
	private native void drawGaugeChart(DataTable data, com.google.gwt.visualization.client.visualizations.Gauge.Options options) /*-{
	    var chart = new $wnd.google.visualization.Gauge($doc.getElementById('chart_nested_div'));
		chart.draw(data, options);
	  }-*/;

	@Override
	public void refreshPanel() {
		//NADA
	}

	@Override
	public void freePanel() {
		//NADA
	}
}
