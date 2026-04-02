import java.sql.*;
import java.util.ArrayList;

public class Booking {
    public void addBooking(String name, String flight, String date, String status) {
        try {
            Connection con = DBConnection.getConnection();
            String query = "INSERT INTO booking VALUES (?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, name);
            ps.setString(2, flight);
            ps.setString(3, date);
            ps.setString(4, status);
            ps.executeUpdate();
            System.out.println("Booking Stored");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public ArrayList<String> searchBooking(String name) {
        ArrayList<String> list = new ArrayList<>();
           try {
            Connection con = DBConnection.getConnection();
            String q = "SELECT * FROM booking WHERE passengerName=?";
            PreparedStatement ps = con.prepareStatement(q);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
        while (rs.next()) {
                list.add(
                    rs.getString("passengerName") + " | " +
                    rs.getString("flightName") + " | " +
                    rs.getString("date") + " | " +
                    rs.getString("status")
                );
            }
    } catch (Exception e) {
            System.out.println(e);
        }

        return list;
    }
    public void cancelBooking(String name) {
        try {
            Connection con = DBConnection.getConnection();

            String q = "UPDATE booking SET status='Cancelled' WHERE passengerName=?";
            PreparedStatement ps = con.prepareStatement(q);
            ps.setString(1, name);
            ps.executeUpdate();
            } catch (Exception e) {
            System.out.println(e);
        }
    }
    public ArrayList<String> getAllBookings() {
        ArrayList<String> list = new ArrayList<>();
       try {
            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM booking");
            while (rs.next()) {
                list.add(
                    rs.getString("passengerName") + " | " +
                    rs.getString("flightName") + " | " +
                    rs.getString("date") + " | " +
                    rs.getString("status")
                );
            }
            } catch (Exception e) {
            System.out.println(e);
        }

        return list;
    }
}