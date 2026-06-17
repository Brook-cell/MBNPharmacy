package Controller;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.stage.Window;

import java.io.IOException;
public class LoginController {

    @FXML private ComboBox<String> roleComboBox;
    @FXML private PasswordField passwordField;

    @FXML
    public void initialize() {
        roleComboBox.setItems(FXCollections.observableArrayList("Manager", "Pharmacist","Cashier"));
        roleComboBox.setPromptText("Select your role...");
    }

    @FXML
    public void handleLogin(ActionEvent event) throws IOException {
        String password = passwordField.getText();
        String role = roleComboBox.getValue();

        if (role == null || password.isEmpty()) {
            showAlert("Please select a role and enter a password.");
            return;
        }

        boolean correct = switch (role) {
            case "Manager" -> password.equals("admin");
            case "Pharmacist" -> password.equals("pharma123");
            case "Cashier" -> password.equals("cash12");
            default -> false;
        };

        if (correct) {
            String fxml = switch (role) {
                case "Manager" -> "View/Manager.fxml";
                case "Pharmacist" -> "View/Pharmacist.fxml";
                case "Cashier"->"View/CashierProduct.fxml";
                default -> null;
            };

            if (fxml != null) {
                Parent root = FXMLLoader.load(getClass().getResource("/" + fxml));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            }
        } else {
            showAlert("Incorrect password. Please try again.");
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Login Failed");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
