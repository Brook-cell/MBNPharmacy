package Controller;

import Model.ProductModel;
import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.sql.*;

public class CashierProductController {

    @FXML private TextField searchIdField;
    @FXML private TextField idField;
    @FXML private TextField nameField;
    @FXML private TextField categoryField;
    @FXML private TextField quantityField;
    @FXML private TextField priceField;
    @FXML private TextField sellAmountField;

    @FXML private TableView<ProductModel>tableView;
    @FXML private TableColumn<ProductModel, String> idColumn;
    @FXML private TableColumn<ProductModel, String>  nameColumn;
    @FXML private TableColumn<ProductModel, String>  categoryColumn;
    @FXML private TableColumn<ProductModel, Integer> quantityColumn;
    @FXML private TableColumn<ProductModel, Double>  priceColumn;
    @FXML private TableColumn<ProductModel, String>  expiryColumn;
    @FXML private TableColumn<ProductModel, String>  supplierColumn;

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        expiryColumn.setCellValueFactory(new PropertyValueFactory<>("expiryDate"));
        supplierColumn.setCellValueFactory(new PropertyValueFactory<>("supplier"));
        loadTable();
    }


    private void loadTable() {
        ObservableList<ProductModel> list = FXCollections.observableArrayList();
        try {
            Connection conn = DBConnection.getConnection();
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM products");
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
            tableView.setItems(list);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSearch() {
        String input = searchIdField.getText().trim();
        if (input.isEmpty()) {
            showAlert("Input Error", "Please enter a Product ID to search.");
            return;
        }
        try {
            String searchId = input;
            String sql = "SELECT * FROM products WHERE id = ?";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setString(1, searchId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                idField.setText(String.valueOf(rs.getString("id")));
                nameField.setText(rs.getString("name"));
                categoryField.setText(rs.getString("category"));
                quantityField.setText(String.valueOf(rs.getInt("quantity")));
                priceField.setText(String.valueOf(rs.getDouble("price")));
            } else {
                showAlert("Not Found", "No product found with ID: " + searchId);
                clearFields();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("DB Error", e.getMessage());
        }
    }


    @FXML
    private void handleSell() {
        if (idField.getText().isEmpty()) {
            showAlert("No Product", "Please search for a product first.");
            return;
        }
        String amountText = sellAmountField.getText().trim();
        if (amountText.isEmpty()) {
            showAlert("Input Error", "Please enter a quantity to sell.");
            return;
        }
        try {
            String productId   = idField.getText().trim();
            int sellAmount  = Integer.parseInt(amountText);
            int currentQty  = Integer.parseInt(quantityField.getText().trim());

            if (sellAmount <= 0) {
                showAlert("Input Error", "Sell amount must be greater than zero.");
                return;
            }
            if (sellAmount > currentQty) {
                showAlert("Stock Error", "Not enough stock. Available: " + currentQty);
                return;
            }

            int newQty = currentQty - sellAmount;
            String sql = "UPDATE products SET quantity = ? WHERE id = ?";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, newQty);
            ps.setString(2, productId);
            ps.executeUpdate();

            String insertSale = "INSERT INTO sales (product_id, product_name, category, quantity_sold, price) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement saleps = DBConnection.getConnection().prepareStatement(insertSale);
            saleps.setString(1, productId);
            saleps.setString(2, nameField.getText());
            saleps.setString(3, categoryField.getText());
            saleps.setInt(4, sellAmount);
            saleps.setDouble(5, Double.parseDouble(priceField.getText()));
            saleps.executeUpdate();

            showAlert("Success", "Sold " + sellAmount + " unit(s). Remaining stock: " + newQty);
            quantityField.setText(String.valueOf(newQty));
            sellAmountField.clear();
            loadTable();

        } catch (NumberFormatException e) {
            showAlert("Input Error", "Sell amount must be a valid number.");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("DB Error", e.getMessage());
        }
    }


    @FXML
    private void switchToSales(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/View/CashierSales.fxml"));
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


    private void clearFields() {
        idField.clear();
        nameField.clear();
        categoryField.clear();
        quantityField.clear();
        priceField.clear();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
