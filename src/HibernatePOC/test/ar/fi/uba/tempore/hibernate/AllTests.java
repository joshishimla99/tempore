package ar.fi.uba.tempore.hibernate;

import ar.fi.uba.tempore.hibernate.dao.TestClientDAO;
import ar.fi.uba.tempore.hibernate.dao.TestContactDAO;
import ar.fi.uba.tempore.hibernate.dao.TestContactTypeDAO;
import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(AllTests.class.getName());
		//$JUnit-BEGIN$
		suite.addTestSuite(TestClientDAO.class);
		suite.addTestSuite(TestContactDAO.class);
		suite.addTestSuite(TestContactTypeDAO.class);
		//$JUnit-END$
		return suite;
	}

}
