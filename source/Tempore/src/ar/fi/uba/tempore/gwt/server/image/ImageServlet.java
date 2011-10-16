package ar.fi.uba.tempore.gwt.server.image;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

public class ImageServlet extends HttpServlet {

	private static final long serialVersionUID = -4219692406434715316L;
	private final Logger log = Logger.getLogger(this.getClass());
	private static final String DESTINATION_DIR_PATH = "C:/Tempore/Workspace/ImageRepository/";
	private static final String TMP_DIR_PATH = DESTINATION_DIR_PATH + "temp/";
	private File tmpDir;
	private File destinationDir; 

	private static final String UPDATE_RESPONSE =	"<script type='text/javascript' language='javascript'>" +
														"top.uploadSuccess('%%PARAMETRO%%');" +
													"</script>";

	/**
	 * Inicializa los directorios para guardar los archivos
	 */
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

	/**
	 * Upload de los archivos para ser procesados
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.info("UPLOADFILE...");
		PrintWriter out = response.getWriter();

		response.setContentType("text/html");
		
		DiskFileItemFactory  fileItemFactory = new DiskFileItemFactory ();
		fileItemFactory.setSizeThreshold(1*1024*1024); //1 MB
		fileItemFactory.setRepository(tmpDir);

		ServletFileUpload uploadHandler = new ServletFileUpload(fileItemFactory);
		try {
			List<FileItem> items = uploadHandler.parseRequest(request);
			for (FileItem item : items) {
				if(!item.isFormField()) {					
					File file = new File(destinationDir,System.currentTimeMillis() + "_" + item.getName());
					item.write(file);
					log.info("Archivo guardado " + file.getName());
					
					
					String scriptReturn = UPDATE_RESPONSE.replaceFirst("%%PARAMETRO%%", file.getName());
				    out.println(scriptReturn);
				}
			}
			out.close();
		}catch(FileUploadException ex) {
			log("Archivo no encontrado",ex);
		} catch(Exception ex) {
			log("Error encontrado al intentar subir el archivo",ex);
		}
	}


	/**
	 * Metodo que se utiliza para bajar archivos existentes
	 * @param id: Nombre del archivo (UNIQUE)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String fileSrc = req.getParameter("id");
		//log.info("DOWNLOADING - "+fileSrc+"...");
		
		File file = null;
		String mimeType = null;
		
		if (fileSrc != null && !fileSrc.isEmpty()){
			file = new File(destinationDir, fileSrc);			
			if (file != null && file.isFile()){
				ServletContext sc = getServletContext();
				 mimeType = sc.getMimeType(fileSrc);
		        if (mimeType == null) {
	        		sc.log("Could not get MIME type of "+fileSrc);
	        		resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	        		return;
		        }
			} else {
	        	//No se encuantra archivo
				log.warn("Error al buscar la imagen correspondiente ("+fileSrc+")");
				file = new File(getServletContext().getRealPath("images/unknownUser.jpg"));
				mimeType = "image/jpg";
			}	
		} else {
			//No se tiene archivo
			log.warn("No se posee imagen, se muestra archivo por defecto...");
			file = new File(getServletContext().getRealPath("images/unknownUser.jpg"));
			mimeType = "image/jpg";
		}
       
		resp.setContentType(mimeType);
		
		FileInputStream in = new FileInputStream(file);
		OutputStream out = resp.getOutputStream();

		resp.setContentLength((int)file.length());
    
        // Copy the contents of the file to the output stream
        byte[] buf = new byte[1024];
        int count = 0;
        while ((count = in.read(buf)) >= 0) {
            out.write(buf, 0, count);
        }
        in.close();
        out.close();
	}
}
