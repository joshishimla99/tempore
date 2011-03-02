package com.gs.spring4.gwt.ex.server;

import org.springframework.stereotype.Service;

import com.gs.spring4.gwt.ex.client.GreetingService;

/**
* The server side implementation of the RPC service.
*/
@SuppressWarnings("serial")
@Service("greetingService")
public class GreetingServiceImpl implements  GreetingService {

 public String checkLogin(String input, String password) {
	 // TODO: Acceder al servicio que valide los datos en la base
  return "Aca debemos validar a , " + input + " que tiene la clave " + password;
 }

}