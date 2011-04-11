package ar.tempore.client;

import ar.tempore.client.content.main.MainContent;
import ar.tempore.shared.Constant;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
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
		
		Label mainLabel = new Label("Principal");
		RootPanel.get("MainMenu").add(mainLabel);		
		mainLabel.addClickHandler(new FirstPageMenuHandler(Constant.CONTENT_MAIN));
		
		Label projectLabel = new Label("Proyecto");
		RootPanel.get("ProjectMenu").add(projectLabel);		
		projectLabel.addClickHandler(new FirstPageMenuHandler(Constant.CONTENT_PROJECT));
		
		Label taskLabel = new Label("Tarea");
		RootPanel.get("TaskMenu").add(taskLabel);		
		taskLabel.addClickHandler(new FirstPageMenuHandler(Constant.CONTENT_TASK));
		
		Label reportLabel = new Label("Reporte");
		RootPanel.get("ReportMenu").add(reportLabel);		
		reportLabel.addClickHandler(new FirstPageMenuHandler(Constant.CONTENT_REPORT));

		Label configurationLabel = new Label("Configuracion");
		RootPanel.get("ConfigurationMenu").add(configurationLabel);		
		configurationLabel.addClickHandler(new FirstPageMenuHandler(Constant.CONTENT_CONFIGURATION));
		
		Label helpLabel = new Label("Ayuda");
		RootPanel.get("HelpMenu").add(helpLabel);		
		helpLabel.addClickHandler(new FirstPageMenuHandler(Constant.CONTENT_HELP));
	}
	
}
