/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * class used to enstablish and close the connection to the db
 * @author Natalia
 */
public class dbConnection {
    
    private static Connection conn = null;
    //associato alla classe e non all'istanza
    public static Connection enstablishConnection(){
        
        if(conn==null){
            try {
                Class.forName("org.sqlite.JDBC");
                String url = "jdbc:sqlite:RestaurantDB.db";
                return DriverManager.getConnection(url);
            } catch (Exception e) {
                e.printStackTrace();
            }
        
        }
           
        return conn;
    }
    
}
