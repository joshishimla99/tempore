package ar.fi.uba.tempore.hibernate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TEstConexion {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String url = "jdbc:mysql://localhost:3306/tempore";
		String user = "admin";
		String pw = "admi1n";
		try {
			Connection dbConn = DriverManager.getConnection( url, user, pw );
			System.out.println(dbConn);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
