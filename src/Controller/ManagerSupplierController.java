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

public class ManagerSupplierController {

    @FXML private TableView<ObservableList<String>>           tableView;
    @FXML private TableColumn<ObservableList<String>, String> idColumn;
    @FXML private TableColumn<ObservableList<String>, String> nameColumn;
    @FXML private TableColumn<ObservableList<String>, String> ageColumn;
    @FXML private TableColumn<ObservableList<String>, String> emailColumn;
    @FXML private TableColumn<ObservableList<String>, String> addressColumn;

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(0)));
        nameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(1)));
        ageColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(2)));
        emailColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(3)));
        addressColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(4)));
        loadTable();
    }

    private void loadTable() {
        ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();
        String sql = "SELECT id, name, age, email, address FROM suppliers ORDER BY id";
        try {
            Statement st = DBConnection.getConnection().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                row.add(String.valueOf(rs.getInt("id")));
                row.add(rs.getString("name"));
                row.add(rs.getString("age"));
                row.add(rs.getString("email"));
                row.add(rs.getString("address"));
                data.add(row);
            }
            tableView.setItems(data);
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("DB Error", "Could not load suppliers: " + e.getMessage());
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
