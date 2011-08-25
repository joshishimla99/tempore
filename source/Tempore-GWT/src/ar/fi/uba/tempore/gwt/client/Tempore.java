package ar.fi.uba.tempore.gwt.client;

import ar.fi.uba.tempore.gwt.client.panel.ContextPanel;
import ar.fi.uba.tempore.gwt.client.panel.FooterPanel;
import ar.fi.uba.tempore.gwt.client.panel.MenuPanel;
import ar.fi.uba.tempore.gwt.client.panel.project.ProjectPanel;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Tempore implements EntryPoint {
	

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {		
		
		DockPanel conteinerPanel = new DockPanel();
		conteinerPanel.setSize("90%", "100%");
		conteinerPanel.setSpacing(15);
		conteinerPanel.setStyleName("conteinerPanel");
		
		// Paneles del dock panel: norte(MENU), oeste(PROYECTOS), centro (MIDDLE)
		FlowPanel headPanel = new FlowPanel();
		
		ContextPanel contextPanel = new ContextPanel();
		contextPanel.addStyleName("contextPanel");
		ProjectPanel projectPanel = new ProjectPanel();
		MenuPanel menuPanel = new MenuPanel(contextPanel);
		FooterPanel footerPanel = new FooterPanel();
		
		headPanel.add(menuPanel);
		
		conteinerPanel.add(headPanel, DockPanel.NORTH);
		conteinerPanel.add(projectPanel, DockPanel.WEST);
		conteinerPanel.add(footerPanel, DockPanel.SOUTH);
		conteinerPanel.add(contextPanel, DockPanel.CENTER);
		
		RootPanel.get().add(conteinerPanel);
		
	}
	
}
/* end Tempore class*/

