package Controller;

import DAO.EmployeeDAO;
import DAO.EmployeeDAOImp;
import Model.EmployeeModel;
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

public class ManagerEmployeeController {

    @FXML private TextField idField;
    @FXML private TextField nameField;
    @FXML private TextField roleField;
    @FXML private TableView<EmployeeModel> tableView;
    @FXML private TableColumn<EmployeeModel, String> idColumn;
    @FXML private TableColumn<EmployeeModel, String> nameColumn;
    @FXML private TableColumn<EmployeeModel, String> roleColumn;
    private Stage stage;


    EmployeeDAO dao = new EmployeeDAOImp();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(c -> c.getValue().idProperty());
        nameColumn.setCellValueFactory(c -> c.getValue().nameProperty());
        roleColumn.setCellValueFactory(c -> c.getValue().roleProperty());
        loadTable();
    }

    @FXML
    public void handleAdd(ActionEvent event) {
        String id = idField.getText();
        String name = nameField.getText();
        String role = roleField.getText();

        if (id.isEmpty() || name.isEmpty() || role.isEmpty()) {
            showAlert("Please fill all fields.");
            return;
        }

        EmployeeModel employee = new EmployeeModel(id, name, role);
        if (dao.addEmployee(employee)) {
            showSuccess("Employee added successfully!");
            clearFields();
            loadTable();
        } else {
            showAlert("Failed to add employee.");
        }
    }

    @FXML
    public void handleUpdate(ActionEvent event) {
        String id = idField.getText();
        String name = nameField.getText();
        String role = roleField.getText();

        if (id.isEmpty()) {
            showAlert("Please enter an ID to update.");
            return;
        }

        EmployeeModel employee = new EmployeeModel(id, name, role);
        if (dao.updateEmployee(employee)) {
            showSuccess("Employee updated successfully!");
            clearFields();
            loadTable();
        } else {
            showAlert("Failed to update employee.");
        }
    }

    @FXML
    public void handleDelete(ActionEvent event) {
        String id = idField.getText();

        if (id.isEmpty()) {
            showAlert("Please enter an ID to delete.");
            return;
        }

        if (dao.deleteEmployee(id)) {
            showSuccess("Employee deleted successfully!");
            clearFields();
            loadTable();
        } else {
            showAlert("Failed to delete employee.");
        }
    }

    public void loadTable() {
        ObservableList<EmployeeModel> list = FXCollections.observableArrayList();
        list.addAll(dao.getAllEmployees());
        tableView.setItems(list);
    }

    private void clearFields() {
        idField.clear();
        nameField.clear();
        roleField.clear();
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
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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
    public void switchtoProducts(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/View/ManagerProduct.fxml"));
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
}