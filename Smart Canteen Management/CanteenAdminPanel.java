import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class CanteenAdminPanel {
    public CanteenAdminPanel() {
        JFrame f = new JFrame("Admin - Canteen Management");
        f.setSize(500, 500);
        f.setLayout(new GridLayout(8, 2));

        JTextField itemField = new JTextField();
        JTextField priceField = new JTextField();
        JTextField stockField = new JTextField();
        JTextArea display = new JTextArea();
        
        JButton saveBtn = new JButton("Save / Add Item");
        JButton viewMenuBtn = new JButton("View Menu");
        JButton viewSalesBtn = new JButton("View All Sales");

        f.add(new JLabel(" Item Name:")); f.add(itemField);
        f.add(new JLabel(" Price:")); f.add(priceField);
        f.add(new JLabel(" Initial/Add Stock:")); f.add(stockField);
        f.add(saveBtn); f.add(viewMenuBtn);
        f.add(new JLabel("")); f.add(viewSalesBtn);
        f.add(new JLabel(" Database Logs:")); f.add(new JScrollPane(display));

        // SAVE OR ADD NEW ITEM LOGIC
        saveBtn.addActionListener(e -> {
            try {
                Connection con = DBConnection.getConnection();
                String itemName = itemField.getText();
                double price = Double.parseDouble(priceField.getText());
                int stockCount = Integer.parseInt(stockField.getText());

                // Check if item already exists
                PreparedStatement checkPs = con.prepareStatement("SELECT * FROM menu WHERE itemName=?");
                checkPs.setString(1, itemName);
                ResultSet rs = checkPs.executeQuery();

                if (rs.next()) {
                    // ITEM EXISTS: Update price and add to current stock
                    PreparedStatement upd = con.prepareStatement("UPDATE menu SET price=?, stock = stock + ? WHERE itemName=?");
                    upd.setDouble(1, price);
                    upd.setInt(2, stockCount);
                    upd.setString(3, itemName);
                    upd.executeUpdate();
                    display.setText("Updated existing item: " + itemName);
                } else {
                    // ITEM IS NEW: Insert new row
                    PreparedStatement ins = con.prepareStatement("INSERT INTO menu (itemName, price, stock) VALUES (?,?,?)");
                    ins.setString(1, itemName);
                    ins.setDouble(2, price);
                    ins.setInt(3, stockCount);
                    ins.executeUpdate();
                    display.setText("Added NEW item to menu: " + itemName);
                }
                JOptionPane.showMessageDialog(f, "Menu Updated successfully!");
            } catch (Exception ex) {
                ex.printStackTrace();
                display.setText("Error: " + ex.getMessage());
            }
        });

        // VIEW CURRENT MENU
        viewMenuBtn.addActionListener(e -> {
            try {
                Connection con = DBConnection.getConnection();
                ResultSet rs = con.createStatement().executeQuery("SELECT * FROM menu");
                display.setText("ID | Item | Price | Stock\n--------------------------\n");
                while(rs.next()) {
                    display.append(rs.getInt(1) + " | " + rs.getString(2) + " | " + rs.getDouble(3) + " | " + rs.getInt(4) + "\n");
                }
            } catch(Exception ex) { ex.printStackTrace(); }
        });

        // VIEW ALL SALES
        viewSalesBtn.addActionListener(e -> {
            try {
                Connection con = DBConnection.getConnection();
                ResultSet rs = con.createStatement().executeQuery("SELECT * FROM billing");
                display.setText("Cust | Item | Qty | Total\n--------------------------\n");
                while(rs.next()) {
                    display.append(rs.getString(2) + " | " + rs.getString(3) + " | " + rs.getInt(4) + " | " + rs.getDouble(5) + "\n");
                }
            } catch(Exception ex) { ex.printStackTrace(); }
        });

        f.setVisible(true);
    }
}