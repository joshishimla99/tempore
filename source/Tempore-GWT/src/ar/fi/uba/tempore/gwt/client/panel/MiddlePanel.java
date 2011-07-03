package ar.fi.uba.tempore.gwt.client.panel;

import com.smartgwt.client.widgets.layout.HLayout;

public class MiddlePanel extends HLayout{
	
	public MiddlePanel(){
	    this.addMember(new ProjectPanel());
	    this.addMember(new ContextPanel());
	}

	//TODO: actualizar el panel ppal
	public void updateContextPanel(){
		
	}
}
