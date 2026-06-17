package Model;

import javafx.beans.property.SimpleStringProperty;

public class EmployeeModel {
    private SimpleStringProperty id;
    private SimpleStringProperty name;
    private SimpleStringProperty role;

    public EmployeeModel(String id, String name, String role) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.role = new SimpleStringProperty(role);
    }

    public String getId() {
        return id.get();
    }
    public String getName() {
        return name.get();
    }
    public String getRole() {
        return role.get();
    }

    public SimpleStringProperty idProperty() {
        return id;
    }
    public SimpleStringProperty nameProperty() {
        return name;
    }
    public SimpleStringProperty roleProperty() {
        return role;
    }
}

