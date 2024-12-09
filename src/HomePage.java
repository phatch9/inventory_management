import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePage {

    // Main JFrame for the application
    private JFrame frame;
    private JPanel mainPanel;
    private JLabel titleLabel;
    private JButton manageProductsButton;
    private JButton manageOrdersButton;
    private JButton manageSuppliersButton;
    private JButton reportsButton; // New button for Reports
    private JButton exitButton;

    public HomePage() {
        // Initialize components
        frame = new JFrame("Inventory Management System");
        mainPanel = new JPanel();
        titleLabel = new JLabel("Inventory Management System", SwingConstants.CENTER);
        manageProductsButton = new JButton("Manage Products");
        manageOrdersButton = new JButton("Manage Orders");
        manageSuppliersButton = new JButton("Manage Suppliers");
        reportsButton = new JButton("Reports"); // Initialize the Reports button
        exitButton = new JButton("Exit");

        // Configure JFrame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);

        // Set up mainPanel layout
        mainPanel.setLayout(new BorderLayout());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Create a button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 1, 10, 10)); // Change to 5 rows to fit the new button
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        buttonPanel.add(manageProductsButton);
        buttonPanel.add(manageOrdersButton);
        buttonPanel.add(manageSuppliersButton);
        buttonPanel.add(reportsButton); // Add the new Reports button
        buttonPanel.add(exitButton);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        // Add Action Listeners for buttons
        manageProductsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Navigating to Manage Products...", "Info", JOptionPane.INFORMATION_MESSAGE);
                // Logic to open product management screen goes here
                frame.setVisible(false);
                new ManageProducts();
            }
        });

        manageOrdersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Navigating to Manage Orders...", "Info", JOptionPane.INFORMATION_MESSAGE);
                // Logic to open the Manage Orders screen goes here
                frame.setVisible(false); // Hide the current frame
                new OrderManager();  // Open the ManageOrders screen
            }
        });

        manageSuppliersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Navigating to Manage Suppliers...", "Info", JOptionPane.INFORMATION_MESSAGE);
                frame.setVisible(false); // Hide the current frame
                new ManageSuppliers();  // Open the ManageOrders screen
            }
        });

        // Action Listener for the new Reports button
        reportsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Opening Reports Page...", "Info", JOptionPane.INFORMATION_MESSAGE);
                frame.setVisible(false);  // Hide the current frame
                new ReportsPage();  // Open the ReportsPage
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to exit?", "Exit", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });

        // Set up the frame
        frame.add(mainPanel);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        // Run the application
        SwingUtilities.invokeLater(() -> new HomePage());
    }
}
