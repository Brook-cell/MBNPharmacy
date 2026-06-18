package DAO;

import Model.ProductModel;
import java.util.List;

public interface ProductDAO {
    boolean addProduct(ProductModel product);
    boolean updateProduct(ProductModel product);
    boolean deleteProduct(String id);
    boolean sellProduct(String id, int quantity);
    ProductModel getProductById(String id);
    List<ProductModel> getAllProducts();
}