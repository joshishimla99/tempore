package ar.fi.uba.tempore.gwt.client;

import ar.fi.uba.tempore.gwt.client.content.main.MainContent;
import ar.fi.uba.tempore.gwt.client.panel.FooterPanel;
import ar.fi.uba.tempore.gwt.client.panel.MainPanel;
import ar.fi.uba.tempore.gwt.client.panel.MenuPanel;
import ar.fi.uba.tempore.gwt.client.panel.MiddlePanel;
import ar.fi.uba.tempore.gwt.shared.Constant;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.layout.VLayout;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Tempore implements EntryPoint {
	
	//private final DemoServiceAsync demoService = GWT.create(DemoService.class);

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {		
		
		
		
		
		// Create a handler for the sendButton and nameField
		class FirstPageMenuHandler implements ClickHandler {
			private int contentId;
			private Panel contentPanel = null;
			
			public FirstPageMenuHandler(int pageId){
				this.contentId = pageId;
				this.contentPanel = RootPanel.get("content");
			}
			
			public void onClick(ClickEvent event) {
				//Limpiar content
				contentPanel.clear();
				
				//TODO Obtenemos HTML del content correspondiente
				switch (contentId){
				case Constant.CONTENT_MAIN:
					loadHTML("Principal.html");
					new MainContent().initialize();
					break;
				case Constant.CONTENT_PROJECT:
					loadHTML("Project.html");
					//TODO class
					break;
				case Constant.CONTENT_TASK:
					loadHTML("Task.html");
					//TODO class
					break;					
				case Constant.CONTENT_REPORT:
					loadHTML("Report.html");
					//TODO class
					break;
				case Constant.CONTENT_CONFIGURATION:
					loadHTML("Configuration.html");
					//TODO class
					break;
				case Constant.CONTENT_HELP:
					break;
				default:
					System.err.println("Content no contemplado");
					break;
				}
				
				//TODO Inicializar
				
				//
			}
				
			private void loadHTML (String htmlPath){
				RequestBuilder request = new RequestBuilder(RequestBuilder.GET, htmlPath);
				try {
					request.sendRequest(null, new RequestCallback(){
					public void onResponseReceived(Request request, Response response) {		
						HTML contentHTML = new HTML(response.getText());				
						contentPanel.add(contentHTML);
					}

					public void onError(Request request, Throwable exception) {
						//TODO Error
					}
					});
				} catch (RequestException e) {
					e.printStackTrace();
				}
			}
		
		}
		MiddlePanel middlePanel = new MiddlePanel();
		MenuPanel menuPanel = new MenuPanel();
		FooterPanel footerPanel = new FooterPanel();
		
		RootPanel.get("main").add(new VLayout());
		RootPanel.get("main").add(menuPanel);
		RootPanel.get("main").add(middlePanel);
		RootPanel.get("main").add(footerPanel);
				
		
	}
	
}
/* end Tempore class*/

