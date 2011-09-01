package ar.fi.uba.tempore.gwt.client.panel.menus;

import ar.fi.uba.tempore.gwt.client.panel.configuration.AlertConfigurationPanel;
import ar.fi.uba.tempore.gwt.client.panel.configuration.ClientConfigurationPanel;
import ar.fi.uba.tempore.gwt.client.panel.configuration.UserConfigurationPanel;

public class ConfigurationTabPanel extends SubTabPanel {
	
	public ConfigurationTabPanel(){
		super();
		ClientConfigurationPanel clientConfigurationPanel = new ClientConfigurationPanel();
		UserConfigurationPanel userConfigurationPanel = new UserConfigurationPanel();
		AlertConfigurationPanel alertConfigurationPanel = new AlertConfigurationPanel();
		
		super.getTabPanel().add(clientConfigurationPanel, "Clientes");
		super.getTabPanel().add(userConfigurationPanel, "Usuarios");
		super.getTabPanel().add(alertConfigurationPanel, "Alertas");
		
		super.addChildPanels(clientConfigurationPanel);
		super.addChildPanels(userConfigurationPanel);
		super.addChildPanels(alertConfigurationPanel);
		
		super.getTabPanel().selectTab(0);
		this.addChild(super.getTabPanel());
	}

}
