import java.sql.*;

public class DBConnection {

    public static Connection getConnection() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

Connection con = DriverManager.getConnection(
    "jdbc:sqlserver://127.0.0.1:1433;databaseName=airline_db;user=sa;password=1234;encrypt=true;trustServerCertificate=true"
);;        return con;

        } catch (Exception e) {
            System.out.println("ERROR: " + e);
            return null;
        }
    }
}