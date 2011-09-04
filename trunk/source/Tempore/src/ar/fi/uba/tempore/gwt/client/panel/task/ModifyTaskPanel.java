package ar.fi.uba.tempore.gwt.client.panel.task;

import ar.fi.uba.tempore.gwt.client.panel.menus.ContextChildPanel;

import com.google.gwt.user.client.ui.VerticalPanel;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;

public class ModifyTaskPanel extends Canvas implements ContextChildPanel{

	private VerticalPanel vPanel;
	
	public ModifyTaskPanel(){
		super();
		this.vPanel = new VerticalPanel();
	}

	@Override
	public void updateContent() {
		// TODO Auto-generated method stub
		this.vPanel.add(new Label("Modificar tareas"));
		this.addChild(this.vPanel);
	}
}
