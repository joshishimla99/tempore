package ar.fi.uba.tempore.gwt.client.panel.report;

import ar.fi.uba.tempore.gwt.client.panel.TabsPanelContainer;

import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.corechart.Options;
import com.google.gwt.visualization.client.visualizations.corechart.PieChart;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.events.ResizedEvent;
import com.smartgwt.client.widgets.events.ResizedHandler;
import com.smartgwt.client.widgets.layout.VLayout;

@SuppressWarnings("deprecation")
public class ReportTabPanel extends TabsPanelContainer{

	private VLayout vLayout = new VLayout();
	private static final String CONTEXT_AREA_WIDTH = "*";
	private static String html = "<div id=\"chart_nested_div\" style=\"position: absolute; z-index: 1000000\"> </div>\n"; 

	private HTMLFlow htmlFlow;

	public ReportTabPanel() {
		super();

		vLayout.setHeight100();
		vLayout.setWidth100();
		vLayout.setOverflow(Overflow.AUTO);
		vLayout.setMargin(5);

		htmlFlow = new HTMLFlow(html); 
		vLayout.addMember(htmlFlow);

		this.addChild(vLayout);
		loadVisualizationApi();
		
		addResizedHandler(new ResizedHandler() {
			@Override
			public void onResized(ResizedEvent event) {
				// TODO Auto-generated method stub
				loadVisualizationApi();
			}
		});
	}

	private void loadVisualizationApi() {
		Runnable onLoadCallback = new Runnable() {
			public void run() {
				drawPieChart(createTable(), createOptions());
			}
		};

		// Load the visualization api, passing the onLoadCallback to be called when loading is complete.
		VisualizationUtils.loadVisualizationApi(onLoadCallback, PieChart.PACKAGE);
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
	 * Metodo nativo de java para dibujar el componente
	 * @param data Datos de la tabla
	 * @param options opciones de la tabla
	 */
	private native void drawPieChart(DataTable data, Options options) /*-{
	    var chart = new $wnd.google.visualization.PieChart($doc.getElementById('chart_nested_div'));
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
