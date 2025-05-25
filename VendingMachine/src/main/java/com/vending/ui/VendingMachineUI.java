package com.vending.ui;

import com.vending.dao.ProductDAO;
import com.vending.model.Product;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class VendingMachineUI extends JFrame {
    private ProductDAO productDAO;
    private JLabel statusLabel;

    public VendingMachineUI() {
        productDAO = new ProductDAO();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Vending Machine");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null); // Center window
        getContentPane().setBackground(new Color(135, 206, 250)); // Soft blue background

        // Main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(Color.WHITE);

        // Title
        JLabel titleLabel = new JLabel("Welcome to the Vending Machine!", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Product panel
        JPanel productPanel = new JPanel(new GridLayout(0, 3, 10, 10));
        productPanel.setBackground(Color.WHITE);
        loadProducts(productPanel);
        mainPanel.add(new JScrollPane(productPanel), BorderLayout.CENTER);

        // Status label
        statusLabel = new JLabel("Select a product to purchase.", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        mainPanel.add(statusLabel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void loadProducts(JPanel productPanel) {
        try {
            List<Product> products = productDAO.getAllProducts();
            for (Product product : products) {
                JButton productButton = new JButton(product.toString());
                productButton.setFont(new Font("Arial", Font.PLAIN, 14));
                productButton.setBackground(new Color(173, 216, 230)); // Light blue
                productButton.setToolTipText("Click to purchase " + product.getName());
                productButton.addActionListener(e -> purchaseProduct(product));
                productPanel.add(productButton);
            }
        } catch (SQLException e) {
            statusLabel.setText("Error loading products: " + e.getMessage());
        }
    }

    private void purchaseProduct(Product product) {
        if (product.getQuantity() <= 0) {
            statusLabel.setText(product.getName() + " is out of stock!");
            return;
        }
        try {
            productDAO.updateProductQuantity(product.getId(), product.getQuantity() - 1);
            statusLabel.setText("Purchased " + product.getName() + " for $" + product.getPrice());
            // Refresh UI
            getContentPane().removeAll();
            initializeUI();
            revalidate();
            repaint();
        } catch (SQLException e) {
            statusLabel.setText("Error processing purchase: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VendingMachineUI().setVisible(true));
    }
}
