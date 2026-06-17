package Controller;

import db.DBConnection;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.sql.*;

public class ManagerSalesController {

    @FXML private TableView<ObservableList<String>>           tableView;
    @FXML private TableColumn<ObservableList<String>, String> idColumn;
    @FXML private TableColumn<ObservableList<String>, String> productIdColumn;
    @FXML private TableColumn<ObservableList<String>, String> productNameColumn;
    @FXML private TableColumn<ObservableList<String>, String> categoryColumn;
    @FXML private TableColumn<ObservableList<String>, String> quantitySoldColumn;
    @FXML private TableColumn<ObservableList<String>, String> priceColumn;
    @FXML private TableColumn<ObservableList<String>, String> saleDateColumn;

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(0)));
        productIdColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(1)));
        productNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(2)));
        categoryColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(3)));
        quantitySoldColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(4)));
        priceColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(5)));
        saleDateColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(6)));
        loadTable();
    }

    private void loadTable() {
        ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();
        String sql = "SELECT id, product_id, product_name, category, quantity_sold, price, sale_date " +
                "FROM sales ORDER BY sale_date DESC";
        try ( Connection conn = DBConnection.getConnection();
              Statement st = conn.createStatement();
              ResultSet rs = st.executeQuery(sql)){
            while (rs.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                row.add(String.valueOf(rs.getInt("id")));
                row.add(rs.getString("product_id"));
                row.add(rs.getString("product_name"));
                row.add(rs.getString("category"));
                row.add(String.valueOf(rs.getInt("quantity_sold")));
                row.add(String.valueOf(rs.getDouble("price")));
                row.add(rs.getString("sale_date"));
                data.add(row);
            }
            tableView.setItems(data);
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("DB Error", "Could not load sales: " + e.getMessage());
        }
    }

    @FXML
    private void switchToSales(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/View/ManagerSales.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void switchToSuppliers(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/View/ManagerSuppliers.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void switchToEmployees(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/View/Manager.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void switchToProducts(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/View/ManagerProduct.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void switchToLogout(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/View/Login.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
