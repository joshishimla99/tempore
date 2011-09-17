package ar.fi.uba.tempore.gwt.client.panel.help;

import ar.fi.uba.temporeutils.image.ImgClient;
import ar.fi.uba.temporeutils.image.UpdateImgHandler;

import com.google.gwt.user.client.Window;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.VLayout;

public class HelpTabPanel extends Canvas {
	private VLayout vLayout = new VLayout();
	
	public HelpTabPanel(){
		super();
		Label titulo = new Label("GWT-Upload Demo");

		final ImgClient img = new ImgClient();
		img.setWidth(300);
		img.setHeight(100);
		img.addUpdateImageHandler(new UpdateImgHandler() {
			@Override
			public void onUpdatedImg(String fileName) {
				Window.alert("Persistir nombre del archivo: " + fileName);
			}
		});
		
		vLayout.addMember(titulo);
		vLayout.addMember(img);
		this.addChild(vLayout);
	}
}
