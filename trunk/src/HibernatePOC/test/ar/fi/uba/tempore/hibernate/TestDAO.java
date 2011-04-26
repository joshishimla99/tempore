package ar.fi.uba.tempore.hibernate;

import java.io.File;

import org.apache.log4j.Logger;
import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.mysql.MySqlDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;

import ar.fi.uba.tempore.hibernate.util.HibernateUtil;

public class TestDAO extends DBTestCase{
	protected final Logger log = Logger.getLogger(this.getClass());
	private Transaction transaction;
	
	public TestDAO(){		
		super("DBUnit Demostracion....");
		
        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, "com.mysql.jdbc.Driver" );
        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, "jdbc:mysql://localhost/tempore" );
        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, "root" );
        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, "" );
	}
		

	@Before
	public void setUp() throws Exception {		
		IDatabaseConnection connection = this.getConnection();
		DatabaseConfig config = connection.getConfig();
        config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new MySqlDataTypeFactory());
        
		IDataSet dataSet = this.getDataSet();
		DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
		transaction = HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
	}

	@After
	public void tearDown() throws Exception {
		transaction.commit();
	}

	@Override
	protected IDataSet getDataSet() throws Exception {
		FlatXmlDataSet build = null;
		File file = new File("./Test1_setup.xml");
		if (file.isFile()){
			build = new FlatXmlDataSetBuilder().build(file);
		} else {
			throw new Exception("No se encuentra el archivo para iniciar la BBDD ("+file.getAbsolutePath()+")");
		}
		return build;
	}
}
