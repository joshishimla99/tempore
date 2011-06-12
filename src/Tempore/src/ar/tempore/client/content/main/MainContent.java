package ar.fi.uba.tempore.gwt.client.content.main;

import ar.fi.uba.tempore.gwt.client.content.Content;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class MainContent implements Content{

	public void initialize() {
		RootPanel.get("subtitle").add(new Label("Subtitulo de pruebas"));
		
	}

}
