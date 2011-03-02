package com.gs.spring4.gwt.ex.client.pages;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.gs.spring4.gwt.ex.client.users.User;
import  com.google.gwt.user.client.ui.Label;

public class HomePage extends Composite{

	public HomePage(User user)    {
	    VerticalPanel vp=new VerticalPanel();
	    Label lblWelcome=new Label();
	    lblWelcome.setText("Bienvenido "+user.getUserName());
	    lblWelcome.setVisible(true);
	    vp.add(lblWelcome);
	 
	    initWidget(vp);
	  }

}
