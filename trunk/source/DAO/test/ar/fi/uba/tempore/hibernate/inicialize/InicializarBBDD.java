package ar.fi.uba.tempore.hibernate.inicialize;


import org.apache.log4j.Logger;
import org.junit.Test;

import ar.fi.uba.tempore.hibernate.TestDAO;

public class InicializarBBDD extends TestDAO {
	protected final Logger log = Logger.getLogger(this.getClass());
	
	@Test
	public void test() {
		log.info("*** BBDD inicializada ***");
	}
}
