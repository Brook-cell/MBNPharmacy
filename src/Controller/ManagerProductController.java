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
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.sql.*;

public class ManagerProductController {

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

    public void loadTable() {
        ObservableList<ProductModel> list = FXCollections.observableArrayList();
        try {
            Connection conn = DBConnection.getConnection();
            ResultSet rs = conn.createStatement()
                    .executeQuery("SELECT * FROM products");
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
    public void switchtoEmployees(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/Manager.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    public void switchtoLogout(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/Login.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

}