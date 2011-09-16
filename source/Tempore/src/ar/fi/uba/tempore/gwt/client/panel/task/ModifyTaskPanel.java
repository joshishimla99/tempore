package ar.fi.uba.tempore.gwt.client.panel.task;


import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.VLayout;

public class ModifyTaskPanel extends Canvas{

	public ModifyTaskPanel(){
		super();
		updateContent();
	}

	public void updateContent() {
		final VLayout vPanel = new VLayout();

		vPanel.addChild(new Label("Modificar tareas"));
		this.addChild(vPanel);
	}
}
