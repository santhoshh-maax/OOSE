import java.sql.*;

public class DBConnection {
    public static Connection getConnection() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            // Updated with your server IP and credentials
            String url = "jdbc:sqlserver://103.207.1.91:1433;databaseName=CSE9490;encrypt=true;trustServerCertificate=true";
            Connection con = DriverManager.getConnection(url, "MZCET", "MZCET@1234");
            return con;
        } catch (Exception e) {
            System.out.println("Database Connection Error: " + e);
            return null;
        }
    }
}