package ar.fi.uba.tempore.gwt.server.image;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class DonwloadImageServlet extends HttpServlet {

	private final Logger log = Logger.getLogger(this.getClass());
	private static final String PATH = "C:/Tempore/Workspace/ImageRepository/";
	private static final long serialVersionUID = 6363075732385292579L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String fileSrc = req.getParameter("id");
		
		log.info("DOWNLOADING a IMAGE ("+fileSrc+")...");
		if (fileSrc != null && !fileSrc.isEmpty()){
			fileSrc = PATH + fileSrc;
			File file = new File(fileSrc);
			if (file != null && file.isFile()){
				ServletContext sc = getServletContext();
				String mimeType = sc.getMimeType(fileSrc);
		        if (mimeType == null) {
		            sc.log("Could not get MIME type of "+fileSrc);
		            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		            return;
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
			} else {
				//TODO enviar imagen por defecto
				log.error("Error al buscar la imagen correspondiente ("+fileSrc+")");
			}
		} else {
			//TODO enviar imagen por defecto
			log.error("No tiene archivo, enviar imagen por defecto");
		}
	}
}
