package ar.fi.uba.tempore.gwt.client.panel;

import com.smartgwt.client.widgets.Label;

public class HelpTabPanel extends SubTabPanel {
	
	public HelpTabPanel(){
		super();
		super.getTabPanel().add(new Label("Creado por Juan Pablo Gigante, Ludmila Lis Rinaudo, Nicolas Garcia - Version 1.0"), "Acerca de");
		super.getTabPanel().selectTab(0);
		this.addChild(super.getTabPanel());
	}

}
