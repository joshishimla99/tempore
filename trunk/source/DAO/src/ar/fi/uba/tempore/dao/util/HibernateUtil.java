package ar.fi.uba.tempore.dao.util;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	private static final SessionFactory sessionFactory = buildSessionFactory();
	private static Logger log = Logger.getLogger(HibernateUtil.class); 
	
	private static SessionFactory buildSessionFactory() {
		try {		
			//log.info("-- buildSessionFactory --");
			System.out.println("-- buildSession FACTORY --");
			return new Configuration().configure().buildSessionFactory();
		} catch (Throwable ex) {
			System.err.println("Error al inicializar Sesion de Hibernate. Mensaje de error: " + ex.getMessage());
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		if (sessionFactory==null || sessionFactory.isClosed()){
			log.info("-- buildSessionFactory --");
			buildSessionFactory();
		}
		return sessionFactory;
	}
}
