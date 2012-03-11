package ar.fi.uba.tempore.gwt.client.panel;

import ar.fi.uba.tempore.gwt.client.login.SessionUser;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;

public class UserLoggedPanel extends HLayout {

	public UserLoggedPanel(){
		this.setHeight(10);
		this.setWidth100();
		this.setAlign(Alignment.RIGHT);
		this.setMembersMargin(10);
		
		Label logout = new Label("Salir");
		logout.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				//TODO logout
				redirect("http://localhost:8080/Tempore/Tempore.html?locale=es"); 
			}
		});
		logout.setWidth(45);
		logout.setAlign(Alignment.RIGHT);
		logout.setStyleName("logoutPanel");
		logout.setShowDown(true);
		
		
		Label user = new Label("Hola "+SessionUser.getInstance().getUser().getName() +"!");
		user.setAlign(Alignment.RIGHT);
		user.setHeight(10);
		user.setWidth(200);
		user.setStyleName("userloggedPanel");
		
		this.addMember(user);
		this.addMember(logout);
	}
	
	public native void redirect(String url) /*-{ 
	        $wnd.location.replace(url); 
	}-*/; 
}
