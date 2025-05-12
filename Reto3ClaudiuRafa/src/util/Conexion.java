package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
	private static String url1 = "jdbc:mysql://localhost:3306/reto3";
	private static String user = "user";
	private static String password = "user";
	private static Connection con;
	
	public static Connection abreconexion() {
		try {
			con = DriverManager.getConnection(url1, user, password);
			return con;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} 	
	}
	
	public static void cierraConexion() {
		try {
			if (con!=null && !con.isClosed()) {
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
}
