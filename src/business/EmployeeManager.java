package business;

import java.util.ArrayList;
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
    
    public boolean updateEmployee(HashMap<String, Object> empinfo ){
        Employee employee = this.employeeTable.constructEntityFromMap(empinfo);
        return this.employeeTable.update(employee);
    }
    
    public boolean deleteEmployee(HashMap<String, Object> empinfo ){
        Employee employee = this.employeeTable.constructEntityFromMap(empinfo);
        return this.employeeTable.delete(employee);
    }
    public ArrayList getAll(){
        ArrayList<Employee> employees = this.employeeTable.getAll();
        ArrayList<HashMap<String,Object>> res  = new ArrayList<HashMap<String,Object>>();
        for(Employee employee : employees){
            res.add(employee.map());
        }
        return res;
    }

    public ArrayList getFrom(Object searchParam, String paramName ){
        ArrayList<Employee> employees = this.employeeTable.getFrom(searchParam, paramName);
        ArrayList<HashMap<String,Object>> res  = new ArrayList<HashMap<String,Object>>();
        for(Employee employee : employees){
            res.add(employee.map());
        }
        return res;
    }
}
