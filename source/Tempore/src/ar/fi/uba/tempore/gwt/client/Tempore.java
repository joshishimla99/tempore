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
	
	//private final DemoServiceAsync demoService = GWT.create(DemoService.class);

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {		
		
		
		
		
//		// Create a handler for the sendButton and nameField
//		class FirstPageMenuHandler implements ClickHandler {
//			private int contentId;
//			private Panel contentPanel = null;
//			
//			public FirstPageMenuHandler(int pageId){
//				this.contentId = pageId;
//				this.contentPanel = RootPanel.get("content");
//			}
//			
//			public void onClick(ClickEvent event) {
//				//Limpiar content
//				contentPanel.clear();
//				
//				//TODO Obtenemos HTML del content correspondiente
//				switch (contentId){
//				case Constant.CONTENT_MAIN:
//					loadHTML("Principal.html");
//					new MainContent().initialize();
//					break;
//				case Constant.CONTENT_PROJECT:
//					loadHTML("Project.html");
//					//TODO class
//					break;
//				case Constant.CONTENT_TASK:
//					loadHTML("Task.html");
//					//TODO class
//					break;					
//				case Constant.CONTENT_REPORT:
//					loadHTML("Report.html");
//					//TODO class
//					break;
//				case Constant.CONTENT_CONFIGURATION:
//					loadHTML("Configuration.html");
//					//TODO class
//					break;
//				case Constant.CONTENT_HELP:
//					break;
//				default:
//					System.err.println("Content no contemplado");
//					break;
//				}
//				
//			}
//				
//			private void loadHTML (String htmlPath){
//				RequestBuilder request = new RequestBuilder(RequestBuilder.GET, htmlPath);
//				try {
//					request.sendRequest(null, new RequestCallback(){
//					public void onResponseReceived(Request request, Response response) {		
//						HTML contentHTML = new HTML(response.getText());				
//						contentPanel.add(contentHTML);
//					}
//
//					public void onError(Request request, Throwable exception) {
//						//TODO Error
//					}
//					});
//				} catch (RequestException e) {
//					e.printStackTrace();
//				}
//			}
//		
//		}
		DockPanel conteinerPanel = new DockPanel();
		conteinerPanel.setSize("90%", "100%");
		// Paneles del dock panel: norte(MENU), oeste(PROYECTOS), centro (MIDDLE)
		FlowPanel headPanel = new FlowPanel();
		conteinerPanel.setStyleName("conteinerPanel");
		
		ContextPanel contextPanel = new ContextPanel();
		ProjectPanel projectPanel = new ProjectPanel();
		//projectPanel.setSize("20%", "250");
		MenuPanel menuPanel = new MenuPanel(contextPanel);
		FooterPanel footerPanel = new FooterPanel();
		
		headPanel.add(menuPanel);
		
		conteinerPanel.add(headPanel, DockPanel.NORTH);
		conteinerPanel.add(projectPanel, DockPanel.WEST);
		conteinerPanel.add(footerPanel, DockPanel.SOUTH);
		conteinerPanel.add(contextPanel, DockPanel.CENTER);
		
		//conteinerPanel.setWidth("100%");
		RootPanel.get().add(conteinerPanel);
		
	}
	
}
/* end Tempore class*/

