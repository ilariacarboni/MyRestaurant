/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Receipt;
import database.dbConnection;
import java.sql.Connection;
import java.sql.Date;
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
public class ReceiptTable implements Table<Receipt>{

    Connection conn = dbConnection.enstablishConnection();
    ArrayList<Receipt> receiptsList = new ArrayList<Receipt>();
    
    
    @Override
    public ArrayList<Receipt> getAll() {
        ArrayList <Receipt> resList = new ArrayList<Receipt>();
        String sql = "SELECT * FROM receipt";
        try {
            Statement stm = conn.createStatement();
            ResultSet resultSet = stm.executeQuery(sql);

            while (resultSet.next()) {
                Receipt r = new Receipt(resultSet.getInt("number"), resultSet.getDate("date").toLocalDate(), resultSet.getDouble("total"));
                resList.add(r);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
        this.receiptsList = resList;
        return resList;
    }

    @Override
    public boolean save(Receipt r) {
        boolean res = false;
        if(receiptsList.indexOf(r)<0){
            
            String sql= "INSERT INTO receipt (number, date, total) VALUES (?,?,?)";
            try {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, r.getNumber());
                ps.setDate(2, r.getDate());
                ps.setDouble(3, r.getTotal());
                ps.execute();
                res = true;
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            receiptsList.add(r); 
        }
        return res;
    }

    @Override
    public boolean update(Receipt r) {
        boolean res = false;
        String sql= "UPDATE receipt SET date = ?, total=? WHERE number=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
                ps.setDate(1, r.getDate());
                ps.setDouble(2, r.getTotal());
                ps.setInt(3, r.getNumber());
            ps.execute();
            res = true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return res;
    }

    @Override
    public boolean delete(Receipt r) {
        boolean res = false;
        String sql= "DELETE FROM receipt WHERE number = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, r.getNumber());
            ps.execute();
            res = true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        this.receiptsList.remove(r);
        return res;
    }

    
    public ArrayList<Receipt> getFrom(Object searchParam, String paramName) {
        //ricerca dello scontrino per data o numero
        //numero
        ArrayList<Receipt> resList = new ArrayList<Receipt>();
        if(searchParam instanceof Integer && paramName.equals("number")){
            String sql = "SELECT * FROM receipt WHERE number =?";
            try {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, (int) searchParam);
                ResultSet resultSet = ps.executeQuery();
                
                while (resultSet.next()) {
                    Receipt r = new Receipt(resultSet.getInt("number"),resultSet.getDate("date").toLocalDate(), resultSet.getDouble("total"));
                    resList.add(r);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }else if(searchParam instanceof Date && paramName.equals("date")){
            System.out.println("class Date");
            //uso Date perchè posso fare il cast da Object a Date
            String sql = "SELECT * FROM receipt WHERE date =?";
            try {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setDate(1, (Date) searchParam);
                ResultSet resultSet = ps.executeQuery();
                
                while (resultSet.next()) {
                    Receipt r = new Receipt(resultSet.getInt("number"),resultSet.getDate("date").toLocalDate(), resultSet.getDouble("total"));
                    resList.add(r);
                }
            } catch (SQLException ex) {
            ex.printStackTrace();
            }
        }
        
        return resList;
    }

    @Override
    public Receipt constructEntityFromMap(HashMap<String, Object> map) {
        int number = (int) map.get("number");
        LocalDate date = (LocalDate) map.get("date");
        double total = (double) map.get("total");
        return new Receipt(number, date, total);
    }
    
}
