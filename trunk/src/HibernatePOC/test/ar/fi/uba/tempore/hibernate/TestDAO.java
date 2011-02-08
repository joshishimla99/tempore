package ar.fi.uba.tempore.hibernate;

import org.apache.log4j.Logger;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import org.junit.After;
import org.junit.Before;

import ar.fi.uba.tempore.hibernate.util.HibernateUtil;

public abstract class TestDAO {
	protected final Logger log = Logger.getLogger(this.getClass());
	private Transaction transaction;
	
	
	@Before
	public void setUp() throws Exception {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		transaction = session.beginTransaction();				
	}

	@After
	public void tearDown() throws Exception {
		transaction.commit();
	}
}
