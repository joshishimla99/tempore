package ar.fi.uba.temporeutils.image;

import java.util.ArrayList;
import java.util.List;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Encoding;
import com.smartgwt.client.types.FormMethod;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.UploadItem;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangeHandler;
import com.smartgwt.client.widgets.layout.VLayout;

public class ImgClient extends VLayout implements UpdateImgHandler {

	private static final String DOWNLOAD_URL = "http://localhost:8080/Tempore/download.img?";
	private static final String UPLOAD_URL = "uploadImage.upld?";
	private static final String DEFAULT_IMAGE = "../images/unknownClient.jpg";
	private static final String FRAME_RESPONSE = "frameResponse";
	private List<UpdateImgHandler> listeners = new ArrayList<UpdateImgHandler>();
	
	private final Img img = new Img(DEFAULT_IMAGE);
	private String imgFileName;
	
	
	public ImgClient (String clientId){
		addUploadJSNIListener(this);
		
		updateImage(clientId);
		
//		img.setContents("<IFRAME name='"+FRAME_RESPONSE+"'></IFRAME>");
		img.setHeight100();
		img.setWidth100();
		
		final Canvas canvas = new Canvas();
		
		canvas.setWidth(10);
		canvas.setHeight(10);
		canvas.setContents("<IFRAME name='"+FRAME_RESPONSE+"' style='width:0;height:0;border:0'></IFRAME>");
		
		//Formulario
		final DynamicForm form = new DynamicForm();
		form.setEncoding(Encoding.MULTIPART);
		form.setMethod(FormMethod.POST);
		form.setAction(UPLOAD_URL);
		form.setTarget(FRAME_RESPONSE);
		
		final UploadItem upload = new UploadItem("Imagen");
		upload.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				form.submitForm();
			}
		});

		form.setFields(upload);
		
		
		this.setAlign(Alignment.CENTER);
		this.setMembersMargin(10);
		
		this.addMember(img);
		this.addMember(canvas);
		this.addMember(form);
	}
	
	public ImgClient (){
		this(null);
	}

	public void updateImage (String uniqueFileName) {
		if (uniqueFileName != null && !uniqueFileName.isEmpty()){
			//La imagen ya existe
			img.setSrc(DOWNLOAD_URL + "id=" + uniqueFileName + "&newurl=" + System.currentTimeMillis());
		}
		this.imgFileName = uniqueFileName;
	}

	public String getImageFileName() {
		return imgFileName;
	}
	
	
	public void addUpdateImageHandler(UpdateImgHandler uil){
		this.listeners.add(uil);
	}
	public void removeUpdateImageHandler(UpdateImgHandler uil){
		this.listeners.remove(uil);
	}

	@Override
	public void onUpdatedImg(String fileName) {
		updateImage(fileName);
		for (UpdateImgHandler listener : listeners) {
			listener.onUpdatedImg(fileName);
		}
	}
	
	public native void addUploadJSNIListener(ImgClient x)/*-{
		$wnd.uploadSuccess = function (param) {
			x.@ar.fi.uba.temporeutils.image.ImgClient::onUpdatedImg(Ljava/lang/String;)(param)
		};  
	}-*/;
}
