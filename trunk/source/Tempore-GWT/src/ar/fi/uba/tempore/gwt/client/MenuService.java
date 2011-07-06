package ar.fi.uba.tempore.gwt.client;

import ar.fi.uba.tempore.gwt.client.panel.ContextPanel;
import ar.fi.uba.tempore.gwt.client.panel.MiddlePanel;

import com.google.gwt.user.client.ui.Widget;

public class MenuService {
	
	private MiddlePanel middlePanel;
	private static MenuService instance = null;
	
	private MenuService(){
	}
	
	public static MenuService getInstance(){
		if(instance == null) {
			instance = new MenuService();
		}
		return instance;
	}
	
	public void init(MiddlePanel middlePanel){
		this.middlePanel = middlePanel;
	}
	
	public void setNewContextPanel(Widget panel){
		middlePanel.updateContextPanel(panel);
	}

}
