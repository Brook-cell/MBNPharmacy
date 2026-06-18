package Controller;

import DAO.SalesDAO;
import DAO.SalesDAOImp;
import Model.SalesModel;
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

public class ManagerSalesController {

    @FXML private TableView<SalesModel> tableView;
    @FXML private TableColumn<SalesModel, Integer>idColumn;
    @FXML private TableColumn<SalesModel, String>productIdColumn;
    @FXML private TableColumn<SalesModel, String>productNameColumn;
    @FXML private TableColumn<SalesModel, String>categoryColumn;
    @FXML private TableColumn<SalesModel, Integer>quantitySoldColumn;
    @FXML private TableColumn<SalesModel, Double>priceColumn;
    @FXML private TableColumn<SalesModel, String>saleDateColumn;
    SalesDAO dao = new SalesDAOImp();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(c -> c.getValue().idProperty().asObject());
        productIdColumn.setCellValueFactory(c -> c.getValue().productIdProperty());
        productNameColumn.setCellValueFactory(c -> c.getValue().productNameProperty());
        categoryColumn.setCellValueFactory(c -> c.getValue().categoryProperty());
        quantitySoldColumn.setCellValueFactory(c -> c.getValue().quantitySoldProperty().asObject());
        priceColumn.setCellValueFactory(c -> c.getValue().priceProperty().asObject());
        saleDateColumn.setCellValueFactory(c -> c.getValue().saleDateProperty());
        loadTable();
    }

    private void loadTable() {
        ObservableList<SalesModel> list = FXCollections.observableArrayList();
        list.addAll(dao.getAllSales());
        tableView.setItems(list);
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