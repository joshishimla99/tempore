package ar.fi.uba.tempore.gwt.client.panel.menus;

import ar.fi.uba.tempore.gwt.client.panel.project.NewProjectPanel;
import ar.fi.uba.tempore.gwt.client.panel.project.ProjectPanel;

import com.smartgwt.client.widgets.tab.Tab;

public class ProjectTabPanel extends SubTabPanel{
	
	private ProjectPanel projectPanel;
	
	public ProjectTabPanel(final ProjectPanel projectPanel){
		super();
		this.projectPanel = projectPanel;
		
		NewProjectPanel newProjectPanel = new NewProjectPanel(this.projectPanel);
		
		Tab newProjectTab = new Tab("Nuevo");
		newProjectTab.setPane(newProjectPanel);
		
		super.addChildPanels(newProjectPanel);
		
		super.getTabPanel().setTabs(newProjectTab);
		
		super.getTabPanel().selectTab(0);
		this.addChild(super.getTabPanel());
	}

}
