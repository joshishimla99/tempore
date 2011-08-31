package ar.fi.uba.tempore.gwt.client.panel;

import ar.fi.uba.tempore.gwt.client.panel.project.ProjectPanel;

import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedHandler;

public class MainTabPanel extends TabSet {
	
	public MainTabPanel(ProjectPanel projectPanel){
		super();
		super.setWidth(700);
		super.setHeight(800);
		
		Tab timeTab = new Tab("Tempore  ", "../images/ico/schedule.ico");
		timeTab.setWidth(150);
		timeTab.setPane(new TimeTabPanel());
		
		Tab projectTab = new Tab("Proyectos  ", "../images/ico/briefcase.ico");
		projectTab.setWidth(150);   
		projectTab.setPane(new ProjectTabPanel(projectPanel));
		
		Tab configurationTab = new Tab("Configuraci&oacute;n  ", "../images/ico/wrench.ico");
		configurationTab.setWidth(100);
		configurationTab.setPane(new ConfigurationTabPanel());
		
		Tab taskTab = new Tab("Tareas  ", "../images/ico/notes.ico");
		taskTab.setWidth(100);
		taskTab.setPane(new TaskTabPanel(projectPanel));
		
		Tab reportTab = new Tab("Reportes  ", "../images/ico/report.ico");
		reportTab.setWidth(100);
		reportTab.setPane(new ReportTabPanel());
		
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

		super.setTabs(timeTab, projectTab, configurationTab, taskTab, reportTab, helpTab);
		super.draw();
	}

}
