package com.gs.spring4.gwt.ex.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.gs.spring4.gwt.ex.client.users.User;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("springGwtServices/greetingService")
public interface GreetingService extends RemoteService {
	
	User checkLogin(String name, String password);
}