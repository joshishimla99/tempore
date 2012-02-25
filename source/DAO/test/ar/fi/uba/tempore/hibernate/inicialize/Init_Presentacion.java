package ar.fi.uba.tempore.hibernate.inicialize;


import org.apache.log4j.Logger;

public class Init_Presentacion {
	public static final Logger log = Logger.getLogger(Init_Presentacion.class);
		
	public static void main(String[] args) throws Exception {
		new InitGeneric("./BBDD_Presentacion_setup.xml");
		log.info("*** BBDD de PRESENTACION Inicializada ***");
	}
}
