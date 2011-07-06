package ar.fi.uba.tempore.gwt.client.panel;

import com.google.gwt.user.client.ui.Widget;
import com.smartgwt.client.widgets.layout.HLayout;

public class MiddlePanel extends HLayout{
	private ContextPanel contextPanel;
	
	public MiddlePanel(){
	    this.addMember(new ProjectPanel());
	    this.contextPanel = new ContextPanel();
	    this.addMember(this.contextPanel);
	}

	public void updateContextPanel(Widget newPanel){
		this.contextPanel.updateContextPanel(newPanel);
	}
}
