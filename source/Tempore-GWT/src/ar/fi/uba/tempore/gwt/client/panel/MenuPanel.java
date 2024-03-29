package ar.fi.uba.tempore.gwt.client.panel;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;

public class MenuPanel extends MenuBar {

	ContextPanel contexPanel;
	
	public MenuPanel(ContextPanel contextPanel) {
		super(false);
		this.addStyleName("itemMenu");
		this.setAutoOpen(true);
		this.setAnimationEnabled(true);
		this.contexPanel = contextPanel;
		
		// menuProject
		MenuBar menuProject = new MenuBar(true);
		menuProject.addStyleName("itemMenu");
		menuProject.setAnimationEnabled(true);
		menuProject.isFocusOnHoverEnabled();
		
		MenuItem itemProject = new MenuItem("Proyectos", false, menuProject);
		MenuItem itemNewProject = new MenuItem("Nuevo", createCmd(Constant.MENU_OPTION_NEW_PROJECT));
		menuProject.addItem(itemNewProject);
		menuProject.addItem(new MenuItem("Modificar", createCmd(Constant.MENU_OPTION_MODIFY_PROJECT)));
		
		// MenuConfiguration
		MenuBar menuConfiguration = new MenuBar(true);
		menuConfiguration.addStyleName("itemMenu");
		menuConfiguration.setAnimationEnabled(true);
		MenuItem itemConfiguration = new MenuItem("Configuracion", false, menuConfiguration);
		menuConfiguration.addItem(new MenuItem("Usuarios", createCmd(Constant.MENU_OPTION_USER_CONFIGURATION)));
		menuConfiguration.addItem(new MenuItem("Clientes", createCmd(Constant.MENU_OPTION_CLIENT_CONFIGURATION)));
		
		// MenuTask
		MenuBar menuTask = new MenuBar(true);
		menuTask.addStyleName("itemMenu");
		menuTask.setAnimationEnabled(true);
		MenuItem itemTask = new MenuItem("Tareas", false, menuTask);
		menuTask.addItem(new MenuItem("Nueva", createCmd(Constant.MENU_OPTION_NEW_TASK)));
		menuTask.addItem(new MenuItem("Modificar", createCmd(Constant.MENU_OPTION_MODIFY_TASK)));

		// MenuReport
		MenuBar menuReport = new MenuBar(true);
		menuReport.addStyleName("itemMenu");
		menuReport.setAnimationEnabled(true);
		MenuItem itemReport = new MenuItem("Reportes", false, menuReport);
		menuReport.addItem(new MenuItem("Consultar", createCmd(Constant.MENU_OPTION_MAIN_REPORT)));

		// MenuHelp
		MenuBar menuHelp = new MenuBar(true);
		menuHelp.addStyleName("itemMenu");
		menuHelp.setAnimationEnabled(true);
		MenuItem itemHelp = new MenuItem("Ayuda", false, menuHelp);

		// MenuPpal
		this.addItem(itemProject);
		this.addItem(itemConfiguration);
		this.addItem(itemTask);
		this.addItem(itemReport);
		this.addItem(itemHelp);
		
		

	}

	private Command createCmd(final int menuOption) {
		Command cmd = new Command() {
			public void execute(){
				contexPanel.updateContextPanel(menuOption);
			}
		};
		return cmd;
	}

}
