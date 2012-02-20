package ar.fi.uba.tempore.gwt.client.panel.report;

import java.util.Date;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class ReportFilter6 extends VLayout {

	private final DynamicForm formFilterDate = new DynamicForm();
	protected static final String DESDE_FIELD = "DesdeItem";
	protected static final String HASTA_FIELD = "HastaItem";

	public ReportFilter6(){
		//Filtro Fecha
		final DateItem ini = new DateItem(DESDE_FIELD,"Desde");
		ini.setDisplayFormat(DateDisplayFormat.TOEUROPEANSHORTDATE);
		ini.setValue(new Date(System.currentTimeMillis() + (3600000*24*30) ));
		final DateItem end = new DateItem(HASTA_FIELD, "Hasta");
		end.setDisplayFormat(DateDisplayFormat.TOEUROPEANSHORTDATE);

		formFilterDate.setFields(ini,end);



		final HTMLFlow htmlFlow5 = new HTMLFlow("Reporte que grafica en columnas los usuario involucrados para todos los proyectos abiertos.");  
	    htmlFlow5.setPadding(10);

		final ImgButton btnReporte5 = new ImgButton();
		btnReporte5.addClickHandler(onClickReport6);
		btnReporte5.setSrc("../images/report/area.png");
		btnReporte5.setSize(64);
		btnReporte5.setAltText("Generar reporte");
	    
	    final HLayout hLayout5 = new HLayout();
	    hLayout5.setAlign(Alignment.CENTER);
	    hLayout5.addMember(btnReporte5);
	    
	    this.addMember(htmlFlow5);
	    this.addMember(formFilterDate);
	    this.addMember(hLayout5);
	}
	
	private ClickHandler onClickReport6 = new ClickHandler() {
		@Override
		public void onClick(ClickEvent event) {
			final Date ini = (Date) formFilterDate.getValue(DESDE_FIELD);
			final Date end = (Date) formFilterDate.getValue(HASTA_FIELD);
			
//			new Report6().draw(ini, end);			
		}
	};
}
