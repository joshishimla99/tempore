package ar.fi.uba.tempore.gwt.client.panel.menus;

import ar.fi.uba.tempore.gwt.client.panel.project.NewProjectPanel;

import com.smartgwt.client.widgets.tab.Tab;

public class ProjectTabPanel extends SubTabPanel{
	
	
	public ProjectTabPanel(){
		super();
		
		NewProjectPanel newProjectPanel = new NewProjectPanel();
		
		Tab newProjectTab = new Tab("Nuevo");
		newProjectTab.setPane(newProjectPanel);
		
		addChildPanels(newProjectPanel);

		getTabPanel().setTabs(newProjectTab);
		
		getTabPanel().selectTab(0);
		this.addChild(getTabPanel());
		this.redraw();
	}

}
