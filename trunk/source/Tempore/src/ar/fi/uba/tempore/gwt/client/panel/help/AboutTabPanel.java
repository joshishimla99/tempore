package ar.fi.uba.tempore.gwt.client.panel.help;



import ar.fi.uba.tempore.gwt.client.panel.TabsPanelContainer;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class AboutTabPanel extends TabsPanelContainer {

	public AboutTabPanel(){
		super();
						
		
		HTMLFlow textHtml = new HTMLFlow();
		textHtml.setWidth100();
		textHtml.setMargin(25);
		textHtml.setContents("<H2>Manej&aacute; tus tiempos</H2>" +
				"<H2>Version 1.0.0</H2>" +
				"<H4>Autores:</H4> " +
				"Ludmila Lis, RINAUDO" +
				"<br/>" +
				"Juan Pablo, GIGANTE" +
				"<br/>" +
				"Nicol&aacute;s, GARC&Iacute;A" +
				"" +
				"<H4>Tutor:</H4>" +
				"Ing. Guillermo, PANTALEO" +
				"<br/>" +"<br/>" +
				"Trabajo Profesional de la Carrera Ingenier&iacute;a en Inform&aacute;tica de la Universidad de Buenos Aires" +
				"<br/>" +
				"Marzo de 2012");
		
		final Img img = new Img("../images/tempore_reporting.jpg");
		img.setPadding(10);
		img.setSize("370", "120");
		final VLayout aboutLayout = new VLayout();
		aboutLayout.setWidth("50%");
		aboutLayout.setHeight100();
		aboutLayout.addMember(img);
		aboutLayout.addMember(textHtml);
		
		
		final Img imgUba2 = new Img("../images/logo_fiuba_circular.png");
		imgUba2.setSize(300);
		imgUba2.setAlign(Alignment.CENTER);
		
		final VLayout imgUbaLayout = new VLayout();
		imgUbaLayout.setWidth("50%");
		imgUbaLayout.setHeight100();
		imgUbaLayout.setAlign(Alignment.CENTER);
		imgUbaLayout.setMembersMargin(30);
		imgUbaLayout.addMember(imgUba2);
		
		
		final HLayout mainLayout = new HLayout();
		mainLayout.setWidth100();
		mainLayout.setHeight100();
		
		mainLayout.addMember(aboutLayout);
		mainLayout.addMember(imgUbaLayout);
		
		this.addChild(mainLayout);
	}

	@Override
	public void refreshPanel() {
		// NO debe hacer nada
	}

	@Override
	public void freePanel() {
		// NO debe hacer nada
	}

}
