import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class OrderManager {

    private JFrame frame;
    private JTable orderTable;
    private DefaultTableModel tableModel;

    public OrderManager() {
        frame = new JFrame("Order Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        // Table model and table initialization
        tableModel = new DefaultTableModel(new String[]{"Order ID", "Order Date", "Product ID", "Status", "Order Quantity", "Total Amount"}, 0);
        orderTable = new JTable(tableModel);

        // Scroll pane for table
        JScrollPane scrollPane = new JScrollPane(orderTable);
        frame.add(scrollPane);

        // Buttons
        JButton addButton = new JButton("Add Order");
        addButton.addActionListener(e -> addOrder());
        JButton editButton = new JButton("Edit Order");
        editButton.addActionListener(e -> editOrder());

        // Back button to navigate to HomePage
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> navigateToHomePage());

        JPanel panel = new JPanel();
        panel.add(addButton);
        panel.add(editButton);
        panel.add(backButton);  // Add the Back button to the panel
        frame.add(panel, "South");

        frame.setVisible(true);

        // Load existing orders into the table
        loadOrders();
    }

    // Load Orders from database into the table
    private void loadOrders() {
        String query = "SELECT OrderID, OrderDate, product_id, Status, OrderQuantity, TotalAmount FROM orders";
        try {
            ResultSet rs = DatabaseConnection.executeQuery(query);
            while (rs.next()) {
                String orderID = rs.getString("OrderID");
                String orderDate = rs.getString("OrderDate");
                String productID = rs.getString("product_id");
                String status = rs.getString("Status");
                String orderQuantity = rs.getString("OrderQuantity");
                String totalAmount = rs.getString("TotalAmount");

                tableModel.addRow(new Object[]{orderID, orderDate, productID, status, orderQuantity, totalAmount});
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error loading orders from database", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Add Order method
    private void addOrder() {
        String orderID = JOptionPane.showInputDialog("Enter Order ID");
        String orderDate = JOptionPane.showInputDialog("Enter Order Date (yyyy-MM-dd)");
        String productID = JOptionPane.showInputDialog("Enter Product ID");
        String status = JOptionPane.showInputDialog("Enter Status");
        String orderQuantity = JOptionPane.showInputDialog("Enter Order Quantity");
        String totalAmount = JOptionPane.showInputDialog("Enter Total Amount");

        if (orderID != null && orderDate != null && productID != null && status != null && orderQuantity != null && totalAmount != null) {
            String query = "INSERT INTO orders (OrderID, OrderDate, product_id, Status, OrderQuantity, TotalAmount) VALUES (?, ?, ?, ?, ?, ?)";
            try {
                int rowsAffected = DatabaseConnection.executePreparedUpdate(query, orderID, orderDate, productID, status, orderQuantity, totalAmount);
                if (rowsAffected > 0) {
                    // Add row to the order table
                    tableModel.addRow(new Object[]{orderID, orderDate, productID, status, orderQuantity, totalAmount});

                    // Update the product quantity in the products table
                    String updateProductQuery = "UPDATE products SET quantity = quantity - ? WHERE product_id = ?";
                    int updatedRows = DatabaseConnection.executePreparedUpdate(updateProductQuery, Integer.parseInt(orderQuantity), productID);

                    // Log the result to confirm the update
                    if (updatedRows > 0) {
                        System.out.println("Product quantity updated successfully!");
                    } else {
                        System.out.println("Error: Product quantity not updated.");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Error adding order to database", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Edit Order method
    private void editOrder() {
        int selectedRow = orderTable.getSelectedRow();
        if (selectedRow >= 0) {
            String orderID = (String) tableModel.getValueAt(selectedRow, 0);
            String orderDate = JOptionPane.showInputDialog("Edit Order Date (yyyy-MM-dd)", tableModel.getValueAt(selectedRow, 1));
            String productID = JOptionPane.showInputDialog("Edit Product ID", tableModel.getValueAt(selectedRow, 2));
            String status = JOptionPane.showInputDialog("Edit Status", tableModel.getValueAt(selectedRow, 3));
            String orderQuantity = JOptionPane.showInputDialog("Edit Order Quantity", tableModel.getValueAt(selectedRow, 4));
            String totalAmount = JOptionPane.showInputDialog("Edit Total Amount", tableModel.getValueAt(selectedRow, 5));

            if (orderDate != null && productID != null && status != null && orderQuantity != null && totalAmount != null) {
                String query = "UPDATE orders SET OrderDate = ?, product_id = ?, Status = ?, OrderQuantity = ?, TotalAmount = ? WHERE OrderID = ?";
                try {
                    // Get the current order quantity before update
                    int currentOrderQuantity = Integer.parseInt((String) tableModel.getValueAt(selectedRow, 4));

                    // Perform update on the order
                    int rowsAffected = DatabaseConnection.executePreparedUpdate(query, orderDate, productID, status, orderQuantity, totalAmount, orderID);
                    if (rowsAffected > 0) {
                        // Update the product quantity in the products table
                        String updateProductQuery = "UPDATE products SET quantity = quantity + ? - ? WHERE product_id = ?";
                        int updatedRows = DatabaseConnection.executePreparedUpdate(updateProductQuery, currentOrderQuantity, Integer.parseInt(orderQuantity), productID);

                        // Log the result to confirm the update
                        if (updatedRows > 0) {
                            System.out.println("Product quantity updated successfully!");
                        } else {
                            System.out.println("Error: Product quantity not updated.");
                        }

                        // Update the table model
                        tableModel.setValueAt(orderDate, selectedRow, 1);
                        tableModel.setValueAt(productID, selectedRow, 2);
                        tableModel.setValueAt(status, selectedRow, 3);
                        tableModel.setValueAt(orderQuantity, selectedRow, 4);
                        tableModel.setValueAt(totalAmount, selectedRow, 5);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error editing order in database", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Please select an order to edit");
        }
    }

    // Navigate to HomePage method
    private void navigateToHomePage() {
        // Assuming HomePage is a separate class and can be instantiated here
        frame.setVisible(false); // Hide the current frame
        new HomePage(); // Show the HomePage (or you could call setVisible(true) for an existing instance of HomePage)
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new OrderManager());
    }
}
