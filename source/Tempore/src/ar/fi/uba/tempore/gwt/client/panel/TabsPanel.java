package ar.fi.uba.tempore.gwt.client.panel;

import ar.fi.uba.tempore.gwt.client.panel.configuration.ConfigurationTabPanel;
import ar.fi.uba.tempore.gwt.client.panel.help.HelpTabPanel;
import ar.fi.uba.tempore.gwt.client.panel.project.ProjectTabPanel;
import ar.fi.uba.tempore.gwt.client.panel.report.ReportTabPanel;
import ar.fi.uba.tempore.gwt.client.panel.resource.ResourceTabPanel;
import ar.fi.uba.tempore.gwt.client.panel.task.TaskTabPanel;
import ar.fi.uba.tempore.gwt.client.panel.time.DragDropTimePanel;

import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.tab.events.TabDeselectedEvent;
import com.smartgwt.client.widgets.tab.events.TabDeselectedHandler;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedHandler;

public class TabsPanel extends TabSet {
	
	private static final String TEMPORE_TABS = "Tempore_Tabs";

	public TabsPanel(){
		super();
		setWidth("80%");
		setHeight100();
		setMargin(5);
		setCanDragResize(true);  
		setResizeFrom("L");   
		setMinWidth(400);  
		setMinHeight(200);
		setStyleName(TEMPORE_TABS);
		
		int tabWidth = 100;
		
		//Tab 0
		final Tab timeTab = new Tab("Tempore  ", "../images/ico/schedule.ico");
		timeTab.setWidth(tabWidth);
//		timeTab.setPane(new TimeTabPanel());
		timeTab.setPrompt("Esta secci&oacute;n le permite administrar sus horas");
		timeTab.setPane(new DragDropTimePanel());
		
		//Tab 1
		final Tab resourceTab = new Tab("Recursos  ", "../images/ico/user_group.ico");
		resourceTab.setPrompt("Esta secci&oacute;n le permite administrar los recursos del proyecto");
		resourceTab.setWidth(tabWidth);   
		resourceTab.setPane(new ResourceTabPanel());
		
		//Tab 2
		final Tab projectTab = new Tab("Proyectos  ", "../images/ico/briefcase.ico");
		projectTab.setWidth(tabWidth);   
		projectTab.setPrompt("Esta secci&oacute;n le permite crear y editar la informaci&oacute;n de proyectos");
		projectTab.setPane(new ProjectTabPanel());
		
		//Tab 3
		final Tab taskTab = new Tab("Tareas  ", "../images/ico/notes.ico");
		taskTab.setWidth(tabWidth);
		taskTab.setPrompt("Esta secci&oacute;n le permite administrar las tareas del proyecto");
		taskTab.setPane(new TaskTabPanel());
		
		//Tab 4
		final Tab reportTab = new Tab("Reportes  ", "../images/ico/report.ico");
		reportTab.setWidth(tabWidth);
		reportTab.setPrompt("Esta secci&oacute;n le permite generar reportes");
		reportTab.setPane(new ReportTabPanel());
		
		//Tab 5
		final Tab configurationTab = new Tab("Configuraci&oacute;n  ", "../images/ico/wrench.ico");
		configurationTab.setWidth(tabWidth);
		configurationTab.setPrompt("Esta secci&oacute;n le permite administrar los usuarios, los clientes y las alarmas");
		configurationTab.setPane(new ConfigurationTabPanel());
		
		//Tab 6
		final Tab helpTab = new Tab("Ayuda  ", "../images/ico/help1.ico");
		helpTab.setWidth(tabWidth);
		helpTab.setPane(new HelpTabPanel());
	
		
		//deteccion del select y el unselected de cada Tab
		addTabSelectedHandler(new TabSelectedHandler() {
			@Override
			public void onTabSelected(TabSelectedEvent event) {
				//Tab seleccionado
				TabsPanelContainer subTabPanel = (TabsPanelContainer) event.getTabPane();
				subTabPanel.refreshPanel();
			}
		});
		addTabDeselectedHandler(new TabDeselectedHandler() {
			@Override
			public void onTabDeselected(TabDeselectedEvent event) {
				//Tab DESseleccionado
				TabsPanelContainer subTabPanel = (TabsPanelContainer) event.getTabPane();
				subTabPanel.freePanel();
			}
		});
		
		setTabs(timeTab, projectTab, resourceTab, taskTab, reportTab, configurationTab, helpTab);
		selectTab(3);
	}

}
