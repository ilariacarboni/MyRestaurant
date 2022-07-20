/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import database.dbConnection;
import entity.Product;
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
public class ProductTable implements Table<Product>{
    
    Connection conn = dbConnection.enstablishConnection();

    @Override
    public ArrayList<Product> getAll() {
        ArrayList<Product> resList = new ArrayList<Product>();
        String sql = "SELECT * FROM product";
        try {
            Statement stm = conn.createStatement();
            ResultSet resultSet = stm.executeQuery(sql);
            
            while (resultSet.next()) {
                Product p = new Product(resultSet.getInt("barcode"), resultSet.getString("name"), resultSet.getInt("qty"), resultSet.getDouble("price"), resultSet.getString("supplier"), resultSet.getString("category"));
                resList.add(p);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
        return resList;
    }

    @Override
    public boolean save(Product p) {
        //lo inserisce nella lista e nel db
        //se il Product è nella lista significa che è stato già inserito nel db
        boolean res = false;
   
        String sql= "INSERT INTO product (barcode, name, qty, price, supplier, category) VALUES (?,?,?,?,?,?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, p.getId());
            ps.setString(2, p.getName());
            ps.setInt(3, p.getQty());
            ps.setDouble(4, p.getPrice());
            ps.setString(5, p.getSupplier());
            ps.setString(6, p.getCategory());
            ps.execute();
            res = true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return res;
    }

    @Override
    public boolean update(Product p) {
        //t deve essere un istanza di Product con lo stesso identificativo 
        //dell'istanza che si vuole modificare
        boolean res = false;
        String sql= "UPDATE product SET name = ?, qty=?, price=?, supplier=?, category=? WHERE barcode=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, p.getName());
            ps.setInt(2, p.getQty());
            ps.setDouble(3, p.getPrice());
            ps.setString(4, p.getSupplier());
            ps.setString(5, p.getCategory());
            ps.setInt(6, p.getId());
            ps.execute();
            res = true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return res;
    }

    @Override
    public boolean delete(Product p) {
        //elimina il prodotto dalla lista (se presente) e dal db
        boolean res = false;
        String sql= "DELETE FROM product WHERE barcode = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, p.getId());
            res = true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return res;
    }

    @Override
    public ArrayList<Product> getFrom(Object searchParam, String paramName) {
        
        ArrayList<Product> resList = new ArrayList<Product>();
        if(searchParam instanceof String){
            if(paramName.equals("name")){
                //search by product name
                String sql = "SELECT p.barcode, p.name, p.qty, p.price, p.category, p.supplier, c.name as categoryName FROM product p JOIN category c ON p.category = c.name WHERE p.name =? ";
                try {
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setString(1, (String) searchParam);
                    ResultSet resultSet = ps.executeQuery();

                    while (resultSet.next()) {
                        Product p = new Product(resultSet.getInt("barcode"),resultSet.getString("name"), resultSet.getInt("qty"), resultSet.getDouble("price"),resultSet.getString("supplier"), resultSet.getString("categoryName"));
                        resList.add(p);
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.toString());
                }
            }else if(paramName.equals("category")){
                //search by product category
                String sql = "SELECT p.barcode, p.name, p.qty, p.price, p.category, p.supplier, c.name as categoryName FROM product p JOIN category c ON p.category = c.name WHERE c.name = ?";
                try {
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setString(1, (String) searchParam);
                    ResultSet resultSet = ps.executeQuery();

                    while (resultSet.next()) {
                        Product p = new Product(resultSet.getInt("barcode"),resultSet.getString("name"), resultSet.getInt("qty"), resultSet.getDouble("price"),resultSet.getString("supplier"), resultSet.getString("categoryName"));
                        resList.add(p);
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.toString());
                }
            }
        }
        return resList;
    }

    @Override
    public Product constructEntityFromMap(HashMap<String, Object> map) {
        int barcode =(int) map.get("barcode");
        String name =(String) map.get("name");
        int qty =(int) map.get("qty");
        double price =(double) map.get("price");
        String supplier =(String) map.get("supplier");
        String category =(String) map.get("category");
        return new Product(barcode, name, qty, price, supplier, category);
    }
    
}
