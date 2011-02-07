package com.gs.spring4.gwt.ex.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
* The async counterpart of GreetingService.
*/
public interface GreetingServiceAsync {
 void greet(String input, String input2, AsyncCallback callback);
}