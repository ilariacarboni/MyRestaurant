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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Natalia
 */
public class ReceiptTable implements Table<Receipt>{

    Connection conn = dbConnection.enstablishConnection();
    ArrayList<Receipt> receiptsList = new ArrayList<Receipt>();
    
    
    @Override
    public List<Receipt> getAll() {
        return this.receiptsList;
    }

    @Override
    public void save(Receipt r) {
        
        if(receiptsList.indexOf(r)<0){
            
            String sql= "INSERT INTO receipt (number, date, total) VALUES (?,?,?)";
            try {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, r.getNumber());
                ps.setDate(2, r.getDate());
                ps.setDouble(3, r.getTotal());
                ps.execute();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            receiptsList.add(r); 
        }
    }

    @Override
    public void update(Receipt r) {
        
        String sql= "UPDATE receipt SET date = ?, total=? WHERE number=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
                ps.setDate(1, r.getDate());
                ps.setDouble(2, r.getTotal());
                ps.setInt(3, r.getNumber());
            ps.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void delete(Receipt r) {
        String sql= "DELETE FROM receipt WHERE number = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, r.getNumber());
            ps.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        this.receiptsList.remove(r);
    }

    
    public List<Receipt> getFrom(Object searchParam) {
        //ricerca dello scontrino per data o numero
        //numero
        List<Receipt> resList = new ArrayList<Receipt>();
        if(searchParam.getClass().getName().replace("java.lang.", "").equals("Integer")){
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
        }else if(searchParam.getClass().getName().replace("java.sql.", "").equals("Date")){
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
    
}
