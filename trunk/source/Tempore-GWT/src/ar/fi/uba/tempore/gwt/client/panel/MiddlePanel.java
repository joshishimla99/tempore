package ar.fi.uba.tempore.gwt.client.panel;

import com.smartgwt.client.widgets.layout.HLayout;

public class MiddlePanel extends HLayout{
	
	public MiddlePanel(){
		this.addChild(new ProjectPanel());
		this.addChild(new MainPanel()) ;
	}

	//TODO: actualizar el panel ppal
	public void updateContextPanel(){
		
	}
}
