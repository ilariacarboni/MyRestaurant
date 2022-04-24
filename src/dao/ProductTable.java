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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Natalia
 */
public class ProductTable implements Table<Product>{
    
    Connection conn = dbConnection.enstablishConnection();
    ArrayList<Product> products = new ArrayList<Product>();

    @Override
    public List<Product> getAll() {
        //seleziona tutti i prodotti nel db, li inserisce uno per uno nella lista e 
        //restituisce la lista
        return products;
    }

    @Override
    public void save(Product t) {
        //lo inserisce nella lista e nel db
        //se il Product è nella lista significa che è stato già inserito nel db
        if(products.indexOf(t)<0){
            
            String sql= "INSERT INTO product (codice_barre, nome, qta, prezzo) VALUES (?,?,?,?)";
            try {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, t.getId());
                ps.setString(2, t.getNome());
                ps.setInt(3, t.getQta());
                ps.setInt(4, t.getPrezzo());
                ps.execute();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            products.add(t); 
        }
        
        
    }

    @Override
    public void update(Product t) {
        //t deve essere un istanza di Product con lo stesso identificativo 
        //dell'istanza che si vuole modificare
    }

    @Override
    public void delete(Product t) {
        //elimina il prodotto dalla lista (se presente) e dal db
    }
    
}
