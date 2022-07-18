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
import java.util.ArrayList;
import java.util.List;
import org.sqlite.SQLiteException;

/**
 *
 * @author Natalia
 */
public class ProductTable implements Table<Product>{
    
    Connection conn = dbConnection.enstablishConnection();
    ArrayList<Product> productsList = new ArrayList<Product>();

    @Override
    public List<Product> getAll() {
        //seleziona tutti i prodotti nel db, li inserisce uno per uno nella lista e 
        //restituisce la lista
        return productsList;
    }

    @Override
    public void save(Product p) {
        //lo inserisce nella lista e nel db
        //se il Product è nella lista significa che è stato già inserito nel db
        if(productsList.indexOf(p)<0){
            
            String sql= "INSERT INTO product (barcode, name, qty, price) VALUES (?,?,?,?)";
            try {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, p.getId());
                ps.setString(2, p.getName());
                ps.setInt(3, p.getQty());
                ps.setDouble(4, p.getPrice());
                ps.execute();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            productsList.add(p); 
        }
    }

    @Override
    public void update(Product p) {
        //t deve essere un istanza di Product con lo stesso identificativo 
        //dell'istanza che si vuole modificare
        
        String sql= "UPDATE product SET name = ?, qty=?, price=? WHERE barcode=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, p.getName());
            ps.setInt(2, p.getQty());
            ps.setDouble(3, p.getPrice());
            ps.setInt(4, p.getId());
            ps.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void delete(Product p) {
        //elimina il prodotto dalla lista (se presente) e dal db
        String sql= "DELETE FROM product WHERE barcode = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, p.getId());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        this.productsList.remove(p);
    }

    @Override
    public List<Product> getFrom(Object searchParam) {
        //ricerca per nome
        List<Product> resList = new ArrayList<Product>();
        if(searchParam.getClass().getName().replace("java.lang.", "").equals("String")){
            String sql = "SELECT * FROM product WHERE name =?";
            try {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, (String) searchParam);
                ResultSet resultSet = ps.executeQuery();
                
                while (resultSet.next()) {
                    Product p = new Product(resultSet.getInt("barcode"),resultSet.getString("name"), resultSet.getInt("qty"), resultSet.getDouble("price"));
                    resList.add(p);
                }
            } catch (SQLException ex) {
                System.out.println(ex.toString());
            }
        }
        
        return resList;
    }
    
}
