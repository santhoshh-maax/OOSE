import java.sql.*;

public class DBConnection {

    private static Connection con;

    private DBConnection() {}

    public static Connection getConnection() {
        try {
            if (con == null) {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

                con = DriverManager.getConnection(
                    "jdbc:sqlserver://127.0.0.1:1433;databaseName=airline_db;user=sa;password=1234;encrypt=true;trustServerCertificate=true"
                );

                System.out.println("Connection Created");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return con;
    }
}