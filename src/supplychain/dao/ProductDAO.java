package supplychain.dao;

import java.sql.*;
import db.DBConnection;

public class ProductDAO {

    public boolean addProduct(String id, String name, String category,
                              int quantity, String date, double price) {

        String sql = "INSERT INTO products (product_id, product_name, category, quantity, production_date, unit_price) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id);
            ps.setString(2, name);
            ps.setString(3, category);
            ps.setInt(4, quantity);
            ps.setDate(5, Date.valueOf(date));
            ps.setDouble(6, price);

            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public ResultSet getAllProducts() {

        String sql = "SELECT * FROM products";

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            return ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}