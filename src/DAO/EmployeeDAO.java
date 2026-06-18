package DAO;

import Model.EmployeeModel;

import java.util.List;

public interface EmployeeDAO {
    boolean addEmployee(EmployeeModel employee);
    boolean updateEmployee(EmployeeModel employee);
    boolean deleteEmployee(String id);
    List<EmployeeModel> getAllEmployees();
}
