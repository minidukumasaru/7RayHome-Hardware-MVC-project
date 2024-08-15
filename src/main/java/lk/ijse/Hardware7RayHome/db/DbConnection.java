package lk.ijse.Hardware7RayHome.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

 public class DbConnection {
    private static DbConnection DbConnection;
    private Connection connection;
    private DbConnection() throws SQLException {
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/7RayHome",
                "root",
                "Ijse@123"

        );
    }
    public static DbConnection getInstance() throws SQLException {
        if (DbConnection == null) {
            DbConnection = new DbConnection();
        }
        return DbConnection;
    }
    public Connection getConnection(){
        return connection;
    }

}
