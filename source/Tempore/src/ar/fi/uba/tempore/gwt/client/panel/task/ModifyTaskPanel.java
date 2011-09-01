package ar.fi.uba.tempore.gwt.client.panel.task;

import ar.fi.uba.tempore.gwt.client.panel.menus.ContextChildPanel;

import com.google.gwt.user.client.ui.VerticalPanel;
import com.smartgwt.client.widgets.Label;

public class ModifyTaskPanel extends VerticalPanel implements ContextChildPanel{

	public ModifyTaskPanel(){
	}

	@Override
	public void UpdateContent() {
		// TODO Auto-generated method stub
		this.add(new Label("Modificar tareas"));
	}
}
