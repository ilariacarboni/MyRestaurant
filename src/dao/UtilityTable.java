/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import database.dbConnection;
import entity.Utility;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class UtilityTable implements Table<Utility>{
    
    Connection conn = dbConnection.enstablishConnection();
    ArrayList<Utility> utilities = new ArrayList<Utility>();
  

    @Override
    public List<Utility> getAll() {
       /* String sql= "SELECT * FROM Utility (numberId, total, type, date)";
            try {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.execute();
            } catch (SQLException ex) {
                
                ex.printStackTrace();
            }*/
        
       return utilities;
    }

    @Override
    public void save(Utility u) {
        //lo inserisce nella lista e nel db
        //se il dipendente è nella lista significa che è stato già inserito nel db
        if(utilities.indexOf(u)<0){
            
            String sql= "INSERT INTO Utility (numberId, total, type, date) VALUES (?,?,?,?)";
            try {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, u.getNumberId());
                ps.setInt(2, u.getTotal());
                ps.setString(3, u.getType());
                ps.setDate(4, u.getDate());
                ps.execute();
            } catch (SQLException ex) {
                
                ex.printStackTrace();
            }

            utilities.add(u); 
        }
        
    }

    @Override
    public void update (Utility u) {
        //t deve essere un istanza di Product con lo stesso identificativo 
        //dell'istanza che si vuole modificare
         if(utilities.indexOf(u)>0){ //seleziono istanza corretta
            
            String sql= "UPDATE Utility  SET numberId = ?, total = ? , type =? , date = ? WHERE numberId = ?";
            try {
                 PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, u.getNumberId());
                ps.setInt(2, u.getTotal());
                ps.setString(3, u.getType());
		ps.setInt(4, u.getNumberId());
                ps.setDate(5, u.getDate());
                ps.execute();
            } catch (SQLException ex) {
                
                ex.printStackTrace();
            }

         }   
    }
  

    @Override
    public void delete(Utility u) {
       
	if(utilities.indexOf(u)>0){
            
            String sql= "DELETE FROM Utility WHERE numberId = ?";
            try {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, u.getNumberId());
                ps.execute();

            } catch (SQLException ex) {
                
                ex.printStackTrace();
            }
        }
    }
    
    
    public List<Utility> search (Object catch_input) { 
        
        List<Utility> resList = new ArrayList<Utility>();
        if(catch_input instanceof String){   //passso un oggetto di ricerca textinput
         
        String sql= "SELECT * FROM Utility WHERE type =? ORDER BY date";
        try {
                PreparedStatement ps = conn.prepareStatement(sql); 
                ps.setString(1, (String) catch_input);
                ps.execute();
                ResultSet resultSet = ps.executeQuery();
                
                while (resultSet.next()) {
                    Utility u = new Utility(resultSet.getInt("numberId"), resultSet.getInt("total"),resultSet.getString("type"),resultSet.getDate("date").toLocalDate());
                    resList.add(u);
                }
            }
            catch (SQLException ex) {
        
                ex.printStackTrace();
            }   
        } 
        
        else if (catch_input instanceof Integer) {
            
        String sql=  "SELECT * FROM Utility WHERE numberId=? OR total = ? ORDER BY date"; 
       
        
            try {
                PreparedStatement ps = conn.prepareStatement(sql); 
                ps.setInt(1, (int) catch_input);
                ps.setInt(2, (int) catch_input);
                ps.execute();
                ResultSet resultSet = ps.executeQuery();
                
                while (resultSet.next()) {
                    Utility u = new Utility(resultSet.getInt("numberId"), resultSet.getInt("total"),resultSet.getString("type"),resultSet.getDate("date").toLocalDate());
                    resList.add(u);
                }
            }
            catch (SQLException ex) {
        
                ex.printStackTrace();
            }  
        }
            
        else if (catch_input instanceof LocalDate) {
            
        String sql=  "SELECT * FROM Utility WHERE date =? ORDER BY date"; 
       
        
            try {
                PreparedStatement ps = conn.prepareStatement(sql); 
                ps.setDate(1, (Date) catch_input);
                ps.execute();
                ResultSet resultSet = ps.executeQuery();
                
                while (resultSet.next()) {
                    Utility u = new Utility(resultSet.getInt("numberId"), resultSet.getInt("total"),resultSet.getString("type"),resultSet.getDate("date").toLocalDate());
                    resList.add(u);
                }
            }
            catch (SQLException ex) {
        
                ex.printStackTrace();
            }
        }     
    }
    
}



//"SELECT * FROM Utility WHERE numberId= ? OR total = ? OR  type =? OR date = ? ORDER BY numberID";