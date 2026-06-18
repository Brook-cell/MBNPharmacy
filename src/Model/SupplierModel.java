package Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class SupplierModel {
    private SimpleIntegerProperty id;
    private SimpleStringProperty name;
    private SimpleStringProperty age;
    private SimpleStringProperty address;
    private SimpleStringProperty email;
    private SimpleStringProperty phone;

    public SupplierModel(int id, String name, String age, String address, String email, String phone) {
        this.id      = new SimpleIntegerProperty(id);
        this.name    = new SimpleStringProperty(name);
        this.age     = new SimpleStringProperty(age);
        this.address = new SimpleStringProperty(address);
        this.email   = new SimpleStringProperty(email);
        this.phone   = new SimpleStringProperty(phone);
    }

    // Getters
    public int getId()          { return id.get(); }
    public String getName()     { return name.get(); }
    public String getAge()      { return age.get(); }
    public String getAddress()  { return address.get(); }
    public String getEmail()    { return email.get(); }
    public String getPhone()    { return phone.get(); }

    // Property methods
    public SimpleIntegerProperty idProperty()      { return id; }
    public SimpleStringProperty nameProperty()     { return name; }
    public SimpleStringProperty ageProperty()      { return age; }
    public SimpleStringProperty addressProperty()  { return address; }
    public SimpleStringProperty emailProperty()    { return email; }
    public SimpleStringProperty phoneProperty()    { return phone; }
}