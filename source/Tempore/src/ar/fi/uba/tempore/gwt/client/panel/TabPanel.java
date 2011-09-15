package ar.fi.uba.tempore.gwt.client.panel;

import ar.fi.uba.tempore.gwt.client.panel.menus.ConfigurationTabPanel;
import ar.fi.uba.tempore.gwt.client.panel.menus.HelpTabPanel;
import ar.fi.uba.tempore.gwt.client.panel.menus.ProjectTabPanel;
import ar.fi.uba.tempore.gwt.client.panel.menus.ReportTabPanel;
import ar.fi.uba.tempore.gwt.client.panel.menus.SubTabPanel;
import ar.fi.uba.tempore.gwt.client.panel.menus.TaskTabPanel;
import ar.fi.uba.tempore.gwt.client.panel.menus.TimeTabPanel;

import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedHandler;

public class TabPanel extends TabSet {
	
	private static final String TEMPORE_TABS = "Tempore_Tabs";

	public TabPanel(){
		super();
		setWidth("80%");
		setHeight100();
		setMargin(5);
		setCanDragResize(true);  
		setResizeFrom("L");   
		setMinWidth(400);  
		setMinHeight(200);
		setStyleName(TEMPORE_TABS);
		
		
		Tab timeTab = new Tab("Tempore  ", "../images/ico/schedule.ico");
		timeTab.setWidth(100);
		timeTab.setPane(new TimeTabPanel());
		
//		Tab resourceTab = new Tab("Recursos  ", "../images/ico/user_group.ico");
//		resourceTab.setWidth(100);   
//		resourceTab.setPane(new ResourceTabPanel());
		
		Tab projectTab = new Tab("Proyectos  ", "../images/ico/briefcase.ico");
		projectTab.setWidth(100);   
		projectTab.setPane(new ProjectTabPanel());
		
		Tab taskTab = new Tab("Tareas  ", "../images/ico/notes.ico");
		taskTab.setWidth(100);
		taskTab.setPane(new TaskTabPanel());
		
		Tab reportTab = new Tab("Reportes  ", "../images/ico/report.ico");
		reportTab.setWidth(100);
		reportTab.setPane(new ReportTabPanel());
		
		Tab configurationTab = new Tab("Configuraci&oacute;n  ", "../images/ico/wrench.ico");
		configurationTab.setWidth(100);
		configurationTab.setPane(new ConfigurationTabPanel());
		
		Tab helpTab = new Tab("Ayuda  ", "../images/ico/help1.ico");
		helpTab.setWidth(100);
		helpTab.setPane(new HelpTabPanel());
		
		this.addTabSelectedHandler(new TabSelectedHandler() {
            public void onTabSelected(TabSelectedEvent event) {
                Tab selectedTab = event.getTab();
                SubTabPanel subTabPanel = (SubTabPanel) selectedTab.getPane();
                subTabPanel.loadChildPanels();
            }
        });

		setTabs(timeTab, projectTab, /*resourceTab,*/ taskTab, reportTab, configurationTab, helpTab);
		selectTab(4);
	}

}
