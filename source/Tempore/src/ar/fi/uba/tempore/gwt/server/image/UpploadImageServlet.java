package ar.fi.uba.tempore.gwt.server.image;

import gwtupload.server.UploadAction;
import gwtupload.server.exceptions.UploadActionException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.log4j.Logger;

public class UpploadImageServlet extends UploadAction {

	private static final long serialVersionUID = -8851211326056615556L;
	private final Logger log = Logger.getLogger(this.getClass());

	private static final String PATH = "C:/Tempore/Workspace/ImageRepository/"; 
	private Hashtable<String, String> receivedContentTypes = new Hashtable<String, String>();
	private Hashtable<String, String> receivedFiles = new Hashtable<String, String>();

	@Override
	public String executeAction(HttpServletRequest request, List<FileItem> sessionFiles) throws UploadActionException {
		String response = "";
		String name = request.getParameter("id");
		
		if (name == null || name.isEmpty()){
			name = "image-" + System.currentTimeMillis() + ".bin";
		}
		log.info("Path para actualizar: " + name);
		for (FileItem item : sessionFiles) {
			if (false == item.isFormField()) {
				try {
					File file = new File(PATH + name);
					item.write(file);
					
					receivedFiles.put(item.getFieldName(), file.getAbsolutePath());
					receivedContentTypes.put(item.getFieldName(), item.getContentType());

					//retornamos el path donde se guarda la info, para que se persista
					response = name;
					log.info("Archivo creado y guardado en " + file.getAbsolutePath());
				} catch (Exception e) {
					throw new UploadActionException(e.getMessage());
				}
			}
		}

		/// Remove files from session because we have a copy of them
		removeSessionFileItems(request);

		/// Send your customized message to the client.
		return response;
	}

	/**
	 * Get the content of an uploaded file.
	 */
	@Override
	public void getUploadedFile(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String fieldName = request.getParameter(PARAM_SHOW);
		String srcFile = receivedFiles.get(fieldName);
		if (srcFile != null) {
			File f = new File(srcFile);
			response.setContentType(receivedContentTypes.get(fieldName));
			FileInputStream is = new FileInputStream(f);
			copyFromInputStreamToOutputStream(is, response.getOutputStream());
		} else {
			renderXmlResponse(request, response, "<" + TAG_ERROR + ">item not found</" + TAG_ERROR + ">");
		}
	}

	/**
	 * Remove a file when the user sends a delete request.
	 */
	@Override
	public void removeItem(HttpServletRequest request, String fieldName)  throws UploadActionException {
		File file = new File(receivedFiles.get(fieldName));
		receivedFiles.remove(fieldName);
		receivedContentTypes.remove(fieldName);
		if (file != null) {
			file.delete();
		}
	}
}
