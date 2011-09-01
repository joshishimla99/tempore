package ar.fi.uba.tempore.gwt.client;

import ar.fi.uba.tempore.gwt.client.panel.ContextPanel;
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
		
		DockPanel conteinerPanel = new DockPanel();
		//conteinerPanel.setSize("90%", "100%");
		// Paneles del dock panel: norte(MENU), oeste(PROYECTOS), centro (MIDDLE)
		FlowPanel headPanel = new FlowPanel();
		conteinerPanel.setStyleName("conteinerPanel");
		ProjectPanel projectPanel = new ProjectPanel();
		
		ContextPanel contextPanel = new ContextPanel(projectPanel);
		MainTabPanel mainTabPanel = new MainTabPanel(projectPanel);
		//MenuPanel menuPanel = new MenuPanel(contextPanel);
		FooterPanel footerPanel = new FooterPanel();
		
		conteinerPanel.add(headPanel, DockPanel.NORTH);
		conteinerPanel.add(projectPanel, DockPanel.WEST);
		conteinerPanel.add(footerPanel, DockPanel.SOUTH);
		conteinerPanel.add(mainTabPanel, DockPanel.CENTER);
		
		//conteinerPanel.setWidth("100%");
		RootPanel.get().add(conteinerPanel);
		
	}
	
}
/* end Tempore class*/

