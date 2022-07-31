package model.dao;

/**
 *
 * @author milar
 */
import model.database.dbConnection;
import model.entity.Employee;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;




public class EmployeeTable implements Table<Employee>{
    
    Connection conn = dbConnection.enstablishConnection();
    ArrayList<Employee> employeesList = new ArrayList<Employee>();

    @Override
    public ArrayList<Employee> getAll() {
        ArrayList <Employee> resList = new ArrayList<Employee>();
        String sql = "SELECT * FROM employee";
        try {
            Statement stm = conn.createStatement();
            ResultSet resultSet = stm.executeQuery(sql);

            while (resultSet.next()) {
                Employee em = new Employee( resultSet.getString("codice_fiscale"),resultSet.getString("name"),resultSet.getString("surname"),resultSet.getString("role"),resultSet.getDate("begin_date").toLocalDate(),resultSet.getDate("end_date").toLocalDate(),resultSet.getInt("wage"));
                resList.add(em);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
        this.employeesList = resList;
        return resList;
    }

    @Override
    public boolean save(Employee em) {
        boolean res = false;
        if(employeesList.indexOf(em)<0){
            
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
                
                res = true;
            } catch (SQLException ex) {
                
                ex.printStackTrace();
            }

            employeesList.add(em); 
        }
        return res;
        
    }

    @Override
    public boolean update(Employee em) {
        //t deve essere un istanza di Product con lo stesso identificativo 
        //dell'istanza che si vuole modificare
        boolean res = false;
        if(employeesList.indexOf(em)>0){
		
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
                
                res = true;
            }
            
           catch (SQLException ex) {
                
                ex.printStackTrace();
            }
         }
        return res;
    }

 
    public boolean delete (Employee em) {
        boolean res = false;
        
	if(employeesList.indexOf(em)>0){
            
            String sql= "DELETE FROM Employee WHERE codiceF = ?";
            try {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, em.getCodiceF());
                //ps.execute();
                res = true;
            } catch (SQLException ex) {
                
                ex.printStackTrace();
            }

    }
        this.employeesList.remove(em);
        return res;
    
  }
    
    
   public ArrayList<Employee> getFrom(Object searchParam, String paramName) {
        //ricerca per nome
        ArrayList<Employee> resList = new ArrayList<Employee>();
        if(searchParam instanceof String){
            if(paramName.equals("name")||paramName.equals("surname")||paramName.equals("role")){
            String sql = "SELECT * FROM Employee WHERE codice_fiscale=? OR  name=? OR  surname =? OR  role=? ORDER BY name";
            try {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, (String) searchParam);
                 ps.setString(2, (String) searchParam);
                 ps.setString(3, (String) searchParam);
                 ps.setString(4, (String) searchParam);
                ResultSet resultSet = ps.executeQuery();
                
                while (resultSet.next()) {
                    Employee em = new Employee(resultSet.getString("codice_fiscale"), resultSet.getString("name"),resultSet.getString("surname"),resultSet.getString("role"),resultSet.getDate("begin_date").toLocalDate(),resultSet.getDate("end_date").toLocalDate(),resultSet.getInt("wage") );
                    resList.add(em);
                }
            } catch (SQLException ex) {
                System.out.println(ex.toString());
            }
        }
   }
        return resList;
 }
  
   @Override
    public Employee constructEntityFromMap(HashMap<String, Object> map) {
        String codice_fiscale =(String) map.get("codice_fiscale");
        String name =(String) map.get("name");
        String surname =(String) map.get("surname");
        String role =(String) map.get("role");
        LocalDate begin_date =(LocalDate) map.get("begin_date");
        LocalDate end_date =(LocalDate) map.get("end_date");
        int wage =(int) map.get("wage");
      
        return new Employee(codice_fiscale, name, surname, role, begin_date, end_date,wage);
    }
}
