import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ReportsPage {

    private JFrame frame;
    private JPanel mainPanel;
    private JButton inventoryReportButton;
    private JButton orderReportButton;
    private JButton supplierReportButton;
    private JTextArea reportArea;

    public ReportsPage() {
        // Create the frame
        frame = new JFrame("Reports Page");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        // Main panel
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        frame.add(mainPanel);

        // Title label
        JLabel titleLabel = new JLabel("Generate Reports", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Text area for report display
        reportArea = new JTextArea();
        reportArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(reportArea);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Buttons for each report
        inventoryReportButton = new JButton("Generate Inventory Report");
        orderReportButton = new JButton("Generate Order Report");
        supplierReportButton = new JButton("Generate Supplier Report");

        buttonPanel.add(inventoryReportButton);
        buttonPanel.add(orderReportButton);
        buttonPanel.add(supplierReportButton);

        // Action Listeners for each report button
        inventoryReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateInventoryReport();
            }
        });

        orderReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateOrderReport();
            }
        });

        supplierReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateSupplierReport();
            }
        });

        // Show frame
        frame.setVisible(true);
    }

    private void generateInventoryReport() {
        StringBuilder report = new StringBuilder();
        report.append("Product Inventory Report\n\n");

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT product_id, name, quantity, supplier_id FROM products";
            try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    int productId = rs.getInt("product_id");
                    String name = rs.getString("name");
                    int quantity = rs.getInt("quantity");
                    int supplierId = rs.getInt("supplier_id");
                    report.append(String.format("Product ID: %d, Name: %s, Quantity: %d, Supplier ID: %d\n",
                            productId, name, quantity, supplierId));
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(frame, "Error generating report: " + e.getMessage(),
                        "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Error connecting to database: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }

        // Display report in text area
        reportArea.setText(report.toString());
    }

    private void generateOrderReport() {
        StringBuilder report = new StringBuilder();
        report.append("Order Report\n\n");

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT OrderID, OrderDate, SupplierID, Status, TotalAmount FROM Orders";
            try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    int orderId = rs.getInt("OrderID");
                    Date orderDate = rs.getDate("OrderDate");
                    int supplierId = rs.getInt("SupplierID");
                    String status = rs.getString("Status");
                    double totalAmount = rs.getDouble("TotalAmount");
                    report.append(String.format("Order ID: %d, Date: %s, Supplier ID: %d, Status: %s, Total Amount: %.2f\n",
                            orderId, orderDate, supplierId, status, totalAmount));
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(frame, "Error generating report: " + e.getMessage(),
                        "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Error connecting to database: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }

        // Display report in text area
        reportArea.setText(report.toString());
    }

    private void generateSupplierReport() {
        StringBuilder report = new StringBuilder();
        report.append("Supplier Report\n\n");

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT SupplierID, Name, ProductSupply FROM Suppliers";
            try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    int supplierId = rs.getInt("SupplierID");
                    String name = rs.getString("Name");
                    String productSupply = rs.getString("ProductSupply");
                    report.append(String.format("Supplier ID: %d, Name: %s, Product Supply: %s\n",
                            supplierId, name, productSupply));
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(frame, "Error generating report: " + e.getMessage(),
                        "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Error connecting to database: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }

        // Display report in text area
        reportArea.setText(report.toString());
    }

    public static void main(String[] args) {
        // Run the application
        SwingUtilities.invokeLater(ReportsPage::new);
    }
}
