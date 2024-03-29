package ar.fi.uba.tempore.gwt.client.login;

import ar.fi.uba.tempore.dto.UserDTO;


/**
 * Clase singleton que contiene la informacion del usuario que esta logeado en la sesion
 * @author Ludmila
 *
 */
public class SessionUser {

	static private SessionUser instance = null;
	
	private UserDTO user;
	
	private SessionUser(){}
	public static SessionUser getInstance(){
		if (instance == null){
			instance = new SessionUser();			
		}
		return instance;
	}
		
	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}
	
}
