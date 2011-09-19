package ar.fi.uba.tempore.gwt.client.panel.help;

import ar.fi.uba.temporeutils.image.ImgClient;
import ar.fi.uba.temporeutils.image.UpdateImgHandler;

import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.VLayout;

public class HelpTabPanel extends Canvas {
		
	public HelpTabPanel(){
		super();
		this.setWidth100();
		this.setHeight100();

		Label titulo = new Label("GWT-Upload Demo");
		
	
		ImgClient img = new ImgClient();
		img.addUpdateImageHandler(new UpdateImgHandler() {
			@Override
			public void onUpdatedImg(String fileName) {
				SC.say("Imagen salvada con un nombre unico ("+fileName+")");
			}
		});
		img.setWidth(200);
		img.setHeight(120);

		
		final VLayout vLayout = new VLayout();
		vLayout.setWidth100();
		vLayout.setHeight100();

		vLayout.addMember(titulo);
		vLayout.addMember(img);
		this.addChild(vLayout);
	}
	
}

