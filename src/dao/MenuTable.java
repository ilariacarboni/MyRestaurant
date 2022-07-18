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
import java.util.ArrayList;
import java.util.List;


public class MenuTable implements Table<Menu>{
    
    Connection conn = dbConnection.enstablishConnection();
    ArrayList<Menu> menues = new ArrayList<Menu>();

    @Override
    public List<Menu> getAll() {
        /*
        String sql= "SELECT * FROM Menu ";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.execute();
            } catch (SQLException ex) {
                
             ex.printStackTrace();
            }*/
        return menues;
    }

    @Override
    public void save(Menu m) {
        //lo inserisce nella lista e nel db
        //se il dipendente è nella lista significa che è stato già inserito nel db
        if(menues.indexOf(m)<0){
            
            String sql= "INSERT INTO Menu (nameDish, price, category) VALUES (?,?,?)";
            try {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, m.getNameDish());
                ps.setInt(2, m.getPrice());
                ps.setString(3, m.getCategory());
                ps.execute();
            } catch (SQLException ex) {
                
                ex.printStackTrace();
            }

            menues.add(m); 
        }
        
    }

    @Override
     public void update (Menu m) {
        //lo inserisce nella lista e nel db
        //se il dipendente è nella lista significa che è stato già inserito nel db
        if(menues.indexOf(m)>0){
            
            String sql= "UPDATE Menu SET nameDish=? , price=? , category=? WHERE nameDish=?";
            try {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, m.getNameDish());
                ps.setInt(2, m.getPrice());
                ps.setString(3, m.getCategory());
                ps.execute();
            } catch (SQLException ex) {
                
                ex.printStackTrace();
            }
        }   
    }

 
    public void delete(Menu m) {
       
	if(menues.indexOf(m)>0){
            
            String sql= "DELETE FROM Menu WHERE nameDish = ?";
            try {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, m.getNameDish());
                ps.execute();
            } catch (SQLException ex) {
                
                ex.printStackTrace();
            }
        }
    }
    
    public List<Menu> search (Object catch_input) { 
        
        List<Menu> resList = new ArrayList<Menu>();
        if(catch_input instanceof String){   //passso un oggetto di ricerca textinput
         
        String sql= "SELECT * FROM Menu WHERE nameDish =? ";
        try {
                PreparedStatement ps = conn.prepareStatement(sql); 
                ps.setString(1, (String) catch_input);
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
        
        else if (catch_input instanceof Integer) {
            
        String sql=  "SELECT * FROM Utility WHERE price=? "; 
       
        
            try {
                PreparedStatement ps = conn.prepareStatement(sql); 
                ps.setInt(1, (int) catch_input);
               
                ps.execute();
                ResultSet resultSet = ps.executeQuery();
                
                while (resultSet.next()) {
                    Menu m = new Menu(resultSet.getString("nameDish"),resultSet.getInt("price"));
                    resList.add(m);
                }
            }
            catch (SQLException ex) {
        
                ex.printStackTrace();
            }  
        }
    
}
