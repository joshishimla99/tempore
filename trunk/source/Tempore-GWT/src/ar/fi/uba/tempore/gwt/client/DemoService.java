package ar.fi.uba.tempore.gwt.client;


import ar.fi.uba.tempore.dto.DemoDto;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("demo")
public interface DemoService extends RemoteService {
	String callServer(String name, DemoDto demoDto) throws IllegalArgumentException;
}
