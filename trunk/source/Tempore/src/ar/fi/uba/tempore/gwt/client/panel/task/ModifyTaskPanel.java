package ar.fi.uba.tempore.gwt.client.panel.task;

import ar.fi.uba.tempore.gwt.client.panel.menus.ContextChildPanel;

import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.VLayout;

public class ModifyTaskPanel extends Canvas implements ContextChildPanel{

	private VLayout vPanel;
	
	public ModifyTaskPanel(){
		super();
		this.vPanel = new VLayout();
	}

	@Override
	public void updateContent() {
		// TODO Auto-generated method stub
		this.vPanel.addChild(new Label("Modificar tareas"));
		this.addChild(this.vPanel);
	}
}
