package com.vending.dao;

import com.vending.model.Product;
import com.vending.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    public List<Product> getAllProducts() throws SQLException {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM products";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
             System.out.println("Retriving");
             System.out.println("ResultSet: " + rs);
            while (rs.next()) {
                products.add(new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getDouble("price"),
                    rs.getInt("quantity")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving products: " + e.getMessage());
            throw e; // Re-throw the exception for further handling
        }
        return products;
    }

    public void updateProductQuantity(int productId, int newQuantity) throws SQLException {
        String query = "UPDATE products SET quantity = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, newQuantity);
            pstmt.setInt(2, productId);
            pstmt.executeUpdate();
        }
    }
}
