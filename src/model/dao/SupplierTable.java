/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import model.entity.Supplier;
import model.database.dbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Natalia
 */
public class SupplierTable implements Table<Supplier>{

    Connection conn = dbConnection.establishConnection();

    @Override
    public ArrayList<Supplier> getAll() {
        ArrayList<Supplier> resList = new ArrayList<Supplier>();
        String sql = "SELECT * FROM supplier";
        try {
            Statement stm = conn.createStatement();
            ResultSet resultSet = stm.executeQuery(sql);
            
            while (resultSet.next()) {
                Supplier s = new Supplier(resultSet.getString("name"), resultSet.getString("site"));
                resList.add(s);
            }
        } catch (SQLException ex) {
            resList = null;
        }
        return resList;
    }

    @Override
    public boolean save(Supplier s) {
        boolean res = false;
        String sql= "INSERT INTO supplier (name, site) VALUES (?,?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, s.getName());
            ps.setString(2, s.getSite());
            ps.execute();
            res = true;
        } catch (SQLException ex) {
        }
        return res;
    }

    @Override
    public boolean update(Supplier s){
        boolean res = false;
        String sql= "UPDATE supplier SET site = ? WHERE name=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, s.getSite());
            ps.setString(2, s.getName());
            ps.execute();
            res = true;
        } catch (SQLException ex) {
        }
        return res;
    }

    @Override
    public boolean delete(Supplier s) {
        boolean res = false;
        String sql= "DELETE FROM supplier WHERE name = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, s.getName());
            res = true;
        } catch (SQLException ex) {
        }
        return res;
    }

    @Override
    public ArrayList<Supplier> getFrom(Object searchParam, String paramName) {
        //ricerca per nome 
        ArrayList<Supplier> resList = new ArrayList<Supplier>();
        if(searchParam instanceof String && paramName.equals("name")){
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
                resList = null;
            }
        }
        
        return resList;
    }

    @Override
    public Supplier constructEntityFromMap(HashMap<String, Object> map) {
        String name = (String) map.get("name");
        String site = (String) map.get("site");
        return new Supplier(name, site);
    }
    
}
