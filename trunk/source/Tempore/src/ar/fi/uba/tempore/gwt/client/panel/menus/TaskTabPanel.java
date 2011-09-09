package ar.fi.uba.tempore.gwt.client.panel.menus;

import ar.fi.uba.tempore.gwt.client.panel.task.ModifyTaskPanel;
import ar.fi.uba.tempore.gwt.client.panel.task.NewTaskPanel;

import com.smartgwt.client.widgets.tab.Tab;

public class TaskTabPanel extends SubTabPanel {
	
	public TaskTabPanel(){
		super();
		NewTaskPanel newTaskPanel = new NewTaskPanel();
		ModifyTaskPanel modifyTaskPanel = new ModifyTaskPanel();
		
		Tab newTaskTab = new Tab("Nueva");
		newTaskTab.setPane(newTaskPanel);
		
		Tab editTaskTab = new Tab("Editar");
		editTaskTab.setPane(modifyTaskPanel);
		
		super.addChildPanels(newTaskPanel);
		super.addChildPanels(modifyTaskPanel);
		
		super.getTabPanel().setTabs(newTaskTab, editTaskTab);
		
		super.getTabPanel().selectTab(0);
		this.addChild(super.getTabPanel());
	}

}
