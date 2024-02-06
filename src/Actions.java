import java.sql.*;
import java.util.ArrayList;

public class Actions {
    // Add Operations
    private static Connection con;
    public void add(Student student) {
        try {
            con = JDBC.getConnect();
            String query = "insert into profile(name, address) value(?,?);";
            assert con != null;
            PreparedStatement p = con.prepareStatement(query);
            p.setString(1, student.getName());
            p.setString(2, student.getAddress());
            p.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    public void delete(int id) {
        try {
            con = JDBC.getConnect();
            String query = "DELETE FROM profile WHERE id = ?";
            assert con != null;
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);
            ps.executeUpdate();

            String recalibrationAutoIncrement = "ALTER TABLE profile AUTO_INCREMENT = ?;";
            ps = con.prepareStatement(recalibrationAutoIncrement);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    public void update(int id, int switchExpression, String new_name_or_address) {
        con = JDBC.getConnect();
        String query;
        try {
            // switchExpression = 0 -> Change name
            // switchExpression = 1 -> Change address
            if (switchExpression == 0) {
                query = "update profile set name = ? where id = ?";
            } else if (switchExpression == 1) {
                query = "update profile set address = ? where id = ?";
            } else {
                System.out.println("Enter Valid Switch Expression!");
                return;
            }
            assert con != null;
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, new_name_or_address);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public String[][] fetchData() {
        String[][] all = new String[0][];
        con = JDBC.getConnect();
        String query = "select * from profile;";
        try {
            ArrayList<String[]> allData = new ArrayList<>();
            // create the java statement
            assert con != null;
            Statement st = con.createStatement();

            // execute the query, and get a java resultset
            ResultSet rs = st.executeQuery(query);

            // iterate through the java resultset
            while (rs.next()) {
                int user_id = rs.getInt("id");
                String user_name = rs.getString("name");
                String user_register = rs.getString("register_time");
                String user_address = rs.getString("address");

                String[] data = {String.valueOf(user_id), user_name, user_register, user_address};
                allData.add(data);
            }
            st.close();
            all = allData.toArray(new String[allData.size()][]);

        } catch (SQLException e) {
            System.out.println(e);
        }
        return all;
    }

    public String[] getLast() {
        con = JDBC.getConnect();
        String query = "SELECT * FROM profile ORDER BY ID DESC LIMIT 1;";
        int user_id = 0;
        String user_name = null;
        String user_register = null;
        String user_address = null;
        try {
            assert con != null;
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                user_id = rs.getInt("id");
                user_name = rs.getString("name");
                user_register = rs.getString("register_time");
                user_address = rs.getString("address");
            }

        } catch (SQLException e) {
            System.out.println(e);
        }
        return new String[]{String.valueOf(user_id), user_name, user_register, user_address};
    }

    public int findID(String name, String address) {
        int user_id = -1;
        con = JDBC.getConnect();
        try {
            String query = "SELECT * FROM profile WHERE name = ? and address = ? LIMIT 1;";
            assert con != null;
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, name);
            ps.setString(2, address);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user_id = rs.getInt("id");
            }

        } catch (SQLException e) {
            System.out.println(e);
        }
        return user_id;
    }

    public void deleteAll() {
        con = JDBC.getConnect();
        try {
            String query = "Delete from profile;";
            assert con != null;
            PreparedStatement ps = con.prepareStatement(query);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void refreshTable() {
        con = JDBC.getConnect();
        try {
            String tableQuery = "ALTER TABLE profile AUTO_INCREMENT = 1;";
            assert con != null;
            PreparedStatement ps = con.prepareStatement(tableQuery);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
