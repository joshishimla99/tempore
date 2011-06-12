package ar.fi.uba.tempore.gwt.client;

import ar.fi.uba.tempore.dto.DemoDto;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface DemoServiceAsync {
	void callServer(String input, DemoDto demoDto,  AsyncCallback<String> callback)
			throws IllegalArgumentException;
}
