package ar.fi.uba.tempore.gwt.client.panel.report;

import ar.fi.uba.tempore.gwt.client.panel.TabsPanelContainer;

import com.smartgwt.client.widgets.Label;

public class ReportTabPanel extends TabsPanelContainer{
	
	public ReportTabPanel(){
		Label label = new Label("Reportes");
		this.addChild(label);
	}

	@Override
	public void refreshPanel() {
		
	}

	@Override
	public void freePanel() {
		
	}

}
