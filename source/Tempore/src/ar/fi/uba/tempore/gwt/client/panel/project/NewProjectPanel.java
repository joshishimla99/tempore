package ar.fi.uba.tempore.gwt.client.panel.project;

import ar.fi.uba.tempore.gwt.client.panel.ContextChildPanel;

import com.google.gwt.user.client.ui.VerticalPanel;
import com.smartgwt.client.widgets.Label;

public class NewProjectPanel extends VerticalPanel implements ContextChildPanel {
	
	public NewProjectPanel(){
	}

	@Override
	public void UpdateContent() {
		// TODO Auto-generated method stub
		this.add(new Label("Crear nuevo projecto"));
	}

}
