package Controller;

import DAO.ProductDAO;
import DAO.ProductDAOImp;
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
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.*;

public class ManagerProductController {

    @FXML private TextField idField;
    @FXML private TextField nameField;
    @FXML private TextField categoryField;
    @FXML private TextField quantityField;
    @FXML private TextField priceField;
    @FXML private TextField expiryField;
    @FXML private TextField supplierField;

    @FXML private TableView<ProductModel> tableView;
    @FXML private TableColumn<ProductModel, String>  idColumn;
    @FXML private TableColumn<ProductModel, String>  nameColumn;
    @FXML private TableColumn<ProductModel, String>  categoryColumn;
    @FXML private TableColumn<ProductModel, Integer> quantityColumn;
    @FXML private TableColumn<ProductModel, Double>  priceColumn;
    @FXML private TableColumn<ProductModel, String>  expiryColumn;
    @FXML private TableColumn<ProductModel, String>  supplierColumn;

    private Stage stage;
    ProductDAO dao = new ProductDAOImp();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(c -> c.getValue().idProperty());
        nameColumn.setCellValueFactory(c -> c.getValue().nameProperty());
        categoryColumn.setCellValueFactory(c -> c.getValue().categoryProperty());
        quantityColumn.setCellValueFactory(c -> c.getValue().quantityProperty().asObject());
        priceColumn.setCellValueFactory(c -> c.getValue().priceProperty().asObject());
        expiryColumn.setCellValueFactory(c -> c.getValue().expiryDateProperty());
        supplierColumn.setCellValueFactory(c -> c.getValue().supplierProperty());
        loadTable();
    }

    @FXML
    public void handleAdd(ActionEvent event) {
        String id= idField.getText();
        String name= nameField.getText();
        String category = categoryField.getText();
        String quantity= quantityField.getText();
        String price = priceField.getText();
        String expiry = expiryField.getText();
        String supplierId = supplierField.getText().trim();

        if (id.isEmpty() || name.isEmpty() || category.isEmpty() || quantity.isEmpty() || price.isEmpty() || expiry.isEmpty() || supplierId.isEmpty()) {
            showAlert("Please fill all fields.");
            return;
        }

        String supplier = null;
        String lookupSql = "SELECT name FROM suppliers WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement lookupPs = conn.prepareStatement(lookupSql)) {
            lookupPs.setInt(1, Integer.parseInt(supplierId));
            ResultSet rs = lookupPs.executeQuery();
            if (rs.next()) {
                supplier = rs.getString("name");
            } else {
                showAlert("No supplier found with ID: " + supplierId);
                return;
            }
        } catch (NumberFormatException e) {
            showAlert("Supplier ID must be a number.");
            return;
        } catch (SQLException e) {
            showAlert("DB Error: " + e.getMessage());
            return;
        }

        try {
            ProductModel product = new ProductModel(
                    id, name, category,
                    Integer.parseInt(quantity),
                    Double.parseDouble(price),
                    expiry, supplier
            );
            if (dao.addProduct(product)) {
                showSuccess("Product added successfully!");
                clearFields();
                loadTable();
            } else {
                showAlert("Failed to add product.");
            }
        } catch (NumberFormatException e) {
            showAlert("Please enter valid numbers for Quantity and Price.");
        }
    }

    @FXML
    public void handleUpdate(ActionEvent event) {
        String id = idField.getText();
        String name = nameField.getText();
        String category = categoryField.getText();
        String quantity = quantityField.getText();
        String price = priceField.getText();
        String expiry = expiryField.getText();
        String supplier = supplierField.getText();

        if (id.isEmpty()) {
            showAlert("Please enter a Product ID to update.");
            return;
        }

        try {
            ProductModel product = new ProductModel(
                    id, name, category,
                    Integer.parseInt(quantity),
                    Double.parseDouble(price),
                    expiry, supplier
            );
            if (dao.updateProduct(product)) {
                showSuccess("Product updated successfully!");
                clearFields();
                loadTable();
            } else {
                showAlert("Failed to update product.");
            }
        } catch (NumberFormatException e) {
            showAlert("Please enter valid numbers for Quantity and Price.");
        }
    }

    @FXML
    public void handleDelete(ActionEvent event) {
        String id = idField.getText();
        if (id.isEmpty()) {
            showAlert("Please enter a Product ID to delete.");
            return;
        }
        if (dao.deleteProduct(id)) {
            showSuccess("Product deleted successfully!");
            clearFields();
            loadTable();
        } else {
            showAlert("Product with ID " + id + " not found.");
        }
    }

    public void loadTable() {
        ObservableList<ProductModel> list = FXCollections.observableArrayList();
        list.addAll(dao.getAllProducts());
        tableView.setItems(list);
    }

    @FXML
    private void switchToEmployees(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/View/Manager.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
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
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
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
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void switchtoLogout(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/View/Login.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
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
        expiryField.clear();
        supplierField.clear();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("❌ Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("✅ Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}