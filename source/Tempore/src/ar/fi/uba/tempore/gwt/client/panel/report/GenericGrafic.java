package ar.fi.uba.tempore.gwt.client.panel.report;

import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.corechart.AxisOptions;
import com.google.gwt.visualization.client.visualizations.corechart.CoreChart;
import com.google.gwt.visualization.client.visualizations.corechart.Options;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.events.ResizedEvent;
import com.smartgwt.client.widgets.events.ResizedHandler;
import com.smartgwt.client.widgets.layout.VLayout;


public abstract class GenericGrafic extends VLayout {
	public static final int PIE = 0;
	public static final int LINE = 1;
	public static final int AREA = 2;
	public static final int COLUMNS = 3;
	public static final Long HORA = 60*60*1000L;
	private int graficType;
	private static String html = "<div id=\"grafic_nested_div\" style=\"position: absolute; z-index: 1000000\"> </div>\n";
	private Options options;

	public GenericGrafic(String title, String titleX, String titleY, int graficType) {
		setGraficType(graficType);

		final HTMLFlow htmlFlow = new HTMLFlow(html); 

		options = Options.create();
		options.setWidth(750);
		options.setHeight(450);
		options.setTitle(title);
		AxisOptions optionsHAxis = AxisOptions.create();
		optionsHAxis.setTitle(titleX);
		AxisOptions optionsVAxis = AxisOptions.create();
		optionsVAxis.setTitle(titleY);
		options.setHAxisOptions(optionsHAxis);
		options.setVAxisOptions(optionsVAxis);

		this.setHeight100();
		this.setWidth100();
		this.setOverflow(Overflow.AUTO);
		this.addMember(htmlFlow);		

		this.addResizedHandler(new ResizedHandler() {
			@Override
			public void onResized(ResizedEvent event) {
				draw();
			}
		});
	}

	public void draw(){
		Runnable onLoadCallback = new Runnable() {
			public void run() {
				drawChart(createTable(), getOptions());
			}
		};

		VisualizationUtils.loadVisualizationApi(onLoadCallback, CoreChart.PACKAGE);
	}

	private void drawChart(DataTable data, Options options) {
		switch (getGraficType()){
		case PIE:
			drawPieChart(data, options);
			break;
		case AREA:
			drawAreaChart(data, options);
			break;
		case COLUMNS:
			drawColumnChart(data, options);
			break;
		case LINE:
			drawLineChart(data, options);
			break;
		default:
			SC.say("No se reconoce tipo de grafico. Se genera tipo LINEA por defecto.");
			drawLineChart(data, options);
			break;
		}
	}


	/**
	 * Metodo nativo de java para dibujar el componente
	 * @param data Datos de la tabla
	 * @param options opciones de la tabla
	 */
	private native void drawPieChart(DataTable data, Options options) /*-{
	    var chart = new $wnd.google.visualization.PieChart($doc.getElementById('grafic_nested_div'));
		chart.draw(data, options);
	  }-*/;

	/**
	 * Metodo nativo de java para dibujar el componente
	 * @param data Datos de la tabla
	 * @param options opciones de la tabla
	 */
	private native void drawLineChart(DataTable data, Options options) /*-{
	    var chart = new $wnd.google.visualization.LineChart($doc.getElementById('grafic_nested_div'));
		chart.draw(data, options);
	  }-*/;

	/**
	 * Metodo nativo de java para dibujar el componente
	 * @param data Datos de la tabla
	 * @param options opciones de la tabla
	 */
	private native void drawAreaChart(DataTable data, Options options) /*-{
	    var chart = new $wnd.google.visualization.AreaChart($doc.getElementById('grafic_nested_div'));
		chart.draw(data, options);
	  }-*/;

	/**
	 * Metodo nativo de java para dibujar el componente
	 * @param data Datos de la tabla
	 * @param options opciones de la tabla
	 */
	private native void drawColumnChart(DataTable data, Options options) /*-{
	    var chart = new $wnd.google.visualization.ColumnChart($doc.getElementById('grafic_nested_div'));
		chart.draw(data, options);
	  }-*/;

	/**
	 * Metodo nativo de java para dibujar el componente
	 * @param data Datos de la tabla
	 * @param options opciones de la tabla
	 */
	private native void drawGaugeChart(DataTable data, com.google.gwt.visualization.client.visualizations.Gauge.Options options) /*-{
	    var chart = new $wnd.google.visualization.Gauge($doc.getElementById('grafic_nested_div'));
		chart.draw(data, options);
	  }-*/;



	public abstract DataTable createTable();

	public Options getOptions() {
		return options;
	}

	public int getGraficType() {
		return graficType;
	}

	protected void setGraficType(int graficType) {
		this.graficType = graficType;
	}	
}
