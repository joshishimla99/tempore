package ar.fi.uba.tempore.gwt.client.panel.report;

import java.util.Date;

import ar.fi.uba.tempore.gwt.client.panel.TabsPanelContainer;

import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.VisibilityMode;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.layout.VLayout;

public class ReportTabPanel extends TabsPanelContainer{

	protected static final String DESDE_FIELD = "DesdeItem";
	protected static final String HASTA_FIELD = "HastaItem";
	private VLayout vLayout = new VLayout();
//	private GenericGrafic gg = null;
	private final DynamicForm form = new DynamicForm();
	
	 private static final String HELPTEXT = "<br><b>Horas cargadas por Proyecto</b><br>Reporte encargado de graficar en forma de torta la cantidad de horas totales reportadas para cada uno de los proyectos existentes en el sistema. " +    
     "<br><br><b>Horas cargadas por Usuario del Proyecto seleccionado</b><br>Reporte que representa por medio de l&iacute;neas la cantidad de horas cargadas por cada usuario del proyecto seleccionado." +  
     "<br><br><b>Horas cargadas a cada Tarea del Proyecto seleccionado</b><br>Reporte que grafica mediante &aacute;reas las horas cargadas a cada una de las tareas creadas para el proyecto seleccionado." +  
     "<br><br><b>Usuarios por proyecto</b><br>Reporte que grafica en columnas los usuario involucrados para todos los proyectos abiertos.";  
	 Integer IMAGE_SIZE = 62;
	 
	public ReportTabPanel() {
		super();

		vLayout.setHeight100();
		vLayout.setWidth100();
		
		SectionStack sectionStackReport = new SectionStack();
		sectionStackReport.setWidth(800);  
		sectionStackReport.setMaxHeight(500);
		sectionStackReport.setVisibilityMode(VisibilityMode.MULTIPLE);  
		
		final ImgButton btnReporte1 = new ImgButton();
		btnReporte1.addClickHandler(onClickReport1);
		btnReporte1.setSrc("../images/report/LineReport.jpg");
		btnReporte1.setHeight(61);
		btnReporte1.setWidth(61);
		btnReporte1.setAltText("Generar reporte");
		
		final ImgButton btnReporte2 = new ImgButton();
		btnReporte2.addClickHandler(onClickReport2);
		btnReporte2.setSrc("../images/report/ColumnReport.gif");
		btnReporte2.setHeight(54);
		btnReporte2.setWidth(54);
		btnReporte2.setAltText("Generar reporte");
		
		final ImgButton btnReporte3 = new ImgButton();
		btnReporte3.addClickHandler(onClickReport3);
		btnReporte3.setSize(IMAGE_SIZE);
		btnReporte3.setSrc("../images/report/AreaReport.png");
		btnReporte3.setHeight(55);
		btnReporte3.setWidth(55);
		btnReporte3.setAltText("Generar reporte");
		
		final ImgButton btnReporte4 = new ImgButton();
		btnReporte4.addClickHandler(onClickReport4);
		btnReporte4.setSrc("../images/report/reportTorta.jpg");
		btnReporte4.setHeight(55);
		btnReporte4.setWidth(55);
		btnReporte4.setAltText("Generar reporte");
		
		HTMLFlow htmlFlow1 = new HTMLFlow();  
	    htmlFlow1.setOverflow(Overflow.AUTO);  
	    htmlFlow1.setPadding(10);
	    
	    HTMLFlow htmlFlow2 = new HTMLFlow();  
	    htmlFlow2.setOverflow(Overflow.AUTO);  
	    htmlFlow2.setPadding(10);
	    
	    HTMLFlow htmlFlow3 = new HTMLFlow();  
	    htmlFlow3.setOverflow(Overflow.AUTO);  
	    htmlFlow3.setPadding(10);
	    
	    HTMLFlow htmlFlow4 = new HTMLFlow();  
	    htmlFlow4.setOverflow(Overflow.AUTO);  
	    htmlFlow4.setPadding(10);
		
	    SectionStackSection sectionReport1 = new SectionStackSection("Proyectos-Horas");  
	    sectionReport1.setExpanded(true);
	    sectionReport1.setCanCollapse(true);
		htmlFlow1.setContents("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Reporte encargado de graficar en forma de torta la cantidad de horas totales reportadas para cada uno de los proyectos existentes en el sistema.");
		htmlFlow1.addChild(btnReporte1);
		sectionReport1.addItem(htmlFlow1);
		//sectionReport1.addItem(btnReporte1);
		
		SectionStackSection sectionReport2 = new SectionStackSection("Usuarios-Horas");  
		sectionReport2.setExpanded(true);  
		sectionReport2.setCanCollapse(true);
		sectionReport2.setResizeable(true);
		sectionReport2.setCanReorder(true);
		htmlFlow2.setContents("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Reporte que representa por medio de l&iacute;neas la cantidad de horas cargadas por cada usuario del proyecto seleccionado.");
		htmlFlow2.addChild(btnReporte2);
		sectionReport2.addItem(htmlFlow2);
		//sectionReport2.addItem(btnReporte2);
		
		SectionStackSection sectionReport3 = new SectionStackSection("Tareas-Horas");  
		sectionReport3.setExpanded(true);  
		sectionReport3.setCanCollapse(true);
		sectionReport3.setResizeable(false);
		sectionReport3.setCanReorder(true);
		htmlFlow3.setContents("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Reporte que grafica mediante &aacute;reas las horas cargadas a cada una de las tareas creadas para el proyecto seleccionado.");
		htmlFlow3.addChild(btnReporte3);
		sectionReport3.addItem(htmlFlow3);
//		sectionReport3.addItem(btnReporte3);
		
		SectionStackSection sectionReport4 = new SectionStackSection("Usuario-Proyecto");  
		sectionReport4.setExpanded(true);  
		sectionReport4.setCanCollapse(true);
		sectionReport4.setResizeable(false);
		sectionReport4.setCanReorder(true);
		htmlFlow4.setContents("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Reporte que grafica en columnas los usuario involucrados para todos los proyectos abiertos.");
		htmlFlow4.addChild(btnReporte4);
		sectionReport4.addItem(htmlFlow4);
		//sectionReport4.addItem(btnReporte4);
		
		sectionStackReport.addSection(sectionReport1);
        sectionStackReport.addSection(sectionReport2);
        sectionStackReport.addSection(sectionReport3);
        sectionStackReport.addSection(sectionReport4);
        
        final Label title = new Label("Administraci&oacute;n de Reportes");
		title.setWidth(200);
		title.setHeight(15);
        title.setIcon("[SKIN]/actions/help.png");
        title.setStyleName("Informal");
		title.setIconOrientation("right");
        title.addIconClickHandler(new com.smartgwt.client.widgets.events.IconClickHandler() {
			
			@Override
			public void onIconClick(
					com.smartgwt.client.widgets.events.IconClickEvent event) {
				 	SC.say(HELPTEXT);
				
			}
		});  
		
		final DateItem ini = new DateItem(DESDE_FIELD,"Desde");
		ini.setValue(new Date(System.currentTimeMillis() + (3600000*24*30) ));
		final DateItem end = new DateItem(HASTA_FIELD, "Hasta");
		
		form.setFields(
					ini,
					end);
		
		vLayout.addMember(title);
		vLayout.addMember(form);
		vLayout.addMember(sectionStackReport);
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
