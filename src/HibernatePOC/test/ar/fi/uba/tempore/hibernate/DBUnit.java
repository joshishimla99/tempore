package ar.fi.uba.tempore.hibernate;

import java.io.FileInputStream;

import org.dbunit.DBTestCase;
import org.dbunit.IDatabaseTester;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;

public class DBUnit extends DBTestCase {

	// static private String pathArchivo = "c:\\Users\\gpantaleo\\trabajo\\ArqEnterpriseMaven\\trunk\\maven.1229447681563\\src\\test\\java\\utilPruebas\\";
	private static final String pathArchivo = "./";
//	private static IDatabaseTester databaseTester;
//	private IDatabaseConnection connection = null;

	public DBUnit(String name) {
		super(name);
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS,"com.mysql.jdbc.Driver");
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL,"jdbc:mysql://localhost/tempore");
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME,"root");
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD,"");

//		try {
//			connection = getConnection();
//		} catch (Exception e1) {
//			fail("Falla get connection");
//		}
//		DatabaseConfig config = connection.getConfig();
//		config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new MySqlDataTypeFactory());
	}
	
	@Override
	protected IDataSet getDataSet() throws Exception {
		return new FlatXmlDataSetBuilder().build(new FileInputStream(pathArchivo + "Test1_setup.xml"));
	}
	
//	@Override
//	protected DatabaseOperation getSetUpOperation () throw Exception{
//		return DatabaseOperation.REFRESH;
//	}
//	
//	@Override
//	protected DatabaseOperation getTearDownOperation () throw Exception{
//		return DatabaseOperation.REFRESH;
//	}

//	protected void setUp() {
//		// initialize your dataset here
//		IDataSet dataSet = null;
//		try {
//			dataSet = getDataSet();
//			databaseTester.setDataSet(dataSet);
//
//			// realiza tarea de inicialización de datos
//			DatabaseOperation.CLEAN_INSERT.execute(getConnection(), dataSet);
//
//		} catch (Exception e) {
//			fail("Falla databaseTester / operation en setup");
//		}
//	}

//	public void testUpdate() throws Exception {
		// adapta el registro
//		ClienteDTO cliente = null;
//		IadminCliente iAdminCliente = (IadminCliente) FactoryServiciosAplicacion
//		.instance().getServiciosAplicacion("AdminCliente");
//
//		cliente = iAdminCliente.findCliente(1);
//		assertNotNull(cliente);
//		assertTrue("No encontramos al cliente", cliente.get("IdClientePersona")
//				.equals("1"));
//
//		cliente.set("DirCorreoElectrónico", "kkk@hotmail.com");
//		iAdminCliente.adaptarCliente(cliente);
//
//		// ----------------- test usando DBUnit ----------------
//		assertDBUnit("testAdminCliente_Update.xml", "client");
//	}

	// --------------------------------------------------------------------
	// metodos auxiliares
	// --------------------------------------------------------------------
//	private void assertDBUnit(String expectedDataSetName, String tableName) {
//		IDataSet databaseDataSet = null;
//		ITable actualTable = null;
//		IDataSet expectedDataSet = null;
//		ITable expectedTable = null;
//
//		try {
//			databaseDataSet = getConnection().createDataSet();
//			actualTable = databaseDataSet.getTable(tableName);
//
//			expectedDataSet = new FlatXmlDataSetBuilder().build(new File(pathArchivo + expectedDataSetName));
//			expectedTable = expectedDataSet.getTable(tableName);
//
//			Assertion.assertEquals(expectedTable, actualTable);
//
//		} catch (SQLException e) {
//			fail(e.getMessage());
//		} catch (Exception e) {
//			fail(e.getMessage());
//		}
//
//
//	}


}

