package Controller;


import db.DBConnection;
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


import java.sql.*;

public class ManagerEmployeeController {

    @FXML private TextField idField;
    @FXML private TextField nameField;
    @FXML private TextField roleField;
    @FXML private TableView<EmployeeModel> tableView;
    @FXML private TableColumn<EmployeeModel, String> idColumn;
    @FXML private TableColumn<EmployeeModel, String> nameColumn;
    @FXML private TableColumn<EmployeeModel, String> roleColumn;

    private Stage stage;
    private Scene scene;



    @FXML
    public void handleAdd(ActionEvent event) {
        String id = idField.getText();
        String name = nameField.getText();
        String role = roleField.getText();

        if (id.isEmpty() || name.isEmpty() || role.isEmpty()) {
            showAlert("Please fill all fields.");
            return;
        }

        String sql ="INSERT INTO employees(id,name,role) VALUES(?,?,?)";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, id);
            stmt.setString(2, name);
            stmt.setString(3, role);
            stmt.executeUpdate();
            showSuccess("Employee added successfully!");
            clearFields();
            loadTable();
        } catch (SQLException e) {
            showAlert("Error: " + e.getMessage());
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

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement("UPDATE employees SET name=?, role=? WHERE id=?");
            stmt.setString(1, name);
            stmt.setString(2, role);
            stmt.setString(3, id);
            stmt.executeUpdate();
            showSuccess("Employee updated successfully!");
            clearFields();
            loadTable();
        } catch (SQLException e) {
            showAlert("Error: " + e.getMessage());
        }
    }

    @FXML
    public void handleDelete(ActionEvent event) {
        String id = idField.getText();

        if (id.isEmpty()) {
            showAlert("Please enter an ID to delete.");
            return;
        }

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM employees WHERE id=?");
            stmt.setString(1, id);
            stmt.executeUpdate();
            showSuccess("Employee deleted successfully!");
            clearFields();
            loadTable();
        } catch (SQLException e) {
            showAlert("Error: " + e.getMessage());
        }
    }

    public void loadTable() {
        ObservableList<EmployeeModel> list = FXCollections.observableArrayList();
        try {
            Connection conn = DBConnection.getConnection();
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM employees");
            while (rs.next()) {
                list.add(new EmployeeModel(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("role")
                ));
            }
            tableView.setItems(list);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(c -> c.getValue().idProperty());
        nameColumn.setCellValueFactory(c -> c.getValue().nameProperty());
        roleColumn.setCellValueFactory(c -> c.getValue().roleProperty());
        loadTable();
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
    public void switchtoProducts(ActionEvent event) {
        try{
        Parent root = FXMLLoader.load(getClass().getResource("/View/ManagerProduct.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void switchtoLogout(ActionEvent event){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/View/Login.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}