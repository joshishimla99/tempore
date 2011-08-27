package ar.fi.uba.tempore.gwt.client.panel.task;

import ar.fi.uba.tempore.gwt.client.panel.ContextChildPanel;

import com.google.gwt.user.client.ui.VerticalPanel;
import com.smartgwt.client.widgets.Label;

public class NewTaskPanel extends VerticalPanel implements ContextChildPanel{
	
	public NewTaskPanel(){
		
	}

	@Override
	public void UpdateContent() {
		// TODO Auto-generated method stub
		this.add(new Label("Crear nueva tarea"));
	}

}
