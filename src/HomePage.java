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
    private JButton manageSuppliersButton;
    private JButton viewReportsButton;
    private JButton exitButton;

    public HomePage() {
        // Initialize components
        frame = new JFrame("Inventory Management System");
        mainPanel = new JPanel();
        titleLabel = new JLabel("Inventory Management System", SwingConstants.CENTER);
        manageProductsButton = new JButton("Manage Products");
        manageSuppliersButton = new JButton("Manage Suppliers");
        viewReportsButton = new JButton("Manage Order Reports");
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
        buttonPanel.setLayout(new GridLayout(4, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        buttonPanel.add(manageProductsButton);
        buttonPanel.add(manageSuppliersButton);
        buttonPanel.add(viewReportsButton);
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

        manageSuppliersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Navigating to Manage Suppliers...", "Info", JOptionPane.INFORMATION_MESSAGE);
                frame.setVisible(false);
                new ManageSuppliers(); 
                // Added logic to open supplier management screen --Phat
            }
        });

        viewReportsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Opening Reports...", "Info", JOptionPane.INFORMATION_MESSAGE);
                frame.setVisible(false);
                new ManageOrders();
                // Logic to open reports screen goes here
                // Added
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
