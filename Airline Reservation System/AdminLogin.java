import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class AdminLogin {

    public static void main(String[] args) {

        JFrame f = new JFrame("Admin Login");
        f.setSize(300,200);
        f.setLayout(new GridLayout(3,2));

        JTextField user = new JTextField();
        JPasswordField pass = new JPasswordField();
        JButton login = new JButton("Login");

        f.add(new JLabel("Username"));
        f.add(user);
        f.add(new JLabel("Password"));
        f.add(pass);
        f.add(new JLabel(""));
        f.add(login);

        login.addActionListener(e -> {
            try {
                Connection con = DBConnection.getConnection();

                String q = "SELECT * FROM admin WHERE username=? AND password=?";
                PreparedStatement ps = con.prepareStatement(q);

                ps.setString(1, user.getText());
                ps.setString(2, new String(pass.getPassword()));

                ResultSet rs = ps.executeQuery();

                if(rs.next()) {
                    JOptionPane.showMessageDialog(f,"Login Success");
                    new AdminPanel();
                    f.dispose();
                } else {
                    JOptionPane.showMessageDialog(f,"Invalid Login");
                }

            } catch(Exception ex) {
                System.out.println(ex);
            }
        });

        f.setVisible(true);
    }
}