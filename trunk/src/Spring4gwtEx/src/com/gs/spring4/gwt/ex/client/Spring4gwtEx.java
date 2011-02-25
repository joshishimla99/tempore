	package com.gs.spring4.gwt.ex.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;
import com.gs.spring4.gwt.ex.client.pages.LoginPage;

/**
 * Entry point classes define onModuleLoad().
*/
public class Spring4gwtEx implements EntryPoint {

 /**
  * Create a remote service proxy to talk to the server-side Greeting service.
  */
 private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);
 
 private static Spring4gwtEx singleton;
 
 public static Spring4gwtEx get() {
	return singleton;
 }
 
 private void setLoginScreen()	{
     //Create the Login screen
     LoginPage scrLogin=new LoginPage();
     //Attach it to the root panel
     RootPanel.get().add(scrLogin);
 }

 public void onModuleLoad() {
	 
	 singleton=this;
     setLoginScreen();
 }
 
 public GreetingServiceAsync getService(){
	 return this.greetingService;
 }
}