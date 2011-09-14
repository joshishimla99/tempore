package ar.fi.uba.tempore.gwt.client;

import ar.fi.uba.tempore.gwt.client.panel.ConteinerMainPanel;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Tempore implements EntryPoint {
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
//		new LoginPanel();
		RootPanel.get("Content").add(new ConteinerMainPanel());
	}
}
/* end Tempore class*/

