package DAO;

import Model.SalesModel;
import db.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SalesDAOImp implements SalesDAO {


@Override
public List<SalesModel> getAllSales() {
    List<SalesModel> list = new ArrayList<>();
    String sql = "SELECT id, product_id, product_name, category, quantity_sold, price, sale_date FROM sales ORDER BY sale_date DESC";
    try (Connection conn = DBConnection.getConnection();
     Statement stmt = conn.createStatement();
     ResultSet rs = stmt.executeQuery(sql)) {
    while (rs.next()) {
        list.add(new SalesModel(
            rs.getInt("id"),
            rs.getString("product_id"),
            rs.getString("product_name"),
            rs.getString("category"),
            rs.getInt("quantity_sold"),
            rs.getDouble("price"),
            rs.getString("sale_date")
        ));
     }
    } catch (SQLException e) {
        e.printStackTrace();
     }
        return list;
    }
}