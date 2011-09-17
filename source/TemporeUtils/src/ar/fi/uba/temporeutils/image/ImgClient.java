package ar.fi.uba.temporeutils.image;

import java.util.ArrayList;
import java.util.List;

import gwtupload.client.IUploadStatus.Status;
import gwtupload.client.IUploader;
import gwtupload.client.IUploader.OnFinishUploaderHandler;
import gwtupload.client.SingleUploader;

import com.google.gwt.user.client.Window;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.layout.VLayout;

public class ImgClient extends VLayout implements UpdateImgHandler {

	private static final String DOWNLOAD_URL = "http://localhost:8080/Tempore/download.img?";
	private static final String UPLOAD_URL = "http://localhost:8080/Tempore/upload.gupld?";
	private static final String DEFAULT_IMAGE = "../images/unknownClient.jpg";
	private List<UpdateImgHandler> listeners = new ArrayList<UpdateImgHandler>();
	
	final Img img = new Img(DEFAULT_IMAGE);
	final SingleUploader link = new SingleUploader();
	private String imgFileName;
	
	public ImgClient (String clientId){
		super(); 
		
		updateImage(clientId);

		link.setHeight("20px");
		link.setAutoSubmit(true);
		link.addOnFinishUploadHandler(new OnFinishUploaderHandler() {
			@Override
			public void onFinish(IUploader uploader) {
				if (uploader.getStatus() == Status.SUCCESS) {
					String response = uploader.getServerInfo().message;
					updateImage(response);
					//Aviso que se actualizo el archivo
					onUpdatedImg(response);
				} else {
					Window.alert("ERROR al subir archivo (STATUS = "+uploader.getStatus()+")");
				}
			}
		});
		
		img.setHeight100();
		img.setWidth100();
		
		
		this.setAlign(Alignment.CENTER);
		this.setMembersMargin(10);
		this.addMember(img);
		this.addMember(link);
	}
	
	public ImgClient (){
		this(null);
	}

	public void updateImage (String id) {
		if (id != null && !id.isEmpty()){
			//La imagen ya existe
			img.setSrc(DOWNLOAD_URL + "id=" + id + "&newurl=" + System.currentTimeMillis());
			link.setServletPath(UPLOAD_URL + "id=" + id);
		}
		this.imgFileName = id;
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
		for (UpdateImgHandler  listener : listeners) {
			listener.onUpdatedImg(getImageFileName());
		}
	}
}
