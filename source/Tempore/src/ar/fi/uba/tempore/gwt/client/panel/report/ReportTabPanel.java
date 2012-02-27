package ar.fi.uba.tempore.gwt.client.panel.report;

import ar.fi.uba.tempore.gwt.client.panel.TabsPanelContainer;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.VisibilityMode;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.layout.VLayout;

public class ReportTabPanel extends TabsPanelContainer{

	final VLayout graficLayout = new VLayout();
	
	 private static final String HELPTEXT = "<br><b>Horas cargadas por Proyecto</b><br>Reporte de tipo torta que muestra la cantidad de horas totales registradas por los miembros para cada uno de los proyectos existentes en el sistema. " +    
     "<br><br><b>Horas cargadas por Usuario del Proyecto seleccionado</b><br>Reporte que representa por medio de l&iacute;neas la cantidad de horas cargadas por cada usuario del proyecto seleccionado." +  
     "<br><br><b>Horas cargadas a cada Tarea del Proyecto seleccionado</b><br>Reporte que grafica mediante &aacute;reas las horas cargadas a cada una de las tareas creadas para el proyecto seleccionado." +  
     "<br><br><b>Usuarios por proyecto</b><br>Reporte que grafica en columnas los usuario involucrados para todos los proyectos abiertos.";  
	 
	public ReportTabPanel() {
		super();
		
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


        graficLayout.setWidth("75%");
        graficLayout.setHeight100();
        graficLayout.setAlign(Alignment.CENTER);
        graficLayout.setMargin(10);
        graficLayout.setBorder("2px solid gray");
        

		final Img img = new Img("../images/tempore_reporting.jpg");
		img.setWidth(619);
		img.setHeight(222);		
		graficLayout.addMember(img);	    
	    
	    final SectionStackSection sectionReport1 = new SectionStackSection("Proyectos-Horas");  
	    sectionReport1.setExpanded(true);
	    sectionReport1.setCanCollapse(true);
		sectionReport1.addItem(new ReportFilter1(graficLayout));
		
		final SectionStackSection sectionReport2 = new SectionStackSection("Usuarios-Horas");  
		sectionReport2.setCanCollapse(true);
		sectionReport2.addItem(new ReportFilter2(graficLayout));
		
		final SectionStackSection sectionReport3 = new SectionStackSection("Tareas-Horas");  
		sectionReport3.setCanCollapse(true);
		sectionReport3.addItem(new ReportFilter3(graficLayout));
		
		final SectionStackSection sectionReport4 = new SectionStackSection("Usuario-Proyecto");  
		sectionReport4.setCanCollapse(true);
		sectionReport4.addItem(new ReportFilter4(graficLayout));

		final SectionStackSection sectionReport5 = new SectionStackSection("Tipo Tarea-Proyecto");  
		sectionReport5.setCanCollapse(true);
		sectionReport5.addItem(new ReportFilter5(graficLayout));
		
		final SectionStackSection sectionReport6 = new SectionStackSection("Horas Usuario-Semana");  
		sectionReport6.setCanCollapse(true);
		sectionReport6.addItem(new ReportFilter6(graficLayout));

		
		final SectionStack sectionStackReport = new SectionStack();
		sectionStackReport.setWidth("25%");  
		sectionStackReport.setHeight100();
		sectionStackReport.setVisibilityMode(VisibilityMode.MUTEX);  
		sectionStackReport.addSection(sectionReport1);
        sectionStackReport.addSection(sectionReport2);
        sectionStackReport.addSection(sectionReport3);
        sectionStackReport.addSection(sectionReport4);
        sectionStackReport.addSection(sectionReport5);
        sectionStackReport.addSection(sectionReport6);
        
        
        
        final HLayout hLayout = new HLayout();
		hLayout.setHeight100();
		hLayout.setWidth100();        		
        hLayout.setMembersMargin(5);
		hLayout.addMember(sectionStackReport);
		hLayout.addMember(graficLayout);
		
		final VLayout vLayout = new VLayout();
		vLayout.setHeight100();
		vLayout.setWidth100();
		vLayout.setMembersMargin(20);
		vLayout.addMember(title);
		vLayout.addMember(hLayout);
		
		this.addChild(vLayout);
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
