package Controller;

import DAO.SupplierDAO;
import DAO.SupplierDAOImp;
import Model.SupplierModel;
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

public class ManagerSupplierController {

    @FXML private TableView<SupplierModel> tableView;
    @FXML private TableColumn<SupplierModel, Integer> idColumn;
    @FXML private TableColumn<SupplierModel, String>  nameColumn;
    @FXML private TableColumn<SupplierModel, String>  ageColumn;
    @FXML private TableColumn<SupplierModel, String>  addressColumn;
    @FXML private TableColumn<SupplierModel, String>  emailColumn;
    @FXML private TableColumn<SupplierModel, String>  phoneColumn;

    SupplierDAO dao = new SupplierDAOImp();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(c -> c.getValue().idProperty().asObject());
        nameColumn.setCellValueFactory(c -> c.getValue().nameProperty());
        ageColumn.setCellValueFactory(c -> c.getValue().ageProperty());
        addressColumn.setCellValueFactory(c -> c.getValue().addressProperty());
        emailColumn.setCellValueFactory(c -> c.getValue().emailProperty());
        phoneColumn.setCellValueFactory(c -> c.getValue().phoneProperty());
        loadTable();
    }

    private void loadTable() {
        ObservableList<SupplierModel> list = FXCollections.observableArrayList();
        list.addAll(dao.getAllSuppliers());
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