package ar.fi.uba.tempore.gwt.client.panel.report;

import java.util.Date;

import ar.fi.uba.tempore.gwt.client.panel.TabsPanelContainer;

import com.smartgwt.client.types.DragAppearance;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.VisibilityMode;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Button;
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
	
	public ReportTabPanel() {
		super();

		vLayout.setHeight100();
		vLayout.setWidth100();
		
		SectionStack sectionStackReport = new SectionStack();
		sectionStackReport.setDragAppearance(DragAppearance.TARGET);  
		sectionStackReport.setOverflow(Overflow.HIDDEN);
		sectionStackReport.setShowResizeBar(true);
		sectionStackReport.setCanDragResize(true);  
		sectionStackReport.setWidth(700);  
		sectionStackReport.setMaxHeight(500);
		sectionStackReport.setCanReorderSections(true);
		sectionStackReport.setVisibilityMode(VisibilityMode.MULTIPLE);  
		
		final Button btnReporte1 = new Button("Generar Reporte");
		btnReporte1.addClickHandler(onClickReport1);
		
		final Button btnReporte2 = new Button("Generar Reporte");
		btnReporte2.addClickHandler(onClickReport2);
		
		final Button btnReporte3 = new Button("Generar Reporte");
		btnReporte3.addClickHandler(onClickReport3);
		
		final Button btnReporte4 = new Button("Generar Reporte");
		btnReporte4.addClickHandler(onClickReport4);
		
		SectionStackSection sectionReport1 = new SectionStackSection("Proyectos-Horas");  
		sectionReport1.setExpanded(true);  
		sectionReport1.setCanCollapse(true);
		sectionReport1.setResizeable(false);
		sectionReport1.setCanReorder(true);
		sectionReport1.setItems(new Label("Reporte encargado de graficar en forma de torta la cantidad de horas totales reportadas para cada uno de los proyectos existentes en el sistema."), btnReporte1);
        
		SectionStackSection sectionReport2 = new SectionStackSection("Usuarios-Horas");  
		sectionReport2.setExpanded(true);  
		sectionReport2.setCanCollapse(true);
		sectionReport2.setResizeable(false);
		sectionReport2.setCanReorder(true);
		sectionReport2.addItem(new Label("Reporte que representa por medio de l&iacute;neas la cantidad de horas cargadas por cada usuario del proyecto seleccionado."));
		sectionReport2.addItem(btnReporte2);
		
		SectionStackSection sectionReport3 = new SectionStackSection("Tareas-Horas");  
		sectionReport3.setExpanded(true);  
		sectionReport3.setCanCollapse(true);
		sectionReport3.setResizeable(false);
		sectionReport3.setCanReorder(true);
		sectionReport3.addItem(new Label("Reporte que grafica mediante &aacute;reas las horas cargadas a cada una de las tareas creadas para el proyecto seleccionado."));
		sectionReport3.addItem(btnReporte3);
		
		SectionStackSection sectionReport4 = new SectionStackSection("Usuario-Proyecto");  
		sectionReport4.setExpanded(true);  
		sectionReport4.setCanCollapse(true);
		sectionReport4.setResizeable(false);
		sectionReport4.setCanReorder(true);
		sectionReport4.addItem(new Label("Reporte que grafica en columnas los usuario involucrados para todos los proyectos abiertos."));
		sectionReport4.addItem(btnReporte4);
		
        sectionStackReport.addSection(sectionReport1);
        sectionStackReport.addSection(sectionReport2);
        sectionStackReport.addSection(sectionReport3);
        sectionStackReport.addSection(sectionReport4);
        
//		final SelectItem selState = new SelectItem("Estado", "Tipo de Grafico");
//		LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>(4);  
//		valueMap.put("0", "Torta");
//		valueMap.put("1", "Linea");
//		valueMap.put("2", "Areas");
//		valueMap.put("3", "Columnas");
//		selState.setValueMap(valueMap);
		
        final Label title = new Label("Administraci&oacute;n de Reportes");
		title.setWidth(200);
		title.setHeight(15);
		
        title.setIcon("[SKIN]/actions/help.png");
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
