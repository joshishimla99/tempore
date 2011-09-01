package ar.fi.uba.tempore.gwt.client.panel.menus;

import ar.fi.uba.tempore.gwt.client.panel.project.ProjectPanel;
import ar.fi.uba.tempore.gwt.client.panel.task.ModifyTaskPanel;
import ar.fi.uba.tempore.gwt.client.panel.task.NewTaskPanel;

public class TaskTabPanel extends SubTabPanel {
	
	public TaskTabPanel(ProjectPanel projectPanel){
		super();
		NewTaskPanel newTaskPanel = new NewTaskPanel(projectPanel);
		ModifyTaskPanel modifyTaskPanel = new ModifyTaskPanel();
		super.getTabPanel().add(newTaskPanel, "Nueva");
		super.getTabPanel().add(modifyTaskPanel, "Editar");
		
		super.addChildPanels(newTaskPanel);
		super.addChildPanels(modifyTaskPanel);
		
		super.getTabPanel().selectTab(0);
		this.addChild(super.getTabPanel());
	}

}
