package ar.gwt.javaproject.stockwatcher.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.Command;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class StockWatcher implements EntryPoint {
	
	private VerticalPanel contextPanel;
	
	public void onModuleLoad() {
		RootPanel rootPanel = RootPanel.get();
		
		DockLayoutPanel dockLayoutPanel = new DockLayoutPanel(Unit.EM);
		rootPanel.add(dockLayoutPanel, 2, 10);
		dockLayoutPanel.setSize("100%", "100%");		
		
		FlowPanel headPanel = new FlowPanel();
		dockLayoutPanel.addNorth(headPanel, 7.5);
		headPanel.setSize("90%", "100");
		
		Image logo = new Image("images/logo_tempore.jpg");
		logo.setAltText("logo Tempore");
		headPanel.add(logo);
		logo.setWidth("722px");
		
		MenuBar menuBar = new MenuBar(false);
		headPanel.add(menuBar);
		
		MenuItem menuPrincipal = new MenuItem("Principal", false, new Command() {
			public void execute() {
				contextPanel.clear();
				
				PrincipalComposite content = new PrincipalComposite();
				contextPanel.add(content);
			}
		});
		menuBar.addItem(menuPrincipal);
		
		MenuItem menuProyecto = new MenuItem("Proyecto", false, new Command() {
			public void execute() {
				contextPanel.clear();
				
				ProyectoComposite content = new ProyectoComposite();
				contextPanel.add(content);
			}
		});
		menuBar.addItem(menuProyecto);
		
		MenuItem menuTarea = new MenuItem("Tarea", false, new Command() {
			public void execute() {
				contextPanel.clear();
				
				TareaComposite content = new TareaComposite();
				contextPanel.add(content);
			}
		});
		menuBar.addItem(menuTarea);
		
		MenuItem menuReportes = new MenuItem("Reportes", false, (Command) null);
		menuBar.addItem(menuReportes);
		
		MenuItem menuCongiguracion = new MenuItem("Configuraci\u00F3n", false, (Command) null);
		menuBar.addItem(menuCongiguracion);
		
		MenuItem menuAyuda = new MenuItem("Ayuda", false, (Command) null);
		menuBar.addItem(menuAyuda);
		
		VerticalPanel projectPanel = new VerticalPanel();
		projectPanel.setBorderWidth(1);
		dockLayoutPanel.addWest(projectPanel, 7.4);
		projectPanel.setSize("20%", "250");
		
		Tree tree = new Tree();
		projectPanel.add(tree);
		
		TreeItem trtmNewItem = new TreeItem("New item");
		tree.addItem(trtmNewItem);
		
		TreeItem trtmNewItem_3 = new TreeItem("New item");
		trtmNewItem.addItem(trtmNewItem_3);
		
		TreeItem trtmNewItem_4 = new TreeItem("New item");
		trtmNewItem.addItem(trtmNewItem_4);
		trtmNewItem.setState(true);
		
		TreeItem trtmNewItem_1 = new TreeItem("New item");
		tree.addItem(trtmNewItem_1);
		
		TreeItem trtmNewItem_5 = new TreeItem("New item");
		trtmNewItem_1.addItem(trtmNewItem_5);
		trtmNewItem_1.setState(true);
		
		TreeItem trtmNewItem_2 = new TreeItem("New item");
		tree.addItem(trtmNewItem_2);
		
		VerticalPanel verticalPanel = new VerticalPanel();
		dockLayoutPanel.add(verticalPanel);
		verticalPanel.setSize("80%", "100%");
		
		contextPanel = new VerticalPanel();
		verticalPanel.add(contextPanel);
		contextPanel.setSize("626px", "328px");		
	}
}
