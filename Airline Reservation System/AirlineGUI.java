import java.awt.*;
import java.sql.*;
import javax.swing.*;
import java.util.ArrayList;

public class AirlineGUI {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Airline Reservation System");
        frame.setSize(500, 450);
        frame.setLayout(new GridLayout(9,2));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JTextField name = new JTextField();
        JTextField date = new JTextField();
        JComboBox<String> flightBox = new JComboBox<>();
        try {
            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT flightName FROM flights");

            while(rs.next()) {
                flightBox.addItem(rs.getString(1));
            }
        } catch(Exception e) {
            System.out.println(e);
        }
        JButton bookBtn = new JButton("Book Ticket");
        JButton cancelBtn = new JButton("Cancel Ticket");
        JButton searchBtn = new JButton("Search");
        JButton viewBtn = new JButton("View All");
        JButton adminBtn = new JButton("Admin Login"); 
        JTextArea output = new JTextArea();
        JScrollPane scroll = new JScrollPane(output);
        // UI Layout
        frame.add(new JLabel("Passenger Name"));
        frame.add(name);
        frame.add(new JLabel("Flight"));
        frame.add(flightBox); // FIXED
        frame.add(new JLabel("Date"));
        frame.add(date);
        frame.add(bookBtn);
        frame.add(cancelBtn);
        frame.add(searchBtn);
        frame.add(viewBtn);
        frame.add(adminBtn);
        frame.add(new JLabel(""));
        frame.add(new JLabel("Output"));
        frame.add(scroll);
        Booking booking = new Booking();
        bookBtn.addActionListener(e -> {
            booking.addBooking(
                name.getText(),
                flightBox.getSelectedItem().toString(),
                date.getText(),
                "Booked"
            );
            output.setText("Booking Successful!");
        });
        cancelBtn.addActionListener(e -> {
            booking.cancelBooking(name.getText());
            output.setText("Booking Cancelled!");
        });
        searchBtn.addActionListener(e -> {
            ArrayList<String> list = booking.searchBooking(name.getText());
            output.setText("");

            for(String s : list) {
                output.append(s + "\n");
            }
        });
        viewBtn.addActionListener(e -> {
            ArrayList<String> list = booking.getAllBookings();
            output.setText("");

            for(String s : list) {
                output.append(s + "\n");
            }
        });
        adminBtn.addActionListener(e -> {

            JTextField user = new JTextField();
            JPasswordField pass = new JPasswordField();
            Object[] message = {
                "Username:", user,
                "Password:", pass,
            };
            int option = JOptionPane.showConfirmDialog(
                frame,
                message,
                  "Admin Login",
                JOptionPane.OK_CANCEL_OPTION
            );
            if(option == JOptionPane.OK_OPTION) {
                try {
                    Connection con = DBConnection.getConnection();
                    String q = "SELECT * FROM admin WHERE username=? AND password=?";
                    PreparedStatement ps = con.prepareStatement(q);
                    ps.setString(1, user.getText());
                    ps.setString(2, new String(pass.getPassword()));
                    ResultSet rs = ps.executeQuery();
                    if(rs.next()) {
                        JOptionPane.showMessageDialog(frame, "Login Success");
                        new AdminPanel();
                    } else {
                        JOptionPane.showMessageDialog(frame, "Invalid Login");
                    }
                } catch(Exception ex) {
                    System.out.println(ex);
                }
            }
        });
        frame.setVisible(true);
    }
}