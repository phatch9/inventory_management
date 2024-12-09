import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManageOrders {

    private JFrame frame;
    private JPanel mainPanel;
    private JTable orderTable;
    private DefaultTableModel tableModel;
    private JButton addButton, updateButton, deleteButton, backButton;

    public ManageOrders() {
        // Create the frame
        frame = new JFrame("Manage Orders");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        // Main panel
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        frame.add(mainPanel);

        // Title label
        JLabel titleLabel = new JLabel("Manage Orders", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Table for displaying orders
        String[] columnNames = {"Order ID", "Order Date", "Supplier ID", "Status", "Total Amount"};
        tableModel = new DefaultTableModel(columnNames, 0); // Empty table initially
        orderTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(orderTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        addButton = new JButton("Add Order");
        updateButton = new JButton("Update Order");
        deleteButton = new JButton("Delete Order");
        backButton = new JButton("Back");

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(backButton);

        // Add sample data to the table (for testing purposes)
        tableModel.addRow(new Object[]{"1", "2024-12-01", "001", "Pending", "150"});
        tableModel.addRow(new Object[]{"2", "2024-12-05", "002", "Completed", "200"});

        // Action Listeners
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addOrder();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateOrder();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteOrder();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Close Manage Orders window
                
            }
        });

        // Show frame
        frame.setVisible(true);
    }

    private void addOrder() {
        // Open a dialog to input new order details
        JTextField idField = new JTextField();
        JTextField dateField = new JTextField();
        JTextField supplierField = new JTextField();
        JTextField statusField = new JTextField();
        JTextField totalField = new JTextField();

        Object[] message = {
                "Order ID:", idField,
                "Order Date (YYYY-MM-DD):", dateField,
                "Supplier ID:", supplierField,
                "Status:", statusField,
                "Total Amount:", totalField
        };

        int option = JOptionPane.showConfirmDialog(frame, message, "Add Order", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            String id = idField.getText();
            String date = dateField.getText();
            String supplier = supplierField.getText();
            String status = statusField.getText();
            String total = totalField.getText();

            // Add the new order to the table
            tableModel.addRow(new Object[]{id, date, supplier, status, total});
        }
    }

    private void updateOrder() {
        // Ensure a row is selected
        int selectedRow = orderTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Select an order to update.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Get the current values of the selected order
        String id = tableModel.getValueAt(selectedRow, 0).toString();
        String date = tableModel.getValueAt(selectedRow, 1).toString();
        String supplier = tableModel.getValueAt(selectedRow, 2).toString();
        String status = tableModel.getValueAt(selectedRow, 3).toString();
        String total = tableModel.getValueAt(selectedRow, 4).toString();

        // Open a dialog to update order details
        JTextField idField = new JTextField(id);
        idField.setEditable(false); // ID cannot be changed
        JTextField dateField = new JTextField(date);
        JTextField supplierField = new JTextField(supplier);
        JTextField statusField = new JTextField(status);
        JTextField totalField = new JTextField(total);

        Object[] message = {
                "Order ID:", idField,
                "Order Date (YYYY-MM-DD):", dateField,
                "Supplier ID:", supplierField,
                "Status:", statusField,
                "Total Amount:", totalField
        };

        int option = JOptionPane.showConfirmDialog(frame, message, "Update Order", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            // Update the order details in the table
            tableModel.setValueAt(dateField.getText(), selectedRow, 1);
            tableModel.setValueAt(supplierField.getText(), selectedRow, 2);
            tableModel.setValueAt(statusField.getText(), selectedRow, 3);
            tableModel.setValueAt(totalField.getText(), selectedRow, 4);
        }
    }

    private void deleteOrder() {
        // Ensure a row is selected
        int selectedRow = orderTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Please select an order to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Confirm deletion
        int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to delete this order?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            // Remove the selected order from the table
            tableModel.removeRow(selectedRow);
        }
    }

    public static void main(String[] args) {
        // Run the application
        SwingUtilities.invokeLater(() -> new ManageOrders());
    }
}
