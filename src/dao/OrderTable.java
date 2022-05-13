/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Order;
import database.dbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Natalia
 */
public class OrderTable implements Table<Order>{
    
    Connection conn = dbConnection.enstablishConnection();
    ArrayList<Order> ordersList = new ArrayList<Order>();

    @Override
    public List<Order> getAll() {
        return ordersList;
    }

    @Override
    public void save(Order o) {
        
        
        if(ordersList.indexOf(o)<0){
            
            String sql= "INSERT INTO order (number, date, product_barcode, qty, state) VALUES (?,?,?,?,?)";
            try {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, o.getNumber());
                ps.setDate(2, o.getDate());
                ps.setInt(3, o.getProduct());
                ps.setInt(4, o.getQty());
                ps.setInt(5, o.getState());
                
                ps.execute();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            ordersList.add(o); 
        }
    }

    @Override
    public void update(Order o) {
        
        String sql= "UPDATE order SET date=?, product_barcode=?, qty=?, state=? WHERE number=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
                ps.setDate(1, o.getDate());
                ps.setInt(2, o.getProduct());
                ps.setDouble(3, o.getQty());
                ps.setInt(4, o.getState());
                ps.setInt(5, o.getNumber());
            ps.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void delete(Order o) {
        
        String sql= "DELETE FROM order WHERE number = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, o.getNumber());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        this.ordersList.remove(o);
    }

    @Override
    public List<Order> getFrom(Object searchParam) {
        //ricerca per numero
        
        List<Order> resList = new ArrayList<Order>();
        if(searchParam.getClass().getName().replace("java.lang.", "").equals("Integer")){
            String sql = "SELECT * FROM order WHERE number =?";
            try {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, (int) searchParam);
                ResultSet resultSet = ps.executeQuery();
                
                while (resultSet.next()) {
                    Order o = new Order(resultSet.getInt("number"),resultSet.getDate("date").toLocalDate(),resultSet.getInt("product_barcode"), resultSet.getInt("qty"), resultSet.getInt("state"));
                    resList.add(o);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        
        return resList;
    }
    
}
