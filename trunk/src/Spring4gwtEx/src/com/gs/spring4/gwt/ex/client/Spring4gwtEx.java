package com.gs.spring4.gwt.ex.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.gs.spring4.gwt.ex.client.component.MessagePanel;
import com.gs.spring4.gwt.ex.shared.FieldVerifier;

/**
 * Entry point classes define onModuleLoad().
*/
public class Spring4gwtEx implements EntryPoint {
 /**
  * The message displayed to the user when the server cannot be reached or
  * returns an error.
  */
 private static final String SERVER_ERROR = "An error occurred while "
   + "attempting to contact the server. Please check your network "
   + "connection and try again.";

 /**
  * Create a remote service proxy to talk to the server-side Greeting service.
  */
 private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);
 
 /**
  * This is the entry point method.
  */
 public void onModuleLoad() {
  final Button sendButton = new Button("Send");
  final TextBox nameField = new TextBox();
  nameField.setText("GWT User");
  final TextBox passwordField = new TextBox();
  passwordField.setText("Password");

  // We can add style names to widgets
  sendButton.addStyleName("sendButton");

  // Add the nameField and sendButton to the RootPanel
  // Use RootPanel.get() to get the entire body element
  RootPanel.get("nameFieldContainer").add(nameField);
  RootPanel.get("passwordFieldContainer").add(passwordField);
  RootPanel.get("sendButtonContainer").add(sendButton);

  // Focus the cursor on the name field when the app loads
  nameField.setFocus(true);
  nameField.selectAll();

  // Create the popup dialog box
  final DialogBox dialogBox = new DialogBox();
  dialogBox.setText("Remote Procedure Call");
  dialogBox.setAnimationEnabled(true);
  
  final Button closeButton = new Button("Cerrar");
  final MessagePanel dialogVPanel = new MessagePanel("dialogVPanel", "", "", closeButton);
  dialogBox.setWidget(dialogVPanel);
  
  // Add a handler to close the DialogBox
  closeButton.addClickHandler(new ClickHandler() {
	   public void onClick(ClickEvent event) {
	    dialogBox.hide();
	    sendButton.setEnabled(true);
	    sendButton.setFocus(true);
	   }
	  });

  ServiceDefTarget endpoint = (ServiceDefTarget) greetingService;
  endpoint.setServiceEntryPoint("../../springGwtServices/greetingService");
  

  // Create a handler for the sendButton and nameField
  class MyHandler implements ClickHandler, KeyUpHandler {
   /**
    * Fired when the user clicks on the sendButton.
    */
   public void onClick(ClickEvent event) {
	   if (validInformation()){
		   sendInformationToServer();   
	   }
   }

   private boolean validInformation() {
	   if (FieldVerifier.isValidName(nameField.getText())){
		   if (FieldVerifier.isValidPassword(passwordField.getText())){
			   return true;
		   }
	   }
	   dialogBox.setText("Error en los datos ingresados");
	   dialogVPanel.setMessage("Los datos ingresados no son v&aacute;lidos. Verifique que el usuario y la clave no sean vacios. La clave debe ser mayor a 3 d&iacute;gitos.");
	   dialogVPanel.setStyleName("serverResponseLabelError");
       dialogBox.center();
       closeButton.setFocus(true);
	   return false;
   }

/**
    * Fired when the user types in the nameField.
    */
   public void onKeyUp(KeyUpEvent event) {
	   if (validInformation()){
		   if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
			     sendInformationToServer();
			   }
	   }
	   
   }

   /**
    * Send the name from the nameField to the server and wait for a response.
    */
   private void sendInformationToServer() {
    sendButton.setEnabled(false);
    String textToServer = nameField.getText();
    dialogVPanel.setMessage(textToServer);
    greetingService.greet(textToServer, passwordField.getText(),
      new AsyncCallback() {
       public void onFailure(Throwable caught) {
        // Show the RPC error message to the user
        dialogBox.setText("Remote Procedure Call - Failure");
        dialogVPanel.setStyleName("serverResponseLabelError");
        dialogVPanel.setMessage(SERVER_ERROR);
        dialogBox.center();
        closeButton.setFocus(true);
       }

       public void onSuccess(String result) {
        dialogBox.setText("Remote Procedure Call");

        dialogVPanel.removeStyle("serverResponseLabelError");
        dialogVPanel.setMessage(result);
        dialogBox.center();
        closeButton.setFocus(true);
       }

	@Override
	public void onSuccess(Object result) {
		String resul = (String) result;
		onSuccess(resul);
		
	}
      });
   }
  }

  // Add a handler to send the name to the server
  MyHandler handler = new MyHandler();
  sendButton.addClickHandler(handler);
  nameField.addKeyUpHandler(handler);
 }
}