package DAO;

import Model.SupplierModel;
import db.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupplierDAOImp implements SupplierDAO {

@Override
public List<SupplierModel> getAllSuppliers() {
    List<SupplierModel> list = new ArrayList<>();
    String sql = "SELECT id, name, age, address, email, phone FROM suppliers ORDER BY id";
    try (Connection conn = DBConnection.getConnection();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {
        while (rs.next()) {
            list.add(new SupplierModel(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("age"),
                    rs.getString("address"),
                    rs.getString("email"),
                    rs.getString("phone")
            ));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
        return list;
    }
}