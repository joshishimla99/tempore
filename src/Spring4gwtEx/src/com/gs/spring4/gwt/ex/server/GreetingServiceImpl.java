package com.gs.spring4.gwt.ex.server;

import org.springframework.stereotype.Service;

import com.gs.spring4.gwt.ex.client.GreetingService;

/**
* The server side implementation of the RPC service.
*/
@SuppressWarnings("serial")
@Service("greetingService")
public class GreetingServiceImpl implements  GreetingService {

 public String greet(String input, String password) {
  return "Hello from the server, " + input + "!\n Tu clave es: " + password;
 }
}