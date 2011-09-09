package ar.fi.uba.tempore.gwt.client.panel;

import ar.fi.uba.tempore.gwt.client.panel.menus.MainTabPanel;
import ar.fi.uba.tempore.gwt.client.panel.project.ProjectPanel;

import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlowPanel;

/**
 * Clase principal que contiene al panel de login y ademas al conteneder de la aplicacion, una vez logeado el usuario
 * @author Ludmila
 *
 */
public class ConteinerMainPanel extends DockPanel {

	static private ConteinerMainPanel instance = null;
	
	private ConteinerMainPanel(){}
	
	static public ConteinerMainPanel getInstance() {

		if (instance == null) {
		instance = new ConteinerMainPanel();
		}
		return instance;
	}
	
	public void init(){
		
		//conteinerPanel.setSize("90%", "100%");
		// Paneles del dock panel: norte(MENU), oeste(PROYECTOS), centro (MIDDLE)
		FlowPanel headPanel = new FlowPanel();
		this.setStyleName("conteinerPanel");
		ProjectPanel.getInstance().init();
		
		MainTabPanel mainTabPanel = new MainTabPanel();
		FooterPanel footerPanel = new FooterPanel();
		
		this.add(headPanel, DockPanel.NORTH);
		this.add(ProjectPanel.getInstance(), DockPanel.WEST);
		this.add(footerPanel, DockPanel.SOUTH);
		this.add(mainTabPanel, DockPanel.CENTER);
		
	}
}
