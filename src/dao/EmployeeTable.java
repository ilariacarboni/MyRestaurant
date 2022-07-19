/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author milar
 */
import database.dbConnection;
import entity.Employee;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class EmployeeTable implements Table<Employee>{
    
    Connection conn = dbConnection.enstablishConnection();
    ArrayList<Employee> employees = new ArrayList<Employee>();

    @Override
    public List<Employee> getAll() {
        /*String sql= "SELECT * FROM Employee ";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.execute();
            } catch (SQLException ex) {
                
             ex.printStackTrace();
            }*/
        return employees;
    }

    @Override
    public void save(Employee em) {
        //lo inserisce nella lista e nel db
        //se il dipendente è nella lista significa che è stato già inserito nel db
        if(employees.indexOf(em)<0){
            
            String sql= "INSERT INTO Employee (codice_fiscale, name, surname, role, begin_date, end_date, wage) VALUES (?,?,?,?,?,?,?)";
            try {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, em.getCodiceF());
                ps.setString(2, em.getName());
                ps.setString(3, em.getSurname());
		ps.setString(4, em.getRole());
                ps.setDate(5, em.getBeginDate());
		ps.setDate(6, em.getEndDate());
                ps.setInt(7, em.getWage()); 
                ps.execute();
            } catch (SQLException ex) {
                
                ex.printStackTrace();
            }

            employees.add(em); 
        }
        
        
    }

    @Override
    public void update(Employee em) {
        //t deve essere un istanza di Product con lo stesso identificativo 
        //dell'istanza che si vuole modificare
         if(employees.indexOf(em)>0){
		
		String sql= "UPDATE Employee  SET codice_fiscale=?, name=?, surname=?, role=?, begin_date=?, end_date=?, wage=? WHERE codice_fiscale = ?";
           	 try {
                 PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, em.getCodiceF());
                ps.setString(2, em.getName());
                ps.setString(3, em.getSurname());
		ps.setString(4, em.getRole());
                ps.setDate(5, em.getBeginDate());
		ps.setDate(6, em.getEndDate());
                ps.setInt(7, em.getWage());
                ps.execute();
            }
            
           catch (SQLException ex) {
                
                ex.printStackTrace();
            }
         }
    }

 
    public void delete (Employee em) {
       
	if(employees.indexOf(em)>0){
            
            String sql= "DELETE FROM Employee WHERE codiceF = ?";
            try {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, em.getCodiceF());
                ps.execute();

            } catch (SQLException ex) {
                
                ex.printStackTrace();
            }

    }
    
  }
    
    
   public List<Employee> search(Object catch_input) {
        //ricerca per nome
        List<Employee> resList = new ArrayList<Employee>();
        if(catch_input instanceof String){
            
            String sql = "SELECT * FROM Employee WHERE codice_fiscale=? OR  name=? OR  surname =? OR  role=? ORDER BY name";
            try {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, (String) catch_input);
                 ps.setString(2, (String) catch_input);
                 ps.setString(3, (String) catch_input);
                 ps.setString(4, (String) catch_input);
                ResultSet resultSet = ps.executeQuery();
                
                while (resultSet.next()) {
                    Employee em = new Employee(resultSet.getString("codice_fiscale"), resultSet.getString("name"),resultSet.getString("surname"),resultSet.getString("role"),resultSet.getDate("begin_date").toLocalDate(),resultSet.getDate("end_date").toLocalDate(),resultSet.getInt("wage") );
                    resList.add(em);
                }
            } catch (SQLException ex) {
                System.out.println(ex.toString());
            }
        }
        
        return resList;
    }
}
