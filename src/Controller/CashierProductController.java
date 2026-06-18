package Controller;

import DAO.ProductDAO;
import DAO.ProductDAOImp;
import Model.ProductModel;
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

public class CashierProductController {

    @FXML private TextField searchIdField;
    @FXML private TextField idField;
    @FXML private TextField nameField;
    @FXML private TextField categoryField;
    @FXML private TextField quantityField;
    @FXML private TextField priceField;
    @FXML private TextField sellAmountField;

    @FXML private TableView<ProductModel> tableView;
    @FXML private TableColumn<ProductModel, String>  idColumn;
    @FXML private TableColumn<ProductModel, String>  nameColumn;
    @FXML private TableColumn<ProductModel, String>  categoryColumn;
    @FXML private TableColumn<ProductModel, Integer> quantityColumn;
    @FXML private TableColumn<ProductModel, Double>  priceColumn;
    @FXML private TableColumn<ProductModel, String>  expiryColumn;
    @FXML private TableColumn<ProductModel, String>  supplierColumn;

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

private void loadTable() {
    ObservableList<ProductModel> list = FXCollections.observableArrayList();
    list.addAll(dao.getAllProducts());
    tableView.setItems(list);
}

@FXML
private void handleSearch(ActionEvent event) {
    String input = searchIdField.getText().trim();
    if (input.isEmpty()) {
        showAlert("Input Error", "Please enter a Product ID to search.");
        return;
    }

    ProductModel product = dao.getProductById(input);
    if (product != null) {
        idField.setText(product.getId());
        nameField.setText(product.getName());
        categoryField.setText(product.getCategory());
        quantityField.setText(String.valueOf(product.getQuantity()));
        priceField.setText(String.valueOf(product.getPrice()));
    } else {
        showAlert("Not Found", "No product found with ID: " + input);
        clearFields();
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
        String productId = idField.getText().trim();
        int sellAmount= Integer.parseInt(amountText);
        int currentQty= Integer.parseInt(quantityField.getText().trim());

        if (sellAmount <= 0) {
            showAlert("Input Error", "Sell amount must be greater than zero.");
            return;
        }
        if (sellAmount > currentQty) {
            showAlert("Stock Error", "Not enough stock. Available: " + currentQty);
            return;
        }

        if (dao.sellProduct(productId, sellAmount)) {
            int newQty = currentQty - sellAmount;
            showAlert("Success", "Sold " + sellAmount + " unit(s). Remaining stock: " + newQty);
            quantityField.setText(String.valueOf(newQty));
            sellAmountField.clear();
            loadTable();
        } else {
            showAlert("Error", "Failed to process sale.");
        }

    } catch (NumberFormatException e) {
        showAlert("Input Error", "Sell amount must be a valid number.");
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