package ar.fi.uba.tempore.gwt.client.panel.project;

import ar.fi.uba.tempore.gwt.client.panel.menus.ContextChildPanel;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;

public class NewProjectPanel extends Canvas implements ContextChildPanel {

	private ProjectPanel projectPanel;
	ProjectComponents projectComponents = null;
	VerticalPanel vPanel;
	
	public NewProjectPanel(ProjectPanel projectPanel) {
		super();
		this.projectPanel = projectPanel;
		this.vPanel = new VerticalPanel();
		this.UpdateContent();
	}

	@Override
	public void UpdateContent() {
		if (this.projectComponents == null){
			projectComponents = new ProjectComponents();
			projectComponents.addProjectComponents("Crear Proyectos", new ClickHandler() {
				public void onClick(ClickEvent event) {
					projectComponents.getForm().validate(false);
					//TODO: actualizar la base de datos!!!!!!!!!!!
					projectPanel.updateProjectTree();
				}
			});
		}
		else {
			projectComponents.clearProjectComponents();
		}
		
		
		this.vPanel.add(this.projectComponents.getForm());
		this.addChild(vPanel);
		this.projectComponents.getForm().draw();
		
	}

}
