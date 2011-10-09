package ar.fi.uba.tempore.gwt.client.panel;

import com.smartgwt.client.widgets.Canvas;


public abstract class TabsPanelContainer extends Canvas {
		
	public TabsPanelContainer(){
		super();

		this.setWidth100();
		this.setHeight100();
	}
	
	public abstract void refreshPanel();
	
	public abstract void freePanel();
}
