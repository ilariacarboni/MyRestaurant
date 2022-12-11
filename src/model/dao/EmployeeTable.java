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
    
    Connection conn = dbConnection.establishConnection();
    //ArrayList<Employee> employeesList = new ArrayList<Employee>();

    @Override
    public ArrayList<Employee> getAll() {
        ArrayList <Employee> resList = new ArrayList<Employee>();
        String sql = "SELECT * FROM employee";
        Statement stm = null;
        try {
            stm = conn.createStatement();
            ResultSet resultSet = stm.executeQuery(sql);

            while (resultSet.next()) {
                Employee em = new Employee( resultSet.getString("codice_fiscale"),resultSet.getString("name"),resultSet.getString("surname"),
                            resultSet.getString("role"),resultSet.getString("begin_date"),resultSet.getString("end_date"),resultSet.getInt("wage"), resultSet.getString("image") );
                resList.add(em);
            }
        } catch (SQLException ex) {
            resList = null;
        }finally {
            try {
                stm.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return resList;
    }

    @Override
    public boolean save(Employee em) {
        boolean res = false;
            
        String sql= "INSERT INTO employee (codice_fiscale, name, surname, role, begin_date, end_date, wage,image) VALUES (?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, em.getCodiceF());
            ps.setString(2, em.getName());
            ps.setString(3, em.getSurname());
    		ps.setString(4, em.getRole());
            ps.setString(5, em.getBeginDate());
	    	ps.setString(6, em.getEndDate());
            ps.setInt(7, em.getWage());
            ps.setString(8, em.getImage());
            ps.execute();
                
            res = true;
        } catch (SQLException ex) {
        }
        return res;
    }

    @Override
    public boolean update(Employee em) {
        boolean res = false;
	    PreparedStatement ps = null;
	    String sql= "UPDATE employee SET end_date = ? WHERE codice_fiscale = ?";
        try {
            ps = conn.prepareStatement(sql);
		    ps.setString(1, em.getEndDate());
            ps.setString(2, em.getCodiceF());
            ps.execute();
                
            res = true;
        }
        catch (SQLException ex) {
        } finally {
            try {
                ps.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
             }
        }
        return res;
    }

 
    public boolean delete (Employee em) {
        boolean res = false;
        
            String sql= "DELETE FROM employee WHERE codice_fiscale = ?";
            PreparedStatement ps = null;
            try {
                ps = conn.prepareStatement(sql);
                ps.setString(1, em.getCodiceF());
                ps.execute();
                res = true;
            } catch (SQLException ex) {
            }finally {
            try {
                ps.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return res;
    
  }
    
    
   public ArrayList<Employee> getFrom(Object searchParam, String paramName) {
        //ricerca per nome
        ArrayList<Employee> resList = new ArrayList<Employee>();
        if(searchParam instanceof String){
            if(paramName.equals("name")||paramName.equals("surname")||paramName.equals("role")){
            String sql = "SELECT * FROM Employee WHERE codice_fiscale=? OR  name=? OR  surname =? OR  role=? ORDER BY name";
            PreparedStatement ps = null;
            try {
                ps = conn.prepareStatement(sql);
                ps.setString(1, (String) searchParam);
                 ps.setString(2, (String) searchParam);
                 ps.setString(3, (String) searchParam);
                 ps.setString(4, (String) searchParam);
                ResultSet resultSet = ps.executeQuery();
                
                while (resultSet.next()) {
                    Employee em = new Employee(resultSet.getString("codice_fiscale"), resultSet.getString("name"),resultSet.getString("surname"),
                            resultSet.getString("role"),resultSet.getString("begin_date"),resultSet.getString("end_date"),resultSet.getInt("wage"),resultSet.getString("image") );
                    resList.add(em);
                }
            } catch (SQLException ex) {
                resList = null;
            } finally {
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
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
        String begin_date = (String) map.get("begin_date");
        String end_date = (String) map.get("end_date");
        int wage =(int) map.get("wage");
        String image = (String) map.get("image");
      
        return new Employee(codice_fiscale, name, surname, role, begin_date, end_date, wage,image);
    }
}
