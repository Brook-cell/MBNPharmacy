package Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class ProductModel {
    private SimpleStringProperty id;
    private SimpleStringProperty name;
    private SimpleStringProperty category;
    private SimpleIntegerProperty quantity;
    private SimpleDoubleProperty price;
    private SimpleStringProperty expiryDate;
    private SimpleStringProperty supplier;

    public ProductModel(String id, String name, String category, int quantity, double price, String expiryDate, String supplier) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.category = new SimpleStringProperty(category);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.price = new SimpleDoubleProperty(price);
        this.expiryDate = new SimpleStringProperty(expiryDate);
        this.supplier = new SimpleStringProperty(supplier);
    }

    public String getId() { return id.get(); }
    public String getName() { return name.get(); }
    public String getCategory() { return category.get(); }
    public int getQuantity() { return quantity.get(); }
    public double getPrice() { return price.get(); }
    public String getExpiryDate() { return expiryDate.get(); }
    public String getSupplier() { return supplier.get(); }

    public SimpleStringProperty idProperty() { return id; }
    public SimpleStringProperty nameProperty() { return name; }
    public SimpleStringProperty categoryProperty() { return category; }
    public SimpleIntegerProperty quantityProperty() { return quantity; }
    public SimpleDoubleProperty priceProperty() { return price; }
    public SimpleStringProperty expiryDateProperty() { return expiryDate; }
    public SimpleStringProperty supplierProperty() { return supplier; }
}