import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class CanteenGUI {
    private static JComboBox<String> itemBox = new JComboBox<>();
    private static DefaultTableModel tableModel;
    private static JTable cartTable;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Smart Canteen - Advanced Billing");
        frame.setSize(600, 700);
        frame.setLayout(new BorderLayout(10, 10));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // --- TOP PANEL: Customer Info & Item Selection ---
        JPanel topPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        JTextField nameField = new JTextField();
        JButton addBtn = new JButton("Add to Cart");
        
        loadItems(); // Populate dropdown

        topPanel.add(new JLabel(" Customer Name:")); topPanel.add(nameField);
        topPanel.add(new JLabel(" Select Item:")); topPanel.add(itemBox);
        topPanel.add(new JLabel("")); topPanel.add(addBtn);

        // --- MIDDLE PANEL: The Cart Table ---
        // Columns: Item Name, Price, Quantity
        String[] columns = {"Item Name", "Price", "Quantity"};
        tableModel = new DefaultTableModel(columns, 0);
        cartTable = new JTable(tableModel);
        JScrollPane tableScroll = new JScrollPane(cartTable);

        // --- BOTTOM PANEL: Actions & Receipt ---
        JPanel bottomPanel = new JPanel(new BorderLayout());
        JPanel btnPanel = new JPanel(new GridLayout(1, 3));
        JButton billBtn = new JButton("Generate Final Bill");
        JButton refreshBtn = new JButton("Refresh Menu");
        JButton adminBtn = new JButton("Admin Portal");
        
        btnPanel.add(billBtn); btnPanel.add(refreshBtn); btnPanel.add(adminBtn);

        JTextArea receiptArea = new JTextArea(12, 20);
        receiptArea.setEditable(false);

        bottomPanel.add(btnPanel, BorderLayout.NORTH);
        bottomPanel.add(new JScrollPane(receiptArea), BorderLayout.CENTER);

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(tableScroll, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        // ACTION: ADD TO CART
        addBtn.addActionListener(e -> {
            String selected = (String) itemBox.getSelectedItem();
            if (selected != null) {
                try {
                    Connection con = DBConnection.getConnection();
                    PreparedStatement ps = con.prepareStatement("SELECT price FROM menu WHERE itemName=?");
                    ps.setString(1, selected);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        // Add row to table: Item Name, Price, Default Qty 1
                        tableModel.addRow(new Object[]{selected, rs.getDouble("price"), "1"});
                    }
                } catch (Exception ex) { ex.printStackTrace(); }
            }
        });

        // ACTION: GENERATE FINAL BILL
        billBtn.addActionListener(e -> {
            try {
                Connection con = DBConnection.getConnection();
                String custName = nameField.getText();
                double grandTotal = 0;
                StringBuilder summary = new StringBuilder("--- FINAL RECEIPT ---\nCustomer: " + custName + "\n\n");

                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    String itemName = tableModel.getValueAt(i, 0).toString();
                    double price = Double.parseDouble(tableModel.getValueAt(i, 1).toString());
                    int qty = Integer.parseInt(tableModel.getValueAt(i, 2).toString()); // User-entered qty

                    // Check stock in DB
                    PreparedStatement ps = con.prepareStatement("SELECT stock FROM menu WHERE itemName=?");
                    ps.setString(1, itemName);
                    ResultSet rs = ps.executeQuery();

                    if (rs.next() && rs.getInt("stock") >= qty) {
                        double itemTotal = price * qty;
                        grandTotal += itemTotal;

                        // Insert Bill
                        PreparedStatement ins = con.prepareStatement("INSERT INTO billing (customerName, itemName, quantity, totalPrice) VALUES (?,?,?,?)");
                        ins.setString(1, custName);
                        ins.setString(2, itemName);
                        ins.setInt(3, qty);
                        ins.setDouble(4, itemTotal);
                        ins.executeUpdate();

                        // Update Stock
                        PreparedStatement upd = con.prepareStatement("UPDATE menu SET stock = stock - ? WHERE itemName = ?");
                        upd.setInt(1, qty);
                        upd.setString(2, itemName);
                        upd.executeUpdate();

                        summary.append(itemName + " x" + qty + " = ₹" + itemTotal + "\n");
                    } else {
                        summary.append("[FAILED] " + itemName + " - Insufficient Stock\n");
                    }
                }

                summary.append("\n----------------------\nGRAND TOTAL: ₹" + grandTotal);
                receiptArea.setText(summary.toString());
                tableModel.setRowCount(0); // Clear cart after billing
                loadItems(); 

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Ensure all quantities are numbers!");
            }
        });

        refreshBtn.addActionListener(e -> loadItems());
        adminBtn.addActionListener(e -> new AdminLogin());
        frame.setVisible(true);
    }

    public static void loadItems() {
        try {
            itemBox.removeAllItems();
            Connection con = DBConnection.getConnection();
            ResultSet rs = con.createStatement().executeQuery("SELECT itemName FROM menu WHERE stock > 0");
            while (rs.next()) itemBox.addItem(rs.getString(1));
            con.close();
        } catch (Exception e) { e.printStackTrace(); }
    }
}