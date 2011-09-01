package ar.fi.uba.tempore.gwt.client.panel.menus;

import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.tab.Tab;

public class HelpTabPanel extends SubTabPanel {
	
	public HelpTabPanel(){
		super();
		Tab aboutTab = new Tab("Acerca de");
		aboutTab.setPane(new Label("Creado por Juan Pablo Gigante, Ludmila Lis Rinaudo, Nicolas Garcia - Version 1.0"));
		
		super.getTabPanel().setTabs(aboutTab);
		super.getTabPanel().selectTab(0);
		
		this.addChild(super.getTabPanel());
	}

}
