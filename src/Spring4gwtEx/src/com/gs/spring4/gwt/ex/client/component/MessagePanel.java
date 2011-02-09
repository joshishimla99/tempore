package com.gs.spring4.gwt.ex.client.component;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

/*
 * Este panel contiene mensajes que la aplicacion despliega como ventanas emergentes 
 * para informar al usuario 
 */

public class MessagePanel extends VerticalPanel{
	final Label message;
	
	public MessagePanel(){
		super();
		this.message = new Label();
		super.add(this.message);
	}
	
	public MessagePanel(String styleName, String messageTitle, String messageBody, Button closeButton){
		super();
		this.message = new Label(messageBody);
		super.add(new HTML(messageTitle));
		super.add(message);
		super.add(new HTML("Server replies:"));
		super.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		super.add(closeButton);
		this.setStyleName(styleName);
		// We can set the id of a widget by accessing its Element
		closeButton.getElement().setId("closeButton");
	}

	public void setStyle(String styleName){
		super.addStyleName(styleName);
	}
	
	public void removeStyle(String styleName){
		this.message.removeStyleName(styleName);
	}
	
	public void setMessageTitle(String messageTitle){
		super.add(new HTML(messageTitle));
	}
	
	public void setMessage(String messageBody){
		this.message.setText(messageBody);
	}
}
