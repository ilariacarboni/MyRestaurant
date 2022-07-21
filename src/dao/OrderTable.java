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
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Natalia
 */
public class OrderTable implements Table<Order>{
    
    Connection conn = dbConnection.enstablishConnection();

    @Override
    public ArrayList<Order> getAll() {
        ArrayList <Order> resList = new ArrayList<Order>();
        String sql = "SELECT * FROM order";
        try {
            Statement stm = conn.createStatement();
            ResultSet resultSet = stm.executeQuery(sql);

            while (resultSet.next()) {
                Order o = new Order( resultSet.getInt("number"), resultSet.getDate("date").toLocalDate(), resultSet.getInt("product_barcode"), resultSet.getInt("qty"), resultSet.getInt("state"));
                resList.add(o);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
        return resList;
    }

    @Override
    public boolean save(Order o) {
        boolean res = false;
        String sql= "INSERT INTO order (number, date, product_barcode, qty, state) VALUES (?,?,?,?,?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, o.getNumber());
            ps.setDate(2, o.getDate());
            ps.setInt(3, o.getProduct());
            ps.setInt(4, o.getQty());
            ps.setInt(5, o.getState());

            ps.execute();
            res = true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }  
        return res;
    }

    @Override
    public boolean update(Order o) {
        boolean res = false;
        String sql= "UPDATE order SET date=?, product_barcode=?, qty=?, state=? WHERE number=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
                ps.setDate(1, o.getDate());
                ps.setInt(2, o.getProduct());
                ps.setDouble(3, o.getQty());
                ps.setInt(4, o.getState());
                ps.setInt(5, o.getNumber());
            ps.execute();
            res = true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return res;
    }

    @Override
    public boolean delete(Order o) {
        boolean res = false;
        String sql= "DELETE FROM order WHERE number = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, o.getNumber());
            res = true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return res;
    }

    @Override
    public ArrayList<Order> getFrom(Object searchParam, String paramName) {
        //ricerca per numero
        ArrayList<Order> resList = new ArrayList<Order>();
        if(searchParam instanceof Integer && paramName.equals("number")){
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

    @Override
    public Order constructEntityFromMap(HashMap<String, Object> map) {
        int number = (int) map.get("number");
        LocalDate date = (LocalDate) map.get("date");
        int productBarcode = (int) map.get("productBarcode");
        int qty = (int) map.get("qty");
        int state = (int) map.get("state");
        return new Order(number, date, productBarcode, qty, state);
    }
    
}
