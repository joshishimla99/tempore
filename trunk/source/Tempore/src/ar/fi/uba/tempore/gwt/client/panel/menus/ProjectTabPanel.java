package ar.fi.uba.tempore.gwt.client.panel.menus;

import ar.fi.uba.tempore.gwt.client.panel.project.ModifyProjectPanel;
import ar.fi.uba.tempore.gwt.client.panel.project.NewProjectPanel;
import ar.fi.uba.tempore.gwt.client.panel.project.ProjectPanel;

public class ProjectTabPanel extends SubTabPanel{
	
	public ProjectTabPanel(ProjectPanel projectPanel){
		super();
		
		NewProjectPanel newProjectPanel = new NewProjectPanel(projectPanel);
		ModifyProjectPanel modifyProjectPanel = new ModifyProjectPanel(projectPanel);
		
		super.getTabPanel().add(newProjectPanel, "Nuevo");
		super.getTabPanel().add(modifyProjectPanel, "Editar");
		
		super.addChildPanels(newProjectPanel);
		super.addChildPanels(modifyProjectPanel);
		
		super.getTabPanel().selectTab(0);
		this.addChild(super.getTabPanel());
	}

}
