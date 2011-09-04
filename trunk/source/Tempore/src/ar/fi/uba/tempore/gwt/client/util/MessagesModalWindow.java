package ar.fi.uba.tempore.gwt.client.util;

import com.google.gwt.user.client.ui.VerticalPanel;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.events.CloseClientEvent;
/**
 * Modal generica para mostrar el messageToDisplay
 * @author Ludmila
 *
 */
public class MessagesModalWindow {
	
	private final Window winModal;
	
	public MessagesModalWindow(String modalTitle, String messageToDisplay){
		winModal = new Window();  
        winModal.setWidth(360);  
        winModal.setHeight(115);  
        winModal.setTitle(modalTitle);  
        winModal.setShowMinimizeButton(false);  
        winModal.setIsModal(true);  
        winModal.setShowModalMask(true);  
        winModal.centerInPage();  
        winModal.setShowEdges(true);
        
        winModal.addCloseClickHandler(new CloseClickHandler() {  
        	
        	@Override
            public void onCloseClick(CloseClientEvent event) {  
                winModal.destroy();  
            }  
        }); 
        VerticalPanel vPanel = new VerticalPanel();
        vPanel.setWidth("150 px");
        IButton acceptButton = new IButton(" Aceptar "); 
        acceptButton.setAutoFit(true);  
        acceptButton.addClickHandler(new ClickHandler() {  
        	@Override
            public void onClick(ClickEvent event) {  
            		winModal.destroy();  
            }  
        });  
        Label messageLabel = new Label(messageToDisplay);
        messageLabel.setAlign(Alignment.CENTER);
        vPanel.add(messageLabel);
        winModal.addItem(vPanel);  
        winModal.addItem(acceptButton);
	}
	
	public void display(){
		winModal.show(); 
	}

}
