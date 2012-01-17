package ar.fi.uba.tempore.hibernate.inicialize;


import org.apache.log4j.Logger;

public class Init_Setup {
	public static final Logger log = Logger.getLogger(Init_Setup.class);
		
	public static void main(String[] args) throws Exception {
		new InitGeneric("./BBDD_setup.xml");
		log.info("*** BBDD de SETUP Inicializada ***");
	}
}
