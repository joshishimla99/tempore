package ar.fi.uba.tempore.gwt.client.panel;

import ar.fi.uba.tempore.gwt.client.panel.configuration.AlertConfigurationPanel;
import ar.fi.uba.tempore.gwt.client.panel.configuration.ClientConfigurationPanel;
import ar.fi.uba.tempore.gwt.client.panel.configuration.UserConfigurationPanel;
import ar.fi.uba.tempore.gwt.client.panel.menus.ContextChildPanel;
import ar.fi.uba.tempore.gwt.client.panel.project.NewProjectPanel;
import ar.fi.uba.tempore.gwt.client.panel.project.ProjectPanel;
import ar.fi.uba.tempore.gwt.client.panel.task.ModifyTaskPanel;
import ar.fi.uba.tempore.gwt.client.panel.task.NewTaskPanel;

import com.google.gwt.user.client.ui.DeckPanel;

/*
 * Este panel es el contenedor de Ppal, Tarea, proyectos, etc.
 * 
 */
public class ContextPanel extends DeckPanel{

	public ContextPanel(ProjectPanel projectPanel){
		this.add(new NewProjectPanel(projectPanel)); // widget 1
		//this.add(new ModifyProjectPanel(projectPanel)); // widget 2
		this.add(new NewTaskPanel(projectPanel)); // widget 3
		this.add(new ModifyTaskPanel()); // widget 4
		this.add(new MainReportPanel()); //widget 5
		this.add(new UserConfigurationPanel()); // widget 6
		this.add(new ClientConfigurationPanel()); //widget 7
		this.add(new AlertConfigurationPanel()); // widget 8
		this.showWidget(Constant.MENU_OPTION_NEW_PROJECT);
	}
	
	public void updateContextPanel(int index){
		if (index < this.getWidgetCount()){
			// Primero actualizo el contenido del panel
			((ContextChildPanel) this.getWidget(index)).UpdateContent();
			this.showWidget(index);
		}
	}
	
}