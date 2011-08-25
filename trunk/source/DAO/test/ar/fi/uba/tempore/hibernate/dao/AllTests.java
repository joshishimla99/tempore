package ar.fi.uba.tempore.hibernate.dao;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(AllTests.class.getName());
		//$JUnit-BEGIN$
		suite.addTestSuite(TestClientDAO.class);
		suite.addTestSuite(TestAlertDAO.class);
		suite.addTestSuite(TestProjectStateDAO.class);
		suite.addTestSuite(TestTaskTypeDAO.class);
		suite.addTestSuite(TestRoleDAO.class);
		suite.addTestSuite(TestUserDAO.class);
		suite.addTestSuite(TestTaskUserDAO.class);
		suite.addTestSuite(TestTaskDAO.class);
		suite.addTestSuite(TestUserProjectDAO.class);
		suite.addTestSuite(TestProjectDAO.class);
		//$JUnit-END$
		return suite;
	}

}
