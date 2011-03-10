package com.gs.spring4.gwt.ex.server;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.gs.spring4.gwt.ex.client.GreetingService;
import com.gs.spring4.gwt.ex.client.users.User;

/**
* The server side implementation of the RPC service.
*/
@SuppressWarnings("serial")
@Service("greetingService")
public class GreetingServiceImpl extends RemoteServiceServlet implements  GreetingService {
 
 private static final String USER_SESSION = "GWTAppUser";
	
 public User checkLogin(String input, String password) {
	 // TODO: Acceder al servicio que valide los datos en la base
	 User user = new User(input, password);
//	 this.setUserInSession(user);
     return user;
 }
 
 private void setUserInSession(User user) {
	    HttpSession session = getThreadLocalRequest().getSession();
	    session.setAttribute(USER_SESSION, user);
	}
	 
 public User getUserFromSession() {
	    HttpSession session = getThreadLocalRequest().getSession();
	    return (User) session.getAttribute(USER_SESSION);
 }
}