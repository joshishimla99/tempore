package ar.fi.uba.tempore.gwt.client.panel;

import ar.fi.uba.tempore.gwt.client.panel.configuration.ConfigurationTabPanel;
import ar.fi.uba.tempore.gwt.client.panel.help.HelpTabPanel;
import ar.fi.uba.tempore.gwt.client.panel.project.ProjectTabPanel;
import ar.fi.uba.tempore.gwt.client.panel.report.ReportTabPanel;
import ar.fi.uba.tempore.gwt.client.panel.resource.ResourceTabPanel;
import ar.fi.uba.tempore.gwt.client.panel.task.TaskTabPanel;
import ar.fi.uba.tempore.gwt.client.panel.time.TimeTabPanel;

import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
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
		
		final Tab timeTab = new Tab("Tempore  ", "../images/ico/schedule.ico");
		timeTab.setWidth(tabWidth);
		timeTab.setPane(new TimeTabPanel());
		
		final Tab resourceTab = new Tab("Recursos  ", "../images/ico/user_group.ico");
		resourceTab.setWidth(tabWidth);   
		resourceTab.setPane(new ResourceTabPanel());
		
		final Tab projectTab = new Tab("Proyectos  ", "../images/ico/briefcase.ico");
		projectTab.setWidth(tabWidth);   
		projectTab.setPane(new ProjectTabPanel());
		
		final Tab taskTab = new Tab("Tareas  ", "../images/ico/notes.ico");
		taskTab.setWidth(tabWidth);
		taskTab.setPane(new TaskTabPanel());
		
		final Tab reportTab = new Tab("Reportes  ", "../images/ico/report.ico");
		reportTab.setWidth(tabWidth);
		reportTab.setPane(new ReportTabPanel());
		
		final Tab configurationTab = new Tab("Configuraci&oacute;n  ", "../images/ico/wrench.ico");
		configurationTab.setWidth(tabWidth);
		configurationTab.setPane(new ConfigurationTabPanel());
		
		final Tab helpTab = new Tab("Ayuda  ", "../images/ico/help1.ico");
		helpTab.setWidth(tabWidth);
		helpTab.setPane(new HelpTabPanel());
	
		
		//deteccion del select y el unselected de cada Tab
		addTabSelectedHandler(new TabSelectedHandler() {
			@Override
			public void onTabSelected(TabSelectedEvent event) {
				int tabsCount = getTabs().length;
				for (int i=0;i<tabsCount;i++){
					TabsPanelContainer subTabPanel = (TabsPanelContainer) event.getTabPane();					
					if (event.getTabNum() == i){
						//seleccionado
						//subTabPanel.selected();
					} else {
						//deseleccionado
						//subTabPanel.deselected();
					}
				}
			}
		});
		
		setTabs(timeTab, projectTab, resourceTab, taskTab, reportTab, configurationTab, helpTab);
		selectTab(2);
	}

}
