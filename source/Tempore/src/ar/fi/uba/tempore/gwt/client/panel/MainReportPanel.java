package ar.fi.uba.tempore.gwt.client.panel;

import ar.fi.uba.tempore.gwt.client.panel.menus.ContextChildPanel;

import com.google.gwt.user.client.ui.VerticalPanel;
import com.smartgwt.client.widgets.Label;

public class MainReportPanel extends VerticalPanel implements ContextChildPanel{

	public MainReportPanel(){
	}

	@Override
	public void UpdateContent() {
		// TODO Auto-generated method stub
		this.add(new Label("Visor de reportes"));
	}
}
