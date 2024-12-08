import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        String[] columnNames = {"Product ID", "Name", "Description", "Quantity"};
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

        // Add sample data to the table (for testing purposes)
        tableModel.addRow(new Object[]{"1", "Laptop", "A high-performance laptop", "10"});
        tableModel.addRow(new Object[]{"2", "Mouse", "Wireless mouse", "50"});

        // Action Listeners
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addProduct();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateProduct();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteProduct();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Close Manage Products window
            }
        });

        // Show frame
        frame.setVisible(true);
    }

    private void addProduct() {
        // Open a dialog to input new product details
        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField descriptionField = new JTextField();
        JTextField quantityField = new JTextField();

        Object[] message = {
                "Product ID:", idField,
                "Name:", nameField,
                "Description:", descriptionField,
                "Quantity:", quantityField
        };

        int option = JOptionPane.showConfirmDialog(frame, message, "Add Product", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            String id = idField.getText();
            String name = nameField.getText();
            String description = descriptionField.getText();
            String quantity = quantityField.getText();

            // Add the new product to the table
            tableModel.addRow(new Object[]{id, name, description, quantity});
        }
    }

    private void updateProduct() {
        // Ensure a row is selected
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Please select a product to update.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Get the current values of the selected product
        String id = tableModel.getValueAt(selectedRow, 0).toString();
        String name = tableModel.getValueAt(selectedRow, 1).toString();
        String description = tableModel.getValueAt(selectedRow, 2).toString();
        String quantity = tableModel.getValueAt(selectedRow, 3).toString();

        // Open a dialog to update product details
        JTextField idField = new JTextField(id);
        idField.setEditable(false); // ID cannot be changed
        JTextField nameField = new JTextField(name);
        JTextField descriptionField = new JTextField(description);
        JTextField quantityField = new JTextField(quantity);

        Object[] message = {
                "Product ID:", idField,
                "Name:", nameField,
                "Description:", descriptionField,
                "Quantity:", quantityField
        };

        int option = JOptionPane.showConfirmDialog(frame, message, "Update Product", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            // Update the product details in the table
            tableModel.setValueAt(nameField.getText(), selectedRow, 1);
            tableModel.setValueAt(descriptionField.getText(), selectedRow, 2);
            tableModel.setValueAt(quantityField.getText(), selectedRow, 3);
        }
    }

    private void deleteProduct() {
        // Ensure a row is selected
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Please select a product to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Confirm deletion
        int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to delete this product?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            // Remove the selected product from the table
            tableModel.removeRow(selectedRow);
        }
    }

    public static void main(String[] args) {
        // Run the application
        SwingUtilities.invokeLater(() -> new ManageProducts());
    }
}
