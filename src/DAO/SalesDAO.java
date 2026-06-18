package DAO;

import Model.SalesModel;
import java.util.List;

public interface SalesDAO {
    boolean addSale(SalesModel sale);
    List<SalesModel> getAllSales();
}