package ar.fi.uba.tempore.hibernate;

import java.io.File;
import java.sql.SQLException;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.dbunit.Assertion;
import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.filter.DefaultColumnFilter;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.mysql.MySqlDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;

import ar.fi.uba.tempore.dao.util.HibernateUtil;

public abstract class TestDAO extends DBTestCase{
	private static final String DDBB_XML_SETUP = "./BBDD_setup.xml";
	private static final String RESULT_FORLDER = "./testResult/";

	
	protected final Logger log = Logger.getLogger(this.getClass());
	protected Transaction transaction;
	
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
	
	/**
	 * Validacion de los resultados como quedaron en la base de datos respecto a lo que espero que quede
	 * @param tableName Nombre de la tabla que quiero
	 * @param xmlExpected Nombre del archivo del XML de como espero que quede
	 */
	protected void validResult (String tableName, String xmlExpected) {
		//cierro la trasaccion
		if (transaction.isActive()){
			transaction.commit();
		}
		try {
			// XML de la base de datos como se espera o desea
			IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(new File(RESULT_FORLDER+xmlExpected));
			ITable expectedTable = expectedDataSet.getTable(tableName);
	
	        // Imagen actual de la Base de datos 
	        IDataSet databaseDataSet = getConnection().createDataSet();
	        ITable actualTable = databaseDataSet.getTable(tableName);
	        //Elimino las columnas de ID para la comparacion
	        ITable filteredTable = DefaultColumnFilter.includedColumnsTable(actualTable, expectedTable.getTableMetaData().getColumns());

        	// Validacion
        	Assertion.assertEquals(expectedTable, filteredTable);
        	
        	//vuelvo abrir la transaccion
        	//transaction = HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
		} catch (DataSetException e) {
			e.printStackTrace();
			Assert.fail("Validando los resultados de la operacion");
		} catch (SQLException e) {
			e.printStackTrace();
			Assert.fail("Validando los resultados de la operacion");
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Validando los resultados de la operacion");
		}
	}
	

	@After
	public void tearDown() throws Exception {
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
