/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.database;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * class used to establish and close the connection to the db
 * @author Natalia
 */
public class dbConnection {
    
    private static Connection conn = null;
    public static Connection establishConnection(){
        
        if(conn==null){
            try {
                Class.forName("org.sqlite.JDBC");
                String url = "jdbc:sqlite::resource:RestaurantDB.db";
                conn = DriverManager.getConnection(url);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return conn;
    }
}
