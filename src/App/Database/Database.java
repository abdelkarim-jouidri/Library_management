package App.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static String url = "jdbc:mysql://localhost:3306/library";
    private static String user = "root";
    private static String password = "";

    private Database() {
    }

    public static Connection getConnection() throws SQLException {
        Connection con = null;
        con = DriverManager.getConnection(url, user, password);

        return con;
    }

}
