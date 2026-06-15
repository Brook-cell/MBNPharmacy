package Controller;

import db.DBConnection;
import Model.ProductModel;
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

public class PharmacistController {

    @FXML private TextField idField;
    @FXML private TextField nameField;
    @FXML private TextField categoryField;
    @FXML private TextField quantityField;
    @FXML private TextField priceField;
    @FXML private TextField expiryField;
    @FXML private TextField supplierField;

    @FXML private TableView<ProductModel> tableView;
    @FXML private TableColumn<ProductModel, String> idColumn;
    @FXML private TableColumn<ProductModel, String> nameColumn;
    @FXML private TableColumn<ProductModel, String> categoryColumn;
    @FXML private TableColumn<ProductModel, Integer> quantityColumn;
    @FXML private TableColumn<ProductModel, Double> priceColumn;
    @FXML private TableColumn<ProductModel, String> expiryColumn;
    @FXML private TableColumn<ProductModel, String> supplierColumn;

    private Stage stage;
    private Scene scene;

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
        String id = idField.getText();
        String name = nameField.getText();
        String category = categoryField.getText();
        String quantity = quantityField.getText();
        String price = priceField.getText();
        String expiry = expiryField.getText();
        String supplierId = supplierField.getText().trim();

        if ( id.isEmpty() || name.isEmpty() || category.isEmpty() || quantity.isEmpty() || price.isEmpty() || expiry.isEmpty() || supplierId.isEmpty()) {
            showAlert("Please fill all fields ");
            return;
        }
        String supplier = null;
        String lookupSql = "SELECT name FROM suppliers WHERE id = ?";
        try (PreparedStatement lookupPs = DBConnection.getConnection().prepareStatement(lookupSql)) {
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
        String  sql = "INSERT INTO products( id,name, category, quantity, price, expiry_date, supplier) VALUES(?,?,?,?,?,?,?)";
        try( Connection conn = DBConnection.getConnection();
             PreparedStatement stmt= conn.prepareStatement(sql);) {
                stmt.setString(1, id);
                stmt.setString(2, name);
                stmt.setString(3, category);
                stmt.setInt(4, Integer.parseInt(quantity));
                stmt.setDouble(5, Double.parseDouble(price));
                stmt.setString(6, expiry);
                stmt.setString(7, supplier);

            stmt.executeUpdate();
            showSuccess("Product added successfully!");
            clearFields();
            loadTable();
        } catch (SQLException e) {
            showAlert("Error: " + e.getMessage());
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

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE products SET name=?, category=?, quantity=?, price=?, expiry_date=?, supplier=? WHERE id=?")){
            stmt.setString(1, name);
            stmt.setString(2, category);
            stmt.setInt(3, Integer.parseInt(quantity));
            stmt.setDouble(4, Double.parseDouble(price));
            stmt.setString(5, expiry);
            stmt.setString(6, supplier);
            stmt.setString(7, id);
            stmt.executeUpdate();
            showSuccess("Product updated successfully!");
            clearFields();
            loadTable();
        } catch (SQLException e) {
            showAlert("Error: " + e.getMessage());
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

        try ( Connection conn = DBConnection.getConnection();
              PreparedStatement stmt = conn.prepareStatement("DELETE FROM products WHERE id=?")){
            stmt.setString(1, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                showSuccess("Product deleted successfully!");
            } else {
                showAlert("Product with ID " + id + " not found.");
            }
            clearFields();
            loadTable();
        } catch (SQLException e) {
            showAlert("Error: " + e.getMessage());
        }
    }

    @FXML
    public void handleSell(ActionEvent event) {
        String id = idField.getText();
        String sellQuantity = quantityField.getText();

        if (id.isEmpty() || sellQuantity.isEmpty()) {
            showAlert("Please enter Product ID and Quantity to sell.");
            return;
        }

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement("SELECT quantity FROM products WHERE id=?")){
            checkStmt.setString(1, id);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                int currentQty = rs.getInt("quantity");
                int qtyToSell = Integer.parseInt(sellQuantity);

                if (qtyToSell > currentQty) {
                    showAlert("Insufficient stock! Available quantity: " + currentQty);
                    return;
                }
                int newQty = currentQty - qtyToSell;
                PreparedStatement updateStmt = conn.prepareStatement("UPDATE products SET quantity=? WHERE id=?");
                updateStmt.setInt(1, newQty);
                updateStmt.setString(2, id);
                updateStmt.executeUpdate();

                showSuccess("Sold " + qtyToSell + " units! Remaining stock: " + newQty);
            } else {
                showAlert("Product with ID " + id + " not found.");
            }

            clearFields();
            loadTable();
        } catch (SQLException e) {
            showAlert("Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            showAlert("Please enter a valid number for quantity.");
        }
    }

    public void loadTable() {
        ObservableList<ProductModel> list = FXCollections.observableArrayList();
        try( Connection conn = DBConnection.getConnection();
             ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM products")) {
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
            showAlert("Error loading products: " + e.getMessage());
        }
    }
    @FXML
    private void switchToSales(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/View/PharmacistSales.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void switchtoLogout(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/Login.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
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




