package ar.fi.uba.tempore.gwt.client.panel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.user.client.ui.DecoratedTabPanel;
import com.smartgwt.client.widgets.Canvas;

public class SubTabPanel extends Canvas {
	
	private List<ContextChildPanel> panels;
	DecoratedTabPanel tabPanel;
	
	public SubTabPanel(){
		super();
		this.panels = new ArrayList<ContextChildPanel>();
		tabPanel = new DecoratedTabPanel();
		tabPanel.setWidth("800px");
		tabPanel.setAnimationEnabled(true);
		
	}
	
	protected void addChildPanels(ContextChildPanel childPanel){
		this.panels.add(childPanel);
	}
	
	protected DecoratedTabPanel getTabPanel(){
		return this.tabPanel;
	}
	
	public void loadChildPanels(){
		Iterator<ContextChildPanel> i = panels.iterator();
		while (i.hasNext()){
			ContextChildPanel childPanel = i.next();
			childPanel.UpdateContent();
		}
	}

}
