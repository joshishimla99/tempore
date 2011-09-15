package ar.fi.uba.tempore.gwt.client.panel.menus;

import ar.fi.uba.tempore.gwt.client.panel.configuration.AlertConfigurationPanel;
import ar.fi.uba.tempore.gwt.client.panel.configuration.ClientConfigurationPanel;
import ar.fi.uba.tempore.gwt.client.panel.configuration.UserConfigurationPanel;

import com.smartgwt.client.widgets.tab.Tab;

public class ConfigurationTabPanel extends SubTabPanel {
	
	public ConfigurationTabPanel(){
		super();
		
		ClientConfigurationPanel clientConfigurationPanel = new ClientConfigurationPanel();
		UserConfigurationPanel userConfigurationPanel = new UserConfigurationPanel();
		AlertConfigurationPanel alertConfigurationPanel = new AlertConfigurationPanel();
		
		Tab clientTab = new Tab("Clientes");
		clientTab.setPane(clientConfigurationPanel);
		
		Tab userTab = new Tab("Usuarios");
		userTab.setPane(userConfigurationPanel);
		
		Tab alertTab = new Tab("Alertas");
		alertTab.setPane(alertConfigurationPanel);
				
		this.getTabPanel().setTabs(clientTab, userTab, alertTab);
		
		this.addChildPanels(clientConfigurationPanel);
		this.addChildPanels(userConfigurationPanel);
		this.addChildPanels(alertConfigurationPanel);
				
		this.addChild(getTabPanel());
	}

}