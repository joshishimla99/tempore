package ar.fi.uba.tempore.gwt.client.panel.menus;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.tab.TabSet;

public class SubTabPanel extends Canvas {
	
	private List<ContextChildPanel> panels;
	TabSet tabPanel;
	
	public SubTabPanel(){
		super();
		this.panels = new ArrayList<ContextChildPanel>();
		tabPanel = new TabSet();
		tabPanel.setWidth("725px");
		tabPanel.setHeight("560 px");
		
	}
	
	protected void addChildPanels(ContextChildPanel childPanel){
		this.panels.add(childPanel);
	}
	
	protected TabSet getTabPanel(){
		return this.tabPanel;
	}
	
	public void loadChildPanels(){
		Iterator<ContextChildPanel> i = panels.iterator();
		while (i.hasNext()){
			ContextChildPanel childPanel = i.next();
			childPanel.updateContent();
		}
	}

}
