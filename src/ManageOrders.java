import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ManageOrders {
    private JFrame frame;
    private JTable orderTable;
    private DefaultTableModel tableModel;

    public ManageOrders() {
        // Frame settings
        frame = new JFrame("Manage Orders");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        // Table Model
        String[] columnNames = {"OrderID", "OrderDate", "SupplierID", "Status", "TotalAmount"};
        tableModel = new DefaultTableModel(columnNames, 0);
        orderTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(orderTable);

        // Buttons Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));  // Center align buttons
        JButton btnAddOrder = new JButton("Add Order");
        JButton btnEditOrder = new JButton("Edit Order");
        JButton btnDeleteOrder = new JButton("Delete Order");

        buttonPanel.add(btnAddOrder);
        buttonPanel.add(btnEditOrder);
        buttonPanel.add(btnDeleteOrder);

        // Button Actions
        btnAddOrder.addActionListener(e -> addOrder());
        btnEditOrder.addActionListener(e -> editOrder());
        btnDeleteOrder.addActionListener(e -> deleteOrder());

        // Adding components to the frame
        frame.add(tableScrollPane, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Load data from database into table
        loadOrdersFromDatabase();

        frame.setVisible(true);
    }

    // Load orders from the database
    private void loadOrdersFromDatabase() {
        try {
            String query = "SELECT * FROM orders";
            ResultSet rs = DatabaseConnection.executeQuery(query);
            while (rs.next()) {
                String orderID = rs.getString("OrderID");
                String orderDate = rs.getString("OrderDate");
                String supplierID = rs.getString("SupplierID");
                String status = rs.getString("Status");
                String totalAmount = rs.getString("TotalAmount");

                // Add row to the table
                tableModel.addRow(new Object[]{orderID, orderDate, supplierID, status, totalAmount});
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error loading orders from database", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Add order to the database
    private void addOrder() {
        String orderID = JOptionPane.showInputDialog("Enter Order ID");
        String orderDate = JOptionPane.showInputDialog("Enter Order Date (yyyy-MM-dd)");
        String supplierID = JOptionPane.showInputDialog("Enter Supplier ID");
        String status = JOptionPane.showInputDialog("Enter Status");
        String totalAmount = JOptionPane.showInputDialog("Enter Total Amount");

        if (orderID != null && orderDate != null && supplierID != null && status != null && totalAmount != null) {
            String query = "INSERT INTO orders (OrderID, OrderDate, SupplierID, Status, TotalAmount) VALUES (?, ?, ?, ?, ?)";
            try {
                int rowsAffected = DatabaseConnection.executePreparedUpdate(query, orderID, orderDate, supplierID, status, totalAmount);
                if (rowsAffected > 0) {
                    tableModel.addRow(new Object[]{orderID, orderDate, supplierID, status, totalAmount});
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Error adding order to database", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Edit selected order in the database
    private void editOrder() {
        int selectedRow = orderTable.getSelectedRow();
        if (selectedRow >= 0) {
            String orderID = (String) tableModel.getValueAt(selectedRow, 0);
            String orderDate = JOptionPane.showInputDialog("Edit Order Date (yyyy-MM-dd)", tableModel.getValueAt(selectedRow, 1));
            String supplierID = JOptionPane.showInputDialog("Edit Supplier ID", tableModel.getValueAt(selectedRow, 2));
            String status = JOptionPane.showInputDialog("Edit Status", tableModel.getValueAt(selectedRow, 3));
            String totalAmount = JOptionPane.showInputDialog("Edit Total Amount", tableModel.getValueAt(selectedRow, 4));

            if (orderDate != null && supplierID != null && status != null && totalAmount != null) {
                String query = "UPDATE orders SET OrderDate = ?, SupplierID = ?, Status = ?, TotalAmount = ? WHERE OrderID = ?";
                try {
                    int rowsAffected = DatabaseConnection.executePreparedUpdate(query, orderDate, supplierID, status, totalAmount, orderID);
                    if (rowsAffected > 0) {
                        tableModel.setValueAt(orderDate, selectedRow, 1);
                        tableModel.setValueAt(supplierID, selectedRow, 2);
                        tableModel.setValueAt(status, selectedRow, 3);
                        tableModel.setValueAt(totalAmount, selectedRow, 4);
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

    // Delete selected order from the database
    private void deleteOrder() {
        int selectedRow = orderTable.getSelectedRow();
        if (selectedRow >= 0) {
            String orderID = (String) tableModel.getValueAt(selectedRow, 0);
            String query = "DELETE FROM orders WHERE OrderID = ?";
            try {
                int rowsAffected = DatabaseConnection.executePreparedUpdate(query, orderID);
                if (rowsAffected > 0) {
                    tableModel.removeRow(selectedRow);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Error deleting order from database", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Please select an order to delete");
        }
    }

    public static void main(String[] args) {
        new ManageOrders();
    }
}
