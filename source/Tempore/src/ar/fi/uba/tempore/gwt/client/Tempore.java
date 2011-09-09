package ar.fi.uba.tempore.gwt.client;

import ar.fi.uba.tempore.gwt.client.login.LoginPanel;
import ar.fi.uba.tempore.gwt.client.panel.ConteinerMainPanel;
import ar.fi.uba.tempore.gwt.client.panel.FooterPanel;
import ar.fi.uba.tempore.gwt.client.panel.menus.MainTabPanel;
import ar.fi.uba.tempore.gwt.client.panel.project.ProjectPanel;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Tempore implements EntryPoint {
	
	//private final DemoServiceAsync demoService = GWT.create(DemoService.class);

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {		
		
		loadLogin();
//		ConteinerMainPanel.getInstance().init();
		RootPanel.get().add(ConteinerMainPanel.getInstance());
		
	}

	private void loadLogin() {
		LoginPanel login = new LoginPanel();
	}
	
}
/* end Tempore class*/

