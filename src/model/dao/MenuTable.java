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
    
    Connection conn = dbConnection.enstablishConnection();
    private final String DISHES_PER_COURSE = "select m.course, count(*) as menu_number from menu m group by m.course;";
   
    @Override
    public ArrayList<Menu> getAll() {
        
        ArrayList<Menu> resList = new ArrayList<Menu>();
        String sql = "SELECT * FROM menu";
        try {
            Statement stm = conn.createStatement();
            ResultSet resultSet = stm.executeQuery(sql);
            
            while (resultSet.next()) {
                Menu m= new Menu(resultSet.getString("nameDish"),resultSet.getInt("price"),resultSet.getString("course") ,resultSet.getString("image"));
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
        //se il dipendente è nella lista significa che è stato già inserito nel db
        boolean res = false;
 
        String sql= "INSERT INTO Menu (nameDish, price, course,image) VALUES (?,?,?,?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, m.getNameDish());
            ps.setInt(2, (int)m.getPrice());
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
        //se il dipendente è nella lista significa che è stato già inserito nel db
        boolean res = false;      
        String sql= "UPDATE Menu SET nameDish=? , price=? , course=? WHERE nameDish=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, m.getNameDish());
            ps.setInt(2, (int)m.getPrice());
            ps.setString(3, m.getCourse());
            ps.setString(4, m.getImage());
            ps.execute();

            res = true;
        } catch (SQLException ex) {

            ex.printStackTrace();
        } 
        return res;
    }

 
    public boolean delete(Menu m) {
        
        boolean res = false;
            
        String sql= "DELETE FROM Menu WHERE nameDish = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, m.getNameDish());
            res = true;
        } catch (SQLException ex) {

            ex.printStackTrace();
        }

        return res;
    }
    
    
    @Override
    public ArrayList<Menu> getFrom(Object searchParam, String paramName)  { 
        String baseSql = "SELECT m.name, m.price, m.course,m.image c.name as courseName FROM menu m JOIN course c ON m.category = c.name ";
        String sql = null;
        ArrayList<Menu> resList = new ArrayList<Menu>();
        
        if(searchParam instanceof String){   //passso un oggetto di ricerca textinput
          switch(paramName){
                case "name":
                    sql = baseSql + "WHERE m.name =? ";
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
                    Menu m = new Menu(resultSet.getString("name"),resultSet.getInt("price"),resultSet.getString("course"),resultSet.getString("image"));
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
     
    public Menu constructEntityFromMap(HashMap<String, Object> map) {
        
        String nameDish =(String) map.get("nameDish");
        int price =(int) map.get("price");
        String course =(String) map.get("course");
        String image =(String) map.get("image");
        return new Menu(nameDish, price, course,image);
    }



}
