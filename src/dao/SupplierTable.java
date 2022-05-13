/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Supplier;
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
public class SupplierTable implements Table<Supplier>{

    Connection conn = dbConnection.enstablishConnection();
    ArrayList<Supplier> suppliersList = new ArrayList<Supplier>();
    
    
    @Override
    public List<Supplier> getAll() {
        return this.suppliersList;
    }

    @Override
    public void save(Supplier s) {
        if(suppliersList.indexOf(s)<0){
            
            String sql= "INSERT INTO supplier (name, site) VALUES (?,?)";
            try {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, s.getName());
                ps.setString(2, s.getSite());
                ps.execute();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            suppliersList.add(s);
        }
    }

    @Override
    public void update(Supplier s){
        
        String sql= "UPDATE supplier SET site = ? WHERE name=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, s.getSite());
                ps.setString(2, s.getName());
            ps.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
    }

    @Override
    public void delete(Supplier s) {
        String sql= "DELETE FROM supplier WHERE name = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, s.getName());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        this.suppliersList.remove(s);
    }

    @Override
    public List<Supplier> getFrom(Object searchParam) {
        //ricerca per nome 
        List<Supplier> resList = new ArrayList<Supplier>();
        if(searchParam.getClass().getName().replace("java.lang.", "").equals("String")){
            String sql = "SELECT * FROM supplier WHERE name =?";
            try {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, (String) searchParam);
                ResultSet resultSet = ps.executeQuery();
                
                while (resultSet.next()) {
                    Supplier s = new Supplier(resultSet.getString("name"), resultSet.getString("site"));
                    resList.add(s);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        
        return resList;
    }
    
}
