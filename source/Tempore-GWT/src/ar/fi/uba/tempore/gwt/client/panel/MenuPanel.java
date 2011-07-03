package ar.fi.uba.tempore.gwt.client.panel;

import ar.fi.uba.tempore.gwt.client.MenuService;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuBar;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.layout.HLayout;

public class MenuPanel extends HLayout{

	public MenuPanel(){
		MenuBar filemenu = new MenuBar(true);
		
		Command cmdPrincipal = new Command(){
			public void execute(){
//				Window win = new Window();
//				win.addItem(new Label("Principal"));
//				win.show();
				MainPanel mainPanel = new MainPanel();
				MenuService.getInstance().setNewContextPanel(mainPanel);
				
			}
		};
		
		Command cmdNuevoProyecto = new Command(){
			public void execute(){
//				ProjectMainPanel projectMainPanel = new ProjectMainPanel();
//				MenuService.getInstance().setNewContextPanel(projectMainPanel);
//				new Window().addItem(new Label("Nuevo Proyecto"));
				
			}
		};
		
		filemenu.addItem("Nuevo", cmdNuevoProyecto);
		
		MenuBar menu = new MenuBar();
		
		
		menu.addItem("Principal", cmdPrincipal);		
		menu.addItem("Proyecto", filemenu);	
		menu.addItem("Tarea", filemenu);	
		menu.addItem("Reportes", filemenu);	
		menu.addItem("Configuracion", filemenu);	
		menu.addItem("Ayuda", filemenu);	
		this.addMember(menu);
	}
	
}
