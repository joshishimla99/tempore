package ar.fi.uba.tempore.gwt.client.panel.menus;

import com.smartgwt.client.widgets.tab.Tab;

import ar.fi.uba.tempore.gwt.client.panel.project.ModifyProjectPanel;
import ar.fi.uba.tempore.gwt.client.panel.project.NewProjectPanel;
import ar.fi.uba.tempore.gwt.client.panel.project.ProjectPanel;

public class ProjectTabPanel extends SubTabPanel{
	
	public ProjectTabPanel(ProjectPanel projectPanel){
		super();
		
		NewProjectPanel newProjectPanel = new NewProjectPanel(projectPanel);
		ModifyProjectPanel modifyProjectPanel = new ModifyProjectPanel(projectPanel);
		
		Tab newProjectTab = new Tab("Nuevo");
		newProjectTab.setPane(newProjectPanel);
		
		Tab editProjectTab = new Tab("Editar");
		editProjectTab.setPane(modifyProjectPanel);
		
		super.addChildPanels(newProjectPanel);
		super.addChildPanels(modifyProjectPanel);
		
		super.getTabPanel().setTabs(newProjectTab, editProjectTab);
		
		super.getTabPanel().selectTab(0);
		this.addChild(super.getTabPanel());
	}

}
