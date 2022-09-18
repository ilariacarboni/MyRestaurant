package business;

import model.dao.EmployeeTable;
import model.entity.Employee;

import java.util.HashMap;

public class EmployeeManager {

    private EmployeeTable employeeTable;

    public EmployeeManager(){
        this.employeeTable = new EmployeeTable();
    }

    public boolean saveEmployee(HashMap<String, Object> data){
        Employee employee = this.employeeTable.constructEntityFromMap(data);
        return this.employeeTable.save(employee);
    }
}
