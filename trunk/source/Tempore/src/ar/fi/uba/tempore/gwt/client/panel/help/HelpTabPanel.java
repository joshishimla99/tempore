package ar.fi.uba.tempore.gwt.client.panel.help;

import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.VLayout;

public class HelpTabPanel extends Canvas {
	
	public HelpTabPanel(){
		super();
		VLayout vLayout = new VLayout();
		
		vLayout.addMember(new Label("Creado por Juan Pablo Gigante, Ludmila Lis Rinaudo, Nicolas Garcia - Version 1.0"));
		
	}

}
