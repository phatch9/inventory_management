import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManageSuppliers {

    private JFrame frame;
    private JPanel mainPanel;
    private JTable supplierTable;
    private DefaultTableModel tableModel;
    private JButton addButton, updateButton, deleteButton, backButton;

    public ManageSuppliers() {
        // Create the frame
        frame = new JFrame("Manage Suppliers");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        // Main panel
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        frame.add(mainPanel);

        // Title label
        JLabel titleLabel = new JLabel("Manage Suppliers", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Table for displaying suppliers
        String[] columnNames = {"Supplier ID", "Name", "Contact Info"};
        tableModel = new DefaultTableModel(columnNames, 0); // Empty table initially
        supplierTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(supplierTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        addButton = new JButton("Add Supplier");
        updateButton = new JButton("Update Supplier");
        deleteButton = new JButton("Delete Supplier");
        backButton = new JButton("Back");

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(backButton);

        // Add sample data to the table (for testing purposes)
        tableModel.addRow(new Object[]{"01", "NewDay Supplies", "abc@supplies.com"});
        tableModel.addRow(new Object[]{"02", "Good Traders", "good@gmail.com"});

        // Action Listeners
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addSupplier();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateSupplier();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSupplier();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Close Manage Suppliers window
                // new Homepage(); // Return to the homepage
            }
        });

        // Show frame
        frame.setVisible(true);
    }

    private void addSupplier() {
        // Open a dialog to input new supplier details
        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField contactField = new JTextField();

        Object[] message = {
                "Supplier ID:", idField,
                "Name:", nameField,
                "Contact Info:", contactField
        };

        int option = JOptionPane.showConfirmDialog(frame, message, "Add Supplier", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            String id = idField.getText();
            String name = nameField.getText();
            String contact = contactField.getText();

            // Add the new supplier to the table
            tableModel.addRow(new Object[]{id, name, contact});
        }
    }

    private void updateSupplier() {
        // Ensure a row is selected
        int selectedRow = supplierTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Select a supplier to update.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Get the current values of the selected supplier
        String id = tableModel.getValueAt(selectedRow, 0).toString();
        String name = tableModel.getValueAt(selectedRow, 1).toString();
        String contact = tableModel.getValueAt(selectedRow, 2).toString();

        // Open a dialog to update supplier details
        JTextField idField = new JTextField(id);
        idField.setEditable(false); // ID cannot be changed
        JTextField nameField = new JTextField(name);
        JTextField contactField = new JTextField(contact);

        Object[] message = {
                "Supplier ID:", idField,
                "Name:", nameField,
                "Contact Info:", contactField
        };

        int option = JOptionPane.showConfirmDialog(frame, message, "Update Supplier", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            // Update the supplier details in the table
            tableModel.setValueAt(nameField.getText(), selectedRow, 1);
            tableModel.setValueAt(contactField.getText(), selectedRow, 2);
        }
    }

    private void deleteSupplier() {
        // Ensure a row is selected
        int selectedRow = supplierTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Select a supplier to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Confirm deletion
        int confirm = JOptionPane.showConfirmDialog(frame, "Make sure you want to delete this supplier?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            // Remove the selected supplier from the table
            tableModel.removeRow(selectedRow);
        }
    }

    public static void main(String[] args) {
        // Run the application
        SwingUtilities.invokeLater(() -> new ManageSuppliers());
    }
}
