package DAO;

import Model.ProductModel;
import db.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOImp implements ProductDAO {

@Override
public boolean addProduct(ProductModel product) {
    String sql = "INSERT INTO products(id, name, category, quantity, price, expiry_date, supplier) VALUES(?, ?, ?, ?, ?, ?, ?)";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, product.getId());
        stmt.setString(2, product.getName());
        stmt.setString(3, product.getCategory());
        stmt.setInt(4, product.getQuantity());
        stmt.setDouble(5, product.getPrice());
        stmt.setString(6, product.getExpiryDate());
        stmt.setString(7, product.getSupplier());
        int rows = stmt.executeUpdate();
        return rows > 0;
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}

@Override
public boolean updateProduct(ProductModel product) {
    String sql = "UPDATE products SET name=?, category=?, quantity=?, price=?, expiry_date=?, supplier=? WHERE id=?";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, product.getName());
        stmt.setString(2, product.getCategory());
        stmt.setInt(3, product.getQuantity());
        stmt.setDouble(4, product.getPrice());
        stmt.setString(5, product.getExpiryDate());
        stmt.setString(6, product.getSupplier());
        stmt.setString(7, product.getId());
        int rows = stmt.executeUpdate();
        return rows > 0;
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}

@Override
public boolean deleteProduct(String id) {
    String sql = "DELETE FROM products WHERE id=?";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, id);
        int rows = stmt.executeUpdate();
        return rows > 0;
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}

@Override
public boolean sellProduct(String id, int quantityToSell) {

String checkSql = "SELECT quantity, name, category, price FROM products WHERE id=?";
try (Connection conn = DBConnection.getConnection();
     PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
    checkStmt.setString(1, id);
    ResultSet rs = checkStmt.executeQuery();

    if (rs.next()) {
        int currentQty = rs.getInt("quantity");
        String name = rs.getString("name");
        String category = rs.getString("category");
        double price = rs.getDouble("price");

        if (quantityToSell > currentQty) {
            return false;
        }

        int newQty = currentQty - quantityToSell;
        String updateSql = "UPDATE products SET quantity=? WHERE id=?";
        PreparedStatement updateStmt = conn.prepareStatement(updateSql);
        updateStmt.setInt(1, newQty);
        updateStmt.setString(2, id);
        updateStmt.executeUpdate();


        String saleSql = "INSERT INTO sales(product_id, product_name, category, quantity_sold, price) VALUES(?, ?, ?, ?, ?)";
        PreparedStatement saleStmt = conn.prepareStatement(saleSql);
        saleStmt.setString(1, id);
        saleStmt.setString(2, name);
        saleStmt.setString(3, category);
        saleStmt.setInt(4, quantityToSell);
        saleStmt.setDouble(5, price);
        saleStmt.executeUpdate();

        return true;
    }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
    }

    @Override
    public ProductModel getProductById(String id) {
    String sql = "SELECT * FROM products WHERE id=?";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, id);
        ResultSet rs = stmt.executeQuery();
    if (rs.next()) {
        return new ProductModel(
                rs.getString("id"),
                rs.getString("name"),
                rs.getString("category"),
                rs.getInt("quantity"),
                rs.getDouble("price"),
                rs.getString("expiry_date"),
                rs.getString("supplier")
        );
    }
    } catch (SQLException e) {
        e.printStackTrace();
    }
        return null;
    }

@Override
public List<ProductModel> getAllProducts() {
    List<ProductModel> list = new ArrayList<>();
    String sql = "SELECT * FROM products";
    try (Connection conn = DBConnection.getConnection();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {
        while (rs.next()) {
            list.add(new ProductModel(
                    rs.getString("id"),
                    rs.getString("name"),
                    rs.getString("category"),
                    rs.getInt("quantity"),
                    rs.getDouble("price"),
                    rs.getString("expiry_date"),
                    rs.getString("supplier")
            ));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
        return list;
    }
}