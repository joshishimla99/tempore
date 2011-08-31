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
		
		Tab projectTab = new Tab("Proyectos   ", "../images/32x32/Portfolio.png");
		projectTab.setWidth(100);   
		projectTab.setPane(new ProjectTabPanel(projectPanel));
		
		Tab configurationTab = new Tab("Configuraci&oacute;n   ", "../images/32x32/Tools.png");
		configurationTab.setWidth(100);
		configurationTab.setPane(new ConfigurationTabPanel());
		
		Tab taskTab = new Tab("Tareas   ", "../images/32x32/Paste.png");
		taskTab.setWidth(100);
		taskTab.setPane(new TaskTabPanel(projectPanel));
		
		Tab reportTab = new Tab("Reportes   ", "../images/32x32/Mail.png");
		reportTab.setWidth(100);
		reportTab.setPane(new ReportTabPanel());
		
		Tab helpTab = new Tab("Ayuda   ", "../images/32x32/Help.png");
		helpTab.setWidth(100);
		helpTab.setPane(new HelpTabPanel());
		
		this.addTabSelectedHandler(new TabSelectedHandler() {
            public void onTabSelected(TabSelectedEvent event) {
                Tab selectedTab = event.getTab();
                SubTabPanel subTabPanel = (SubTabPanel) selectedTab.getPane();
                subTabPanel.loadChildPanels();
            }
        });

		super.setTabs(projectTab, configurationTab, taskTab, reportTab, helpTab);
		super.draw();
	}

}
