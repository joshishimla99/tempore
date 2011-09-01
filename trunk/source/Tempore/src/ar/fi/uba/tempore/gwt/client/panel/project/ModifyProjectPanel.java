package ar.fi.uba.tempore.gwt.client.panel.project;

import ar.fi.uba.tempore.gwt.client.panel.menus.ContextChildPanel;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class ModifyProjectPanel extends Canvas implements ContextChildPanel {

	private ProjectPanel projectPanel;
	ProjectComponents projectComponents = null;
	private VerticalPanel vPanel;
	
	public ModifyProjectPanel(ProjectPanel projectPanel){
		super();
		this.vPanel = new VerticalPanel();
		this.projectPanel = projectPanel;
	}

	@Override
	public void UpdateContent() {
		if (this.projectComponents == null){
			projectComponents = new ProjectComponents();

			projectComponents.addProjectComponents("Editar Proyecto", new ClickHandler() {
				public void onClick(ClickEvent event) {
					projectComponents.getForm().validate(false);
					//TODO: actualizar la base de datos!!!!!!!!!!!
					ListGridRecord projectSelected = projectPanel.getProjectSelected();
					if (projectSelected != null){
						projectPanel.updateProjectTree();
					} else {
						//TODO: mostrar un mensaje de error
					}
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
