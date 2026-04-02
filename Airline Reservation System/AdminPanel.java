import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class AdminPanel {

    JFrame f;
    JTextArea output;

    public AdminPanel() {

        f = new JFrame("Admin Panel - Airline System");
        f.setSize(600, 450);
        f.setLayout(new GridLayout(6,2));
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTextField flight = new JTextField();
        JTextField source = new JTextField();
        JTextField dest = new JTextField();

        JButton addFlight = new JButton("Add Flight");
        JButton deleteFlight = new JButton("Delete Flight");
        JButton viewFlights = new JButton("View Flights");
        JButton viewBookings = new JButton("View Bookings");

        output = new JTextArea();
        JScrollPane scroll = new JScrollPane(output);

        // UI Layout
        f.add(new JLabel("Flight Name"));
        f.add(flight);

        f.add(new JLabel("Source"));
        f.add(source);

        f.add(new JLabel("Destination"));
        f.add(dest);

        f.add(addFlight);
        f.add(deleteFlight);

        f.add(viewFlights);
        f.add(viewBookings);

        f.add(new JLabel("Output"));
        f.add(scroll);

        //ADD FLIGHT
        addFlight.addActionListener(e -> {
            try {
                Connection con = DBConnection.getConnection();

                String q = "INSERT INTO flights (flightName, source, destination) VALUES (?,?,?)";
                PreparedStatement ps = con.prepareStatement(q);

                ps.setString(1, flight.getText());
                ps.setString(2, source.getText());
                ps.setString(3, dest.getText());

                ps.executeUpdate();

                JOptionPane.showMessageDialog(f, "Flight Added!");

            } catch(Exception ex) {
                System.out.println(ex);
            }
        });

        //DELETE FLIGHT
        deleteFlight.addActionListener(e -> {
            try {
                Connection con = DBConnection.getConnection();

                String q = "DELETE FROM flights WHERE flightName=?";
                PreparedStatement ps = con.prepareStatement(q);

                ps.setString(1, flight.getText());
                ps.executeUpdate();

                JOptionPane.showMessageDialog(f, "Flight Deleted!");

            } catch(Exception ex) {
                System.out.println(ex);
            }
        });

        // VIEW FLIGHTS
        viewFlights.addActionListener(e -> {
            try {
                Connection con = DBConnection.getConnection();
                Statement st = con.createStatement();

                ResultSet rs = st.executeQuery("SELECT * FROM flights");

                output.setText("");

                while(rs.next()) {
                    output.append(
                        rs.getInt("flightId") + " | " +
                        rs.getString("flightName") + " | " +
                        rs.getString("source") + " → " +
                        rs.getString("destination") + "\n"
                    );
                }

            } catch(Exception ex) {
                System.out.println(ex);
            }
        });

        // VIEW BOOKINGS
        viewBookings.addActionListener(e -> {
            try {
                Connection con = DBConnection.getConnection();
                Statement st = con.createStatement();

                ResultSet rs = st.executeQuery("SELECT * FROM booking");

                output.setText("");

                while(rs.next()) {
                    output.append(
                        rs.getInt("id") + " | " +
                        rs.getString("passengerName") + " | " +
                        rs.getString("flightName") + " | " +
                        rs.getString("date") + " | " +
                        rs.getString("status") + "\n"
                    );
                }

            } catch(Exception ex) {
                System.out.println(ex);
            }
        });

        f.setVisible(true);
    }
}