package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    public static Connection getConnection() {
        Connection connection = null;
        try {
            String url = "jdbc:mysql://localhost:3306/pharmacy_db";
            String user = "root";
            String password = "Birukserver190";
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to database!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}