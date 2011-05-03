package ar.fi.uba.tempore.hibernate;

import junit.framework.Test;
import junit.framework.TestSuite;
import ar.fi.uba.tempore.hibernate.dao.TestClientDAO;
import ar.fi.uba.tempore.hibernate.dao.TestContactDAO;
import ar.fi.uba.tempore.hibernate.dao.TestContactTypeDAO;
import ar.fi.uba.tempore.hibernate.dao.TestPositionDAO;
import ar.fi.uba.tempore.hibernate.dao.TestPrivilegeDAO;
import ar.fi.uba.tempore.hibernate.dao.TestProjectDAO;
import ar.fi.uba.tempore.hibernate.dao.TestProjectStateDAO;
import ar.fi.uba.tempore.hibernate.dao.TestRoleDAO;
import ar.fi.uba.tempore.hibernate.dao.TestTaskDAO;
import ar.fi.uba.tempore.hibernate.dao.TestTaskTypeDAO;
import ar.fi.uba.tempore.hibernate.dao.TestTaskUserDAO;
import ar.fi.uba.tempore.hibernate.dao.TestUserDAO;
import ar.fi.uba.tempore.hibernate.dao.TestUserProjectDAO;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(AllTests.class.getName());
		//$JUnit-BEGIN$
		suite.addTestSuite(TestClientDAO.class);
		suite.addTestSuite(TestContactDAO.class);
		suite.addTestSuite(TestContactTypeDAO.class);
		suite.addTestSuite(TestPositionDAO.class);
		suite.addTestSuite(TestProjectStateDAO.class);
		suite.addTestSuite(TestTaskTypeDAO.class);
		suite.addTestSuite(TestPrivilegeDAO.class);
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
