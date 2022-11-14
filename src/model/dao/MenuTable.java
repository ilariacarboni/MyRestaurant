package model.dao;

/**
 *
 * @author milar
 */
import model.database.dbConnection;
import model.entity.Menu;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;


public class MenuTable implements Table<Menu>{
    
    
    Connection conn = dbConnection.establishConnection();
    private final String DISHES_PER_COURSE = "select m.course, count(*) as menu_number from menu m group by m.course;";
    private final String MOST_REQUESTED_PER_COURSE = "SELECT m.course, ri.dish \n" +
    "FROM receipt_item ri JOIN menu m ON ri.dish = m.nameDish\n" +
    "WHERE ( SELECT MAX(qty) as qty_dish FROM receipt_item GROUP BY dish ) \n" +
    "GROUP BY m.course; ";
   
    @Override
    public ArrayList<Menu> getAll() {
        
        ArrayList<Menu> resList = new ArrayList<Menu>();
        String sql = "SELECT * FROM menu";
        try {
            Statement stm = conn.createStatement();
            ResultSet resultSet = stm.executeQuery(sql);
            
            while (resultSet.next()) {
                Menu m= new Menu(resultSet.getString("nameDish"),resultSet.getDouble("price"),resultSet.getString("course") ,resultSet.getString("image"));
                resList.add(m);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
        return resList;
    }

    @Override
    public boolean save(Menu m) {
        //lo inserisce nella lista e nel db
        //se il menu è nella lista significa che è stato già inserito nel db
        boolean res = false;
 
        String sql= "INSERT INTO menu (nameDish, price, course, image) VALUES (?,?,?,?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, m.getNameDish());
            ps.setDouble(2, m.getPrice());
            ps.setString(3, m.getCourse());
            ps.setString(4, m.getImage());
            ps.execute();

            res = true;
        } catch (SQLException ex) {

            ex.printStackTrace();
        }

        return res;
    }

    @Override
     public boolean update (Menu m) {
        //lo inserisce nella lista e nel db
        //se il menu è nella lista significa che è stato già inserito nel db
        boolean res = false;      
        String sql= "UPDATE menu SET price = ? image = ? WHERE nameDish = ?";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setDouble(1, m.getPrice());
            ps.setString(2, m.getNameDish());
            ps.execute();

            res = true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        } 
        return res;
    }

 
    public boolean delete(Menu m) {
        
        boolean res = false;
            
        String sql= "DELETE FROM menu WHERE nameDish = ?";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, m.getNameDish());
            ps.execute();
            res = true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
            try {
                ps.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return res;
    }
    
    
    @Override
    public ArrayList<Menu> getFrom(Object searchParam, String paramName)  { 
        String baseSql = "SELECT m.nameDish, m.price, m.course, m.image, c.name as courseName FROM menu m JOIN course c ON m.course = c.name ";
        String sql = null;
        ArrayList<Menu> resList = new ArrayList<Menu>();
        
        if(searchParam instanceof String){   //passso un oggetto di ricerca textinput
          switch(paramName){
                case "nameDish":
                    sql = baseSql + "WHERE m.nameDish =? ";
                    break;
                case "course":
                    sql = baseSql + "WHERE c.name = ?";
                    break;
            }
        try {
                PreparedStatement ps = conn.prepareStatement(sql); 
                ps.setString(1, (String) searchParam);
               // ps.execute();
                ResultSet resultSet = ps.executeQuery();
                
                while (resultSet.next()) {
                    Menu m = new Menu(resultSet.getString("nameDish"),resultSet.getDouble("price"),resultSet.getString("course"),resultSet.getString("image"));
                    resList.add(m);
                }
            }
            catch (SQLException ex) {
        
                ex.printStackTrace();
            }   
        }
    return resList;
    }
     
    public HashMap<String, Integer> getTotalDishesPerCourse(){
        HashMap<String, Integer> res = null;
        try{
            Statement stm = conn.createStatement();
            ResultSet resultSet = stm.executeQuery(this.DISHES_PER_COURSE);
            res = new HashMap<String, Integer>();
            while (resultSet.next()) {
                String course = resultSet.getString("course");
                int dishNumber = resultSet.getInt("menu_number");
                res.put(course, dishNumber);
            }
        }catch (SQLException ex){
            System.out.println(ex.toString());
        }
        return res;
     }
    
    public HashMap<String, String> getMostRequestedPerCourse(){
        HashMap<String, String> res = null;
        try{
            Statement stm = conn.createStatement();
            ResultSet resultSet = stm.executeQuery(this.MOST_REQUESTED_PER_COURSE);
            res = new HashMap<String, String>();
            while (resultSet.next()) {
                String course = resultSet.getString("course");
                String dishName = resultSet.getString("dish");
                res.put(course, dishName);
            }
        }catch (SQLException ex){
            System.out.println(ex.toString());
        }
        return res;
     }
     
    public Menu constructEntityFromMap(HashMap<String, Object> map) {
        
        String nameDish =(String) map.get("nameDish");
        double price =(double) map.get("price");
        String course =(String) map.get("course");
        String image =(String) map.get("image");
        return new Menu(nameDish, price, course, image);
    }



}
