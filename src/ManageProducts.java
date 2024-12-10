import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ManageProducts {

    private JFrame frame;
    private JPanel mainPanel;
    private JTable productTable;
    private DefaultTableModel tableModel;
    private JButton addButton, updateButton, deleteButton, backButton;

    public ManageProducts() {
        // Create the frame
        frame = new JFrame("Manage Products");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        // Main panel
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        frame.add(mainPanel);

        // Title label
        JLabel titleLabel = new JLabel("Manage Products", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Table for displaying products
        String[] columnNames = {"Product ID", "Name", "Description", "Quantity", "Supplier ID"};
        tableModel = new DefaultTableModel(columnNames, 0); // Empty table initially
        productTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(productTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        addButton = new JButton("Add Product");
        updateButton = new JButton("Update Product");
        deleteButton = new JButton("Delete Product");
        backButton = new JButton("Back");

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(backButton);

        // Load data from the database
        loadProductsFromDatabase();

        // Action Listeners
        addButton.addActionListener(e -> addProduct());
        updateButton.addActionListener(e -> updateProduct());
        deleteButton.addActionListener(e -> deleteProduct());
        backButton.addActionListener(e -> navigateToHomePage());

        // Show frame
        frame.setVisible(true);
    }

    private void loadProductsFromDatabase() {
        try (ResultSet rs = DatabaseConnection.executeQuery("SELECT * FROM products")) {
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getInt("product_id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getInt("quantity"),
                        rs.getInt("supplier_id")
                });
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Error loading products: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addProduct() {
        // Input dialog for product details
        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField descriptionField = new JTextField();
        JTextField quantityField = new JTextField();
        JTextField supplierIdField = new JTextField();

        Object[] message = {
                "Product ID:", idField,
                "Name:", nameField,
                "Description:", descriptionField,
                "Quantity:", quantityField,
                "Supplier ID:", supplierIdField
        };

        int option = JOptionPane.showConfirmDialog(frame, message, "Add Product", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            try {
                String query = "INSERT INTO products (product_id, name, description, quantity, supplier_id) VALUES (?, ?, ?, ?, ?)";
                DatabaseConnection.executePreparedUpdate(query, Integer.parseInt(idField.getText()),
                        nameField.getText(), descriptionField.getText(), Integer.parseInt(quantityField.getText()),
                        Integer.parseInt(supplierIdField.getText()));

                // Add to table model
                tableModel.addRow(new Object[]{
                        idField.getText(),
                        nameField.getText(),
                        descriptionField.getText(),
                        quantityField.getText(),
                        supplierIdField.getText()
                });

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(frame, "Error adding product: " + e.getMessage(),
                        "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void updateProduct() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Please select a product to update.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Get current product details
        String id = tableModel.getValueAt(selectedRow, 0).toString();
        JTextField nameField = new JTextField(tableModel.getValueAt(selectedRow, 1).toString());
        JTextField descriptionField = new JTextField(tableModel.getValueAt(selectedRow, 2).toString());
        JTextField quantityField = new JTextField(tableModel.getValueAt(selectedRow, 3).toString());
        JTextField supplierIdField = new JTextField(tableModel.getValueAt(selectedRow, 4).toString());

        Object[] message = {
                "Name:", nameField,
                "Description:", descriptionField,
                "Quantity:", quantityField,
                "Supplier ID:", supplierIdField
        };

        int option = JOptionPane.showConfirmDialog(frame, message, "Update Product", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            try {
                String query = "UPDATE products SET name = ?, description = ?, quantity = ?, supplier_id = ? WHERE product_id = ?";
                DatabaseConnection.executePreparedUpdate(query, nameField.getText(), descriptionField.getText(),
                        Integer.parseInt(quantityField.getText()), Integer.parseInt(supplierIdField.getText()),
                        Integer.parseInt(id));

                // Update the table model
                tableModel.setValueAt(nameField.getText(), selectedRow, 1);
                tableModel.setValueAt(descriptionField.getText(), selectedRow, 2);
                tableModel.setValueAt(quantityField.getText(), selectedRow, 3);
                tableModel.setValueAt(supplierIdField.getText(), selectedRow, 4);

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(frame, "Error updating product: " + e.getMessage(),
                        "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deleteProduct() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Please select a product to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String id = tableModel.getValueAt(selectedRow, 0).toString();

        int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to delete this product?",
                "Confirm Deletion", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                String query = "DELETE FROM products WHERE product_id = ?";
                DatabaseConnection.executePreparedUpdate(query, Integer.parseInt(id));

                // Remove from table model
                tableModel.removeRow(selectedRow);

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(frame, "Error deleting product: " + e.getMessage(),
                        "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void navigateToHomePage() {
        // Close the current frame and open the HomePage frame
        frame.dispose();
        new HomePage();
    }

    public static void main(String[] args) {
        // Run the application
        SwingUtilities.invokeLater(ManageProducts::new);
    }
}
