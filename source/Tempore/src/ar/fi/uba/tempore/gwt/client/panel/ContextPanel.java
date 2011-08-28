package ar.fi.uba.tempore.gwt.client.panel;

import ar.fi.uba.tempore.gwt.client.panel.configuration.AlertConfigurationPanel;
import ar.fi.uba.tempore.gwt.client.panel.configuration.ClientConfigurationPanel;
import ar.fi.uba.tempore.gwt.client.panel.configuration.UserConfigurationPanel;
import ar.fi.uba.tempore.gwt.client.panel.project.ModifyProjectPanel;
import ar.fi.uba.tempore.gwt.client.panel.project.NewProjectPanel;
import ar.fi.uba.tempore.gwt.client.panel.task.ModifyTaskPanel;
import ar.fi.uba.tempore.gwt.client.panel.task.NewTaskPanel;

import com.google.gwt.user.client.ui.DeckPanel;

/*
 * Este panel es el contenedor de Ppal, Tarea, proyectos, etc.
 * 
 */
public class ContextPanel extends DeckPanel{

	public ContextPanel(){
		this.add(new MainPanel()); // widget 0
		this.add(new NewProjectPanel()); // widget 1
		this.add(new ModifyProjectPanel()); // widget 2
		this.add(new NewTaskPanel()); // widget 3
		this.add(new ModifyTaskPanel()); // widget 4
		this.add(new MainReportPanel()); //widget 5
		this.add(new UserConfigurationPanel()); // widget 6
		this.add(new ClientConfigurationPanel()); //widget 7
		this.add(new AlertConfigurationPanel()); // widget 8
		this.showWidget(Constant.MENU_OPTION_MAIN);
	}
	
	public void updateContextPanel(int index){
		if (index < this.getWidgetCount()){
			// Primero actualizo el contenido del panel
			((ContextChildPanel) this.getWidget(index)).UpdateContent();
			this.showWidget(index);
		}
	}
	
}
