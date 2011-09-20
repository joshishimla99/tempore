package ar.fi.uba.tempore.gwt.client.panel.configuration;

import ar.fi.uba.tempore.gwt.client.panel.TabsPanelContainer;

import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;

public class ConfigurationTabPanel extends TabsPanelContainer {
	
	public ConfigurationTabPanel(){
		super();
				
		TabSet subTabPanel = new TabSet();
		subTabPanel.setWidth100();
		subTabPanel.setHeight100();
		
		Tab clientTab = new Tab("Clientes");
		clientTab.setPane(new ClientConfigurationPanel());
		Tab userTab = new Tab("Usuarios");
		userTab.setPane(new UserConfigurationPanel());
		Tab alertTab = new Tab("Alertas");
		alertTab.setPane(new AlertConfigurationPanel());	

		subTabPanel.setTabs(userTab, clientTab, alertTab);
		this.addChild(subTabPanel);
	}
}
