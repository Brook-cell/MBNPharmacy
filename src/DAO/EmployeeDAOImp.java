package DAO;
import Model.EmployeeModel;
import db.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class EmployeeDAOImp  implements EmployeeDAO{
    @Override
    public boolean addEmployee(EmployeeModel employee) {
        String sql = "INSERT INTO employees(id, name, role) VALUES(?,?,?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, employee.getId());
            stmt.setString(2, employee.getName());
            stmt.setString(3, employee.getRole());
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateEmployee(EmployeeModel employee) {
        String sql = "UPDATE employees SET name=?, role=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, employee.getName());
        stmt.setString(2, employee.getRole());
        stmt.setString(3, employee.getId());
        int rows = stmt.executeUpdate();
        return rows > 0;
    } catch (SQLException e) {
        e.printStackTrace();
    }
        return false;
    }

    @Override
    public boolean deleteEmployee(String id) {
        String sql = "DELETE FROM employees WHERE id=?";
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
    public List<EmployeeModel> getAllEmployees() {
        List<EmployeeModel> list = new ArrayList<>();
        String sql = "SELECT * FROM employees";
    try (Connection conn = DBConnection.getConnection();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {
        while (rs.next()) {
            list.add(new EmployeeModel(
                    rs.getString("id"),
                    rs.getString("name"),
                    rs.getString("role")
            ));
        }
    } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}


