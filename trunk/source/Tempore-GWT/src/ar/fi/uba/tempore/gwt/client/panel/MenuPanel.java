package ar.fi.uba.tempore.gwt.client.panel;

import ar.fi.uba.tempore.gwt.client.MenuService;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuBar;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.layout.HLayout;

public class MenuPanel extends HLayout{

	public MenuPanel(){
		
		Command cmdPrincipal = new Command(){
			public void execute(){
//				Window win = new Window();
//				win.addItem(new Label("Principal"));
//				win.show();
				MainPanel mainPanel = new MainPanel();
				MenuService.getInstance().setNewContextPanel(mainPanel);
				
			}
		};
		
		MenuBar menu = new MenuBar(false);
		
		menu.addItem("Principal", cmdPrincipal);		
		menu.addItem("Proyecto", createProjectMenu());	
		menu.addItem("Tarea", createTaskMenu());	
		menu.addItem("Reportes", createReportMenu());	
		menu.addItem("Configuracion", createConfigurationMenu());	
		menu.addItem("Ayuda", createHelpMenu());	
		this.addMember(menu);
	}

	private MenuBar createHelpMenu() {
		
		return null;
	}

	private MenuBar createConfigurationMenu() {
		// TODO Auto-generated method stub
		return null;
	}

	private MenuBar createReportMenu() {
		// TODO Auto-generated method stub
		return null;
	}

	private MenuBar createTaskMenu() {
		Command cmdNewTask = new Command(){
			public void execute(){
//				ProjectMainPanel projectMainPanel = new ProjectMainPanel();
//				MenuService.getInstance().setNewContextPanel(projectMainPanel);
				new Window().addItem(new Label("Nueva Tarea"));
				
			}
		};
		
		Command cmdModifyTask = new Command(){
			public void execute(){
//				ProjectMainPanel projectMainPanel = new ProjectMainPanel();
//				MenuService.getInstance().setNewContextPanel(projectMainPanel);
				new Window().addItem(new Label("Modificar Tarea"));
				
			}
		};
		
		MenuBar fileMenu = new MenuBar(true);
		fileMenu.addItem("Nueva", cmdNewTask);
		fileMenu.addItem("Modificar", cmdModifyTask);
		return fileMenu;
	}

	private MenuBar createProjectMenu() {
		Command cmdNewProject = new Command(){
			public void execute(){
//				ProjectMainPanel projectMainPanel = new ProjectMainPanel();
//				MenuService.getInstance().setNewContextPanel(projectMainPanel);
				new Window().addItem(new Label("Nuevo Proyecto"));
				
			}
		};
		
		Command cmdModifyProject = new Command(){
			public void execute(){
//				ProjectMainPanel projectMainPanel = new ProjectMainPanel();
//				MenuService.getInstance().setNewContextPanel(projectMainPanel);
				new Window().addItem(new Label("Modificar Proyecto"));
				
			}
		};
		
		MenuBar fileMenu = new MenuBar(true);
		fileMenu.addItem("Nuevo", cmdNewProject);
		fileMenu.addItem("Modificar", cmdModifyProject);
		return fileMenu;
	}
	
}
