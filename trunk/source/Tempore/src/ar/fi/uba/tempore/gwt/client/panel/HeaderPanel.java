package ar.fi.uba.tempore.gwt.client.panel;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.layout.VLayout;

public class HeaderPanel extends VLayout {
	private static final String TEMPORE_HEADER = "Tempore_Header";

	public HeaderPanel(){
		super();
		setStyleName(TEMPORE_HEADER);
		setWidth100();
		setHeight("75px");
		setDefaultLayoutAlign(Alignment.CENTER);
		setAlign(VerticalAlignment.CENTER);

		Img a = new Img("../images/logo_tempore.jpg");
		a.setHeight(75);
		a.setWidth(900);
		addMember(a);
	}
}
