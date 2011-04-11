package ar.tempore.client.content.main;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

import ar.tempore.client.content.Content;

public class MainContent implements Content{

	public void initialize() {
		RootPanel.get("subtitle").add(new Label("Subtitulo de pruebas"));
		
	}

}
