import java.sql.*;

public class DBConnection {
    public static Connection getConnection() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            // Updated with your server IP and credentials
            String url = "jdbc:sqlserver://your server ip:1433;databaseName=DBname;encrypt=true;trustServerCertificate=true";
            Connection con = DriverManager.getConnection(url, "Username", "Password");
            return con;
        } catch (Exception e) {
            System.out.println("Database Connection Error: " + e);
            return null;
        }
    }
}