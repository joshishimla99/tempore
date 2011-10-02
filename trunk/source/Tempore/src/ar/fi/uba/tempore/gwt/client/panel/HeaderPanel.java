package ar.fi.uba.tempore.gwt.client.panel;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.layout.HLayout;

public class HeaderPanel extends HLayout {
	private static final String TEMPORE_HEADER = "Tempore_Header";

	public HeaderPanel(){
		super();
		
		HLayout hLayout_izq = new HLayout();
		HLayout hLayout_der = new HLayout();
		
		hLayout_izq.setWidth("60%");
		hLayout_izq.setAlign(Alignment.LEFT);
		hLayout_der.setWidth("40%");
		hLayout_der.setAlign(Alignment.RIGHT);
		
		setStyleName(TEMPORE_HEADER);
		setWidth100();
		setSize("100%", "75px");
//		setDefaultLayoutAlign(Alignment.CENTER);
//		setAlign(VerticalAlignment.CENTER);

		Img a = new Img("../images/logo_tempore_izq.jpg");
		a.setSize("360", "75");
		
		hLayout_izq.addMember(a);		
		Img b = new Img("../images/logo_tempore_der.jpg");
		b.setSize("230", "75");
		hLayout_der.addMember(b);	
		addMember(hLayout_izq);
		addMember(hLayout_der);
		
		
	}
}
