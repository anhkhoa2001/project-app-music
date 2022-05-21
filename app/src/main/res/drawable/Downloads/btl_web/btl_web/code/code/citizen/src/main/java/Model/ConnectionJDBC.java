package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//tạo kết nối với database thông qua JDBC
public class ConnectionJDBC {
	private static final int port = 3306;
	private static final String user = "root";
	private static final String pass = "1234";
	private static final String DBname = "citizen";
	
	
	public static Connection getJDBC() {
		String query = "jdbc:mysql://localhost:" + port + "/" + DBname;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			return DriverManager.getConnection(query, user, pass);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} 
		return null;
	}	
}
