package ar.fi.uba.tempore.gwt.client;

import ar.fi.uba.tempore.dto.UserDTO;
import ar.fi.uba.tempore.gwt.client.login.LoginPanel;
import ar.fi.uba.tempore.gwt.client.login.SessionUser;
import ar.fi.uba.tempore.gwt.client.panel.ConteinerMainPanel;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.smartgwt.client.util.SC;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Tempore implements EntryPoint {
	
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
//		dummyLogin();
		
		UserServicesClient.Util.getInstance().getUserLoggued(new AsyncCallback<String>() {
			@Override
			public void onSuccess(String username) {
				if (username == null){
					//usuario desloguado
					
					new LoginPanel();
				} else {
					//usuario ya logueado
					RootPanel.get("Content").add(new ConteinerMainPanel());
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				new LoginPanel();
			}
		});
		
	}

	public void dummyLogin(){
		//TODO Dummy de loggin
		UserServicesClient.Util.getInstance().validateUser("ngarcia", "1234", new AsyncCallback<UserDTO>() {
			@Override
			public void onSuccess(UserDTO result) {
				if (result != null) {
					//LOGGIN SUCCESS
					SessionUser.getInstance().setUser(result);
					RootPanel.get("Content").add(new ConteinerMainPanel());
				} else {
					SC.say("Dummy Logging", "El usuario o password hardcodeado son erroneos (ngarcia, 1234)");
				}
			}
			@Override
			public void onFailure(Throwable caught) {
				SC.say("Dummy Logging", "Error al buscar los datos de loggin");
			}
		});	
	}

}
/* end Tempore class*/

