package ar.fi.uba.tempore.gwt.client.panel.task;

import ar.fi.uba.tempore.gwt.client.panel.TabsPanelContainer;

import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;

public class TaskTabPanel extends TabsPanelContainer {
	
	public TaskTabPanel(){
		super();
		TabSet subTabPanel = new TabSet();
		subTabPanel.setWidth100();
		subTabPanel.setHeight100();

		Tab newTaskTab = new Tab("Nueva");
		newTaskTab.setPane(new NewTaskPanel());
		
		Tab editTaskTab = new Tab("Editar");
		editTaskTab.setPane(new ModifyTaskPanel());
		
		subTabPanel.setTabs(newTaskTab , editTaskTab);
		
		this.addChild(subTabPanel);
	}

}
