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
    private JButton backButton; // Back button
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
        backButton = new JButton("Back to Home"); // Back button

        buttonPanel.add(inventoryReportButton);
        buttonPanel.add(orderReportButton);
        buttonPanel.add(supplierReportButton);
        buttonPanel.add(backButton);

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

        // Action Listener for Back button
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Close the current frame
                new HomePage(); // Navigate back to the HomePage
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
            // Updated query with the new columns
            String query = "SELECT OrderID, OrderDate, product_id, Status, OrderQuantity, TotalAmount FROM Orders";
            try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    int orderId = rs.getInt("OrderID");
                    Date orderDate = rs.getDate("OrderDate");
                    int productId = rs.getInt("product_id");  // updated column name
                    String status = rs.getString("Status");
                    int orderQuantity = rs.getInt("OrderQuantity");  // updated column name
                    double totalAmount = rs.getDouble("TotalAmount");

                    // Adjust the report format to include the new columns
                    report.append(String.format("Order ID: %d, Date: %s, Product ID: %d, Status: %s, Quantity: %d, Total Amount: %.2f\n",
                            orderId, orderDate, productId, status, orderQuantity, totalAmount));
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(frame, "Error generating report: " + e.getMessage(),
                        "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Error connecting to database: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }

        // Display the updated report in the text area
        reportArea.setText(report.toString());
    }

    private void generateSupplierReport() {
        StringBuilder report = new StringBuilder();
        report.append("Supplier Report\n\n");

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT SupplierID, Name, ContactInfo FROM Suppliers";
            try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    int supplierId = rs.getInt("SupplierID");
                    String name = rs.getString("Name");
                    String contactInfo = rs.getString("ContactInfo");
                    report.append(String.format("Supplier ID: %d, Name: %s, Contact Info: %s\n",
                            supplierId, name, contactInfo));
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
