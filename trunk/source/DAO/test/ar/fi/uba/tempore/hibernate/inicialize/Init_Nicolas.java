package ar.fi.uba.tempore.hibernate.inicialize;


import org.apache.log4j.Logger;

public class Init_Nicolas {
	public static final Logger log = Logger.getLogger(Init_Nicolas.class);
		
	public static void main(String[] args) throws Exception {
		new InitGeneric("./BBDD_Nicolas_setup.xml");
		log.info("*** BBDD de Nicolas Inicializada ***");
	}
}
