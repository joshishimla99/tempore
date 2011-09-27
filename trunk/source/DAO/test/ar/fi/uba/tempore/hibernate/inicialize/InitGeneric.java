package ar.fi.uba.tempore.hibernate.inicialize;

import java.io.File;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.mysql.MySqlDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.Transaction;

import ar.fi.uba.tempore.dao.util.HibernateUtil;

public class InitGeneric extends DBTestCase{
	private static String DDBB_XML_SETUP = "./BBDD_setup.xml";
	
	protected final Logger log = Logger.getLogger(this.getClass());
	protected Transaction transaction;
	
	public InitGeneric(String fileName) throws Exception{
		super("DBUnit Demostracion....");

		if (fileName != null){
			DDBB_XML_SETUP = fileName;
		}
		
        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, "com.mysql.jdbc.Driver" );
        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, "jdbc:mysql://localhost/tempore" );
        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, "admin" );
        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, "admin" );
	
        
		IDatabaseConnection connection = this.getConnection();
		DatabaseConfig config = connection.getConfig();
        config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new MySqlDataTypeFactory());
        
		IDataSet dataSet = this.getDataSet();
		DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
		
		transaction = HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
		if (transaction.isActive()){
			transaction.commit();
		}
	}

	@Override
	protected IDataSet getDataSet() throws Exception {
		File file = new File(DDBB_XML_SETUP);
		if (!file.isFile()){
			Assert.fail("No se encuentra el archivo para iniciar la BBDD ("+file.getAbsolutePath()+")");			
		}
		return new FlatXmlDataSetBuilder().build(file);
	}
}
