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
		
		addChildPanels(newProjectPanel);

		getTabPanel().setTabs(newProjectTab);
		
		getTabPanel().selectTab(0);
		this.addChild(getTabPanel());
		this.redraw();
	}

}
