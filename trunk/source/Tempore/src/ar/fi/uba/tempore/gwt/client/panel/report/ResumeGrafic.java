package ar.fi.uba.tempore.gwt.client.panel.report;

import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.LegendPosition;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.corechart.AxisOptions;
import com.google.gwt.visualization.client.visualizations.corechart.CoreChart;
import com.google.gwt.visualization.client.visualizations.corechart.Options;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.events.ResizedEvent;
import com.smartgwt.client.widgets.events.ResizedHandler;
import com.smartgwt.client.widgets.layout.VLayout;


public abstract class ResumeGrafic extends VLayout {
	public static final Long HORA = 60*60*1000L;
	private static String html = "<div id=\"grafic_nested_div\" style=\"position: absolute; z-index: 1000000\"> </div>\n";
	private Options options;

	public ResumeGrafic() {

		final HTMLFlow htmlFlow = new HTMLFlow(html); 

		options = Options.create();
		options.setWidth(300);
		options.setColors("gray");
		options.setHeight(170);
//		options.setTitle("XXXXXXX");
		options.setLegend(LegendPosition.NONE);
		AxisOptions optionsHAxis = AxisOptions.create();
//		optionsHAxis.setTitle(titleX);
		AxisOptions optionsVAxis = AxisOptions.create();
		optionsVAxis.setMinValue(0);
		optionsVAxis.setMaxValue(8);
//		optionsVAxis.setTitle(titleY);
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
				drawColumnChart(createTable(), getOptions());
			}
		};

		VisualizationUtils.loadVisualizationApi(onLoadCallback, CoreChart.PACKAGE);
	}

	/**
	 * Metodo nativo de java para dibujar el componente
	 * @param data Datos de la tabla
	 * @param options opciones de la tabla
	 */
	private native void drawColumnChart(DataTable data, Options options) /*-{
	    var chart = new $wnd.google.visualization.ColumnChart($doc.getElementById('grafic_nested_div'));
		chart.draw(data, options);
	}-*/;


	public abstract DataTable createTable();

	public Options getOptions() {
		return options;
	}
}
