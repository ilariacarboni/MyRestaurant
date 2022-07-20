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
import entity.Menu;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MenuTable implements Table<Menu>{
    
    Connection conn = dbConnection.enstablishConnection();
    ArrayList<Menu> menuList = new ArrayList<Menu>();

    @Override
    public ArrayList<Menu> getAll() {
        
        ArrayList<Menu> resList = new ArrayList<Menu>();
        String sql = "SELECT * FROM menu";
        try {
            Statement stm = conn.createStatement();
            ResultSet resultSet = stm.executeQuery(sql);
            
            while (resultSet.next()) {
                Menu m= new Menu(resultSet.getString("nameDish"),resultSet.getInt("price"), resultSet.getString("category"));
                resList.add(m);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
        menuList = resList;
        return menuList;
    }

    @Override
    public boolean save(Menu m) {
        //lo inserisce nella lista e nel db
        //se il dipendente è nella lista significa che è stato già inserito nel db
        boolean res = false;
        if(menuList.indexOf(m)<0){
            
            String sql= "INSERT INTO Menu (nameDish, price, category) VALUES (?,?,?)";
            try {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, m.getNameDish());
                ps.setInt(2, m.getPrice());
                ps.setString(3, m.getCategory());
                ps.execute();
                
                res = true;
            } catch (SQLException ex) {
                
                ex.printStackTrace();
            }

            menuList.add(m); 
        }
        return res;
    }

    @Override
     public boolean update (Menu m) {
        //lo inserisce nella lista e nel db
        //se il dipendente è nella lista significa che è stato già inserito nel db
        boolean res = false;
        if(menuList.indexOf(m)>0){
            
            String sql= "UPDATE Menu SET nameDish=? , price=? , category=? WHERE nameDish=?";
            try {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, m.getNameDish());
                ps.setInt(2, m.getPrice());
                ps.setString(3, m.getCategory());
                ps.execute();
                
                res = true;
            } catch (SQLException ex) {
                
                ex.printStackTrace();
            }
            
        }  
        return res;
    }

 
    public boolean delete(Menu m) {
        
        boolean res = false;
        
	if(menuList.indexOf(m)>0){
            
            String sql= "DELETE FROM Menu WHERE nameDish = ?";
            try {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, m.getNameDish());
                res = true;
            } catch (SQLException ex) {
                
                ex.printStackTrace();
            }
        }
        this.menuList.remove(m);
        return res;
    }
    
    
    @Override
     public ArrayList<Menu> getFrom(Object searchParam, String paramName)  { 
        
        ArrayList<Menu> resList = new ArrayList<Menu>();
        
        if(searchParam instanceof String){   //passso un oggetto di ricerca textinput
           if(paramName.equals("nameDish")){
            String sql= "SELECT * FROM Menu WHERE nameDish =? ";
        try {
                PreparedStatement ps = conn.prepareStatement(sql); 
                ps.setString(1, (String) searchParam);
                ps.execute();
                ResultSet resultSet = ps.executeQuery();
                
                while (resultSet.next()) {
                    Menu m = new Menu(resultSet.getString("name"),resultSet.getInt("price"),resultSet.getString("category"));
                    resList.add(m);
                }
            }
            catch (SQLException ex) {
        
                ex.printStackTrace();
            }   
        }
        }
        
        else if (searchParam instanceof Integer) {
            if(paramName.equals("price")){
            
        String sql=  "SELECT * FROM Menu WHERE price=? "; 
       
        
            try {
                PreparedStatement ps = conn.prepareStatement(sql); 
                ps.setInt(1, (int) searchParam);
               
                ps.execute();
                ResultSet resultSet = ps.executeQuery();
                
                while (resultSet.next()) {
                    Menu m = new Menu(resultSet.getString("nameDish"),resultSet.getInt("price"),resultSet.getString("category"));
                    resList.add(m);
                }
            }
            catch (SQLException ex) {
        
                ex.printStackTrace();
            }  
        }
   
    }
    return resList;
}
   
    public Menu constructEntityFromMap(HashMap<String, Object> map) {
        
        String nameDish =(String) map.get("nameDish");
        int price =(int) map.get("price");
        String category =(String) map.get("category");
        return new Menu(nameDish, price, category);
    }



}
