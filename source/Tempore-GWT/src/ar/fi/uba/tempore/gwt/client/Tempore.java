package ar.fi.uba.tempore.gwt.client;

import ar.fi.uba.tempore.gwt.shared.Constant;

import ar.fi.uba.tempore.gwt.client.content.main.MainContent;


import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DragAppearance;
import com.smartgwt.client.types.HeaderControls;
import com.smartgwt.client.types.LayoutPolicy;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.AnimationCallback;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HeaderControl;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.LayoutSpacer;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.layout.VStack;


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
		
		Command cmdPrincipal = new Command(){
			public void execute(){
				Window win = new Window();
				win.addItem(new Label("Principal"));
				win.show();
			}
		};
		
		Command cmdNuevoProyecto = new Command(){
			public void execute(){
				new Window().addItem(new Label("Nuevo Proyecto"));
			}
		};
		
		MenuBar filemenu = new MenuBar(true);
		
		filemenu.addItem("Nuevo", cmdNuevoProyecto);
		
		MenuBar menu = new MenuBar();
		
		
		menu.addItem("Principal", cmdPrincipal);		
		menu.addItem("Proyecto", filemenu);	
		menu.addItem("Tarea", filemenu);	
		menu.addItem("Reportes", filemenu);	
		menu.addItem("Configuracion", filemenu);	
		menu.addItem("Ayuda", filemenu);	
		RootPanel.get("MainMenu").add(menu);		

		
		RootPanel.get("main").add(new PanelControl());		
		
	}
	
}

class PanelControl extends VerticalPanel{
		private  String[] colors = new String[]{  
	        "FF6600", "808000", "008000", "008080", "0000FF", "666699",  
	        "FF0000", "FF9900", "99CC00", "339966", "33CCCC", "3366FF",  
	        "800080", "969696", "FF00FF", "FFCC00", "FFFF00", "00FF00",  
	        "00FFFF", "00CCFF", "993366", "C0C0C0", "FF99CC", "FFCC99",  
	        "FFFF99", "CCFFCC", "CCFFFF", "99CCFF", "CC99FF", "FFFFFF"  
	};  
	
	public PanelControl(){
			
		       final PortalLayout portalLayout = new PortalLayout(3);  
		        //portalLayout.setWidth100();  
		        //portalLayout.setHeight100();  
		       
		       portalLayout.setWidth("760px");
		       portalLayout.setHeight("580px");
		  
		        // create portlets...  
		        for (int i = 1; i <= 2; i++) {  
		            Portlet portlet = new Portlet();  
		            portlet.setTitle("Reporte");  
		  
		            Label label = new Label();  
		            label.setAlign(Alignment.CENTER);  
		            label.setLayoutAlign(VerticalAlignment.CENTER);  
		            label.setContents("Contenido del Reporte...");  
		            label.setBackgroundColor(colors[Random.nextInt(colors.length - 1)]);  
		            portlet.addItem(label);  
		            portalLayout.addPortlet(portlet);  
		        }  
		  
		        VLayout vLayout = new VLayout(15);  
		        vLayout.setMargin(10);  
		  
		        final DynamicForm form = new DynamicForm();  
		        form.setAutoWidth();  
		        form.setNumCols(5);  
		  
		        final StaticTextItem numColItem = new StaticTextItem();  
		        numColItem.setTitle("Columns");  
		        numColItem.setValue(portalLayout.getMembers().length);  
		  
		        ButtonItem addColumn = new ButtonItem("addColumn", "Add Column");  
		        addColumn.setIcon("silk/application_side_expand.png");  
		        addColumn.setAutoFit(true);  
		        addColumn.setStartRow(false);  
		        addColumn.setEndRow(false);  
			
		        addColumn.addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {  
		            public void onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {  
		                portalLayout.addMember(new PortalColumn());  
		                numColItem.setValue(portalLayout.getMembers().length);  
		  
		            }  
		        });
			
		        ButtonItem removeColumn = new ButtonItem("removeColumn", "Remove Column");  
		        removeColumn.setIcon("silk/application_side_contract.png");  
		        removeColumn.setAutoFit(true);  
		        removeColumn.setStartRow(false);  
		        removeColumn.setEndRow(false);  
		  
		  
		        removeColumn.addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {  
		            public void onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {  
		  
		                Canvas[] canvases = portalLayout.getMembers();  
		                int numMembers = canvases.length;  
		                if (numMembers > 0) {  
		                    Canvas lastMember = canvases[numMembers - 1];  
		                    portalLayout.removeMember(lastMember);  
		                    numColItem.setValue(numMembers - 1);  
		                }  
		  
		            }  
		        });  
		  
		        final ButtonItem addPortlet = new ButtonItem("addPortlet", "Add Portlet");  
		        addPortlet.setIcon("silk/application_view_tile.png");  
		        addPortlet.setAutoFit(true);  
		  
		        addPortlet.setStartRow(false);  
		        addPortlet.setEndRow(false);  
		        addPortlet.addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {  
		            public void onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {  
		  
		                final Portlet newPortlet = new Portlet();  
		                newPortlet.setTitle("Portlet ");  
		  
		                Label label = new Label();  
		                label.setAlign(Alignment.CENTER);  
		                label.setLayoutAlign(VerticalAlignment.CENTER);  
		                label.setContents("Portlet contents");  
		                label.setBackgroundColor(colors[Random.nextInt(colors.length - 1)]);  
		                newPortlet.addItem(label);  
		  
		                newPortlet.setVisible(false);  
		                PortalColumn column = portalLayout.addPortlet(newPortlet);  
		  
		                // also insert a blank spacer element, which will trigger the built-in  
		                //  animateMembers layout animation  
		                final LayoutSpacer placeHolder = new LayoutSpacer();  
		                placeHolder.setRect(newPortlet.getRect());  
		                column.addMember(placeHolder, 0); // add to top  
		  
		                // create an outline around the clicked button  
		                final Canvas outline = new Canvas();  
		                outline.setLeft(form.getAbsoluteLeft() + addPortlet.getLeft());  
		                outline.setTop(form.getAbsoluteTop());  
		                outline.setWidth(addPortlet.getWidth());  
		                outline.setHeight(addPortlet.getHeight());  
		                outline.setBorder("2px solid #8289A6");  
		                outline.draw();  
		                outline.bringToFront();  
		  
		                outline.animateRect(newPortlet.getPageLeft(), newPortlet.getPageTop(),  
		                        newPortlet.getVisibleWidth(), newPortlet.getViewportHeight(),  
		                        new AnimationCallback() {  
		                            public void execute(boolean earlyFinish) {  
		                                // callback at end of animation - destroy placeholder and outline; show the new portlet  
		                                placeHolder.destroy();  
		                                outline.destroy();  
		                                newPortlet.show();  
		                            }  
		                        }, 750);  
		            }  
		        });  
		  
		  
		        form.setItems(numColItem, addPortlet, addColumn, removeColumn);  
		  
		        vLayout.addMember(form);  
		        vLayout.addMember(portalLayout);  
		  
		        vLayout.draw();  
			
	        this.add(vLayout);
	        
	        //panel.show();   
			
	}
}

class Portlet extends Window {  
    public Portlet() {

        setShowShadow(false);

        // enable predefined component animation
        setAnimateMinimize(true);

        // Window is draggable with "outline" appearance by default.
        // "target" is the solid appearance.
        setDragAppearance(DragAppearance.OUTLINE);
        setCanDrop(true);

        // customize the appearance and order of the controls in the window header
        setHeaderControls(HeaderControls.MINIMIZE_BUTTON, HeaderControls.HEADER_LABEL, new HeaderControl(HeaderControl.SETTINGS), 
        				  new HeaderControl(HeaderControl.HELP), HeaderControls.CLOSE_BUTTON);

        // show either a shadow, or translucency, when dragging a portlet
        // (could do both at the same time, but these are not visually compatible effects)
        setShowDragShadow(true);
        setDragOpacity(30);

        // these settings enable the portlet to autosize its height only to fit its contents
        // (since width is determined from the containing layout, not the portlet contents)
        setVPolicy(LayoutPolicy.NONE);
        setOverflow(Overflow.VISIBLE);
    }
}  

/** 
 * PortalColumn class definition 
 */  
class PortalColumn extends VStack {
    public PortalColumn() {

        // espacio entre las ventanas
        setMembersMargin(6);

        // enable predefined component animation
        setAnimateMembers(true);
        setAnimateMemberTime(300);

        // enable drop handling
        setCanAcceptDrop(true);

        // change appearance of drag placeholder and drop indicator
        setDropLineThickness(4);

        Canvas dropLineProperties = new Canvas();
        dropLineProperties.setBackgroundColor("aqua");
        setDropLineProperties(dropLineProperties);

        setShowDragPlaceHolder(true);

        Canvas placeHolderProperties = new Canvas();
        placeHolderProperties.setBorder("1px solid #8289A6");
        setPlaceHolderProperties(placeHolderProperties);
    }
}

/** 
 * PortalLayout class definition 
 */  
class PortalLayout extends HLayout {  
	public PortalLayout(int numColumns) {  
        setMembersMargin(6);  
        for (int i = 0; i < numColumns; i++) {  
            addMember(new PortalColumn());  
        }  
    }  
    public PortalColumn addPortlet(Portlet portlet) {  
        // find the column with the fewest portlets  
        int fewestPortlets = Integer.MAX_VALUE;  
        PortalColumn fewestPortletsColumn = null;  
        for (int i = 0; i < getMembers().length; i++) {  
            int numPortlets = ((PortalColumn) getMember(i)).getMembers().length;  
            if (numPortlets < fewestPortlets) {  
                fewestPortlets = numPortlets;  
                fewestPortletsColumn = (PortalColumn) getMember(i);  
            }  
        }  
        fewestPortletsColumn.addMember(portlet);  
        return fewestPortletsColumn;  
    }  
}  