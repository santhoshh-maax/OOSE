import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class AdminLogin {

    public AdminLogin() {
        JFrame f = new JFrame("Admin Login - Smart Canteen");
        f.setSize(300, 200);
        f.setLayout(new GridLayout(3, 2));
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTextField user = new JTextField();
        JPasswordField pass = new JPasswordField();
        JButton login = new JButton("Login");

        f.add(new JLabel(" Username:"));
        f.add(user);
        f.add(new JLabel(" Password:"));
        f.add(pass);
        f.add(new JLabel(""));
        f.add(login);

        login.addActionListener(e -> {
            try {
                Connection con = DBConnection.getConnection();
                // Querying the canteen_admin table we created in the SQL script
                String q = "SELECT * FROM canteen_admin WHERE username=? AND password=?";
                PreparedStatement ps = con.prepareStatement(q);

                ps.setString(1, user.getText());
                ps.setString(2, new String(pass.getPassword()));

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    JOptionPane.showMessageDialog(f, "Login Success");
                    new CanteenAdminPanel(); // Opens the stock management panel
                    f.dispose();
                } else {
                    JOptionPane.showMessageDialog(f, "Invalid Credentials");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        f.setVisible(true);
    }
}