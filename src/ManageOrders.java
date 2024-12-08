
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
    private JTextField txtOrderID, txtOrderDate, txtSupplierID, txtStatus, txtTotalAmount;

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

        // Form Panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(10, 2, 10, 10));

        formPanel.add(new JLabel("Order ID:"));
        txtOrderID = new JTextField();
        formPanel.add(txtOrderID);

        formPanel.add(new JLabel("Order Date:"));
        txtOrderDate = new JTextField();
        formPanel.add(txtOrderDate);

        formPanel.add(new JLabel("Supplier ID:"));
        txtSupplierID = new JTextField();
        formPanel.add(txtSupplierID);

        formPanel.add(new JLabel("Status:"));
        txtStatus = new JTextField();
        formPanel.add(txtStatus);

        formPanel.add(new JLabel("Total Amount:"));
        txtTotalAmount = new JTextField();
        formPanel.add(txtTotalAmount);

        // Buttons Panel
        JPanel buttonPanel = new JPanel();
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
        frame.add(formPanel, BorderLayout.EAST);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void addOrder() {
        String orderID = txtOrderID.getText();
        String orderDate = txtOrderDate.getText();
        String supplierID = txtSupplierID.getText();
        String status = txtStatus.getText();
        String totalAmount = txtTotalAmount.getText();

        tableModel.addRow(new Object[]{orderID, orderDate, supplierID, status, totalAmount});
        clearFields();
    }

    private void editOrder() {
        int selectedRow = orderTable.getSelectedRow();
        if (selectedRow >= 0) {
            tableModel.setValueAt(txtOrderID.getText(), selectedRow, 0);
            tableModel.setValueAt(txtOrderDate.getText(), selectedRow, 1);
            tableModel.setValueAt(txtSupplierID.getText(), selectedRow, 2);
            tableModel.setValueAt(txtStatus.getText(), selectedRow, 3);
            tableModel.setValueAt(txtTotalAmount.getText(), selectedRow, 4);
            clearFields();
        } else {
            JOptionPane.showMessageDialog(frame, "Please select an order to edit");
        }
    }

    private void deleteOrder() {
        int selectedRow = orderTable.getSelectedRow();
        if (selectedRow >= 0) {
            tableModel.removeRow(selectedRow);
        } else {
            JOptionPane.showMessageDialog(frame, "Please select an order to delete");
        }
    }

    private void clearFields() {
        txtOrderID.setText("");
        txtOrderDate.setText("");
        txtSupplierID.setText("");
        txtStatus.setText("");
        txtTotalAmount.setText("");
    }

    public static void main(String[] args) {
        new ManageOrders();
    }
}
