package ar.fi.uba.tempore.gwt.server.image;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

public class NewUpploadImageServlet extends HttpServlet {

	private final Logger log = Logger.getLogger(this.getClass());
	private File tmpDir;
	private File destinationDir;
	private static final long serialVersionUID = -4219692406434715316L;
	private static final String TMP_DIR_PATH = "C:/Tempore/Workspace/ImageRepository/temp";
	private static final String DESTINATION_DIR_PATH = "C:/Tempore/Workspace/ImageRepository/";
	
	private static final String RESPONSE =	"<script type='text/javascript' language='javascript'>" +
												"top.uploadSuccess('%%PARAMETRO%%');" +
											"</script>";

	//private static final String RESPONSE ="<script type='text/javascript' language='javascript'>myFunction();alert('Hola mundo');</script>";

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		tmpDir = new File(TMP_DIR_PATH);
		if(!tmpDir.isDirectory()) {
			throw new ServletException(TMP_DIR_PATH + " is not a directory");
		}
		//String realPath = getServletContext().getRealPath(DESTINATION_DIR_PATH);
		String realPath = DESTINATION_DIR_PATH;
		destinationDir = new File(realPath);
		if(!destinationDir.isDirectory()) {
			throw new ServletException(DESTINATION_DIR_PATH+" is not a directory");
		}

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.info("NEW UPLOADFILE...");
		PrintWriter out = response.getWriter();

		response.setContentType("text/html");
		
		
		DiskFileItemFactory  fileItemFactory = new DiskFileItemFactory ();
		fileItemFactory.setSizeThreshold(1*1024*1024); //1 MB
		fileItemFactory.setRepository(tmpDir);

		ServletFileUpload uploadHandler = new ServletFileUpload(fileItemFactory);
		try {
			List<FileItem> items = uploadHandler.parseRequest(request);
			for (FileItem item : items) {
				if(item.isFormField()) {
					//					out.println("File Name = "+item.getFieldName()+", Value = "+item.getString());
				} else {
					String newName = System.currentTimeMillis() + "_" + item.getName();
					File file = new File(destinationDir,newName);
					item.write(file);
					log.info("Archivo guardado " + file.getAbsolutePath());
					
				    out.println(RESPONSE.replaceFirst("%%PARAMETRO%%", newName));
				    log.info("Script de respuesta: " + RESPONSE.replaceFirst("%%PARAMETRO%%", newName));
				}
			}
			out.close();
		}catch(FileUploadException ex) {
			log("Error encountered while parsing the request",ex);
		} catch(Exception ex) {
			log("Error encountered while uploading file",ex);
		}
	}


}
