import java.sql.*;

public class JDBC {
    private static Connection con;
    public static Connection getConnect() {
        try {
            // Student is db name.
            String url = "jdbc:mysql://localhost:3306/student"; // Please enter your url.
            String user = "root";
            String password = ""; // Please enter your password if you have one:
            Class.forName("com.mysql.cj.jdbc.Driver");
            return con = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
