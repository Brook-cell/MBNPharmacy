package Model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class SalesModel {
    private SimpleIntegerProperty id;
    private SimpleStringProperty productId;
    private SimpleStringProperty productName;
    private SimpleStringProperty category;
    private SimpleIntegerProperty quantitySold;
    private SimpleDoubleProperty price;
    private SimpleStringProperty saleDate;

    public SalesModel(int id, String productId, String productName, String category, int quantitySold, double price, String saleDate) {
        this.id           = new SimpleIntegerProperty(id);
        this.productId    = new SimpleStringProperty(productId);
        this.productName  = new SimpleStringProperty(productName);
        this.category     = new SimpleStringProperty(category);
        this.quantitySold = new SimpleIntegerProperty(quantitySold);
        this.price        = new SimpleDoubleProperty(price);
        this.saleDate     = new SimpleStringProperty(saleDate);
    }

    public int getId()              { return id.get(); }
    public String getProductId()    { return productId.get(); }
    public String getProductName()  { return productName.get(); }
    public String getCategory()     { return category.get(); }
    public int getQuantitySold()    { return quantitySold.get(); }
    public double getPrice()        { return price.get(); }
    public String getSaleDate()     { return saleDate.get(); }


    public SimpleIntegerProperty idProperty()           { return id; }
    public SimpleStringProperty productIdProperty()     { return productId; }
    public SimpleStringProperty productNameProperty()   { return productName; }
    public SimpleStringProperty categoryProperty()      { return category; }
    public SimpleIntegerProperty quantitySoldProperty() { return quantitySold; }
    public SimpleDoubleProperty priceProperty()         { return price; }
    public SimpleStringProperty saleDateProperty()      { return saleDate; }
}
