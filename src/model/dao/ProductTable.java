/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import model.database.dbConnection;
import model.entity.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author Natalia
 */
public class ProductTable implements Table<Product>{

    Connection conn = dbConnection.establishConnection();
    private final String PRODUCT_USAGE_QUERY =
            "select strftime('%m-%Y', r.date) as month, count(*) as count\n" +
            "from receipt r \n" +
            "join receipt_item ri on r.number=ri.receipt\n" +
            "join composition c on ri.dish = c.dish\n" +
            "where c.product = ? and r.date between date('now', '-1 year') and date('now')\n" +
            "group by month order by strftime('%Y', r.date) asc , strftime('%m', r.date);";

    private final String PRODUCT_ORDERS_QUERY = "select strftime('%m-%Y', o.date) as month, count(*) as tot from orders o where o.product_barcode = ?\n" +
            "group by strftime('%m-%Y', o.date)\n" +
            "order by strftime('%Y', o.date) asc , strftime('%m', o.date);";
    private final String PRODUCTS_PER_CATEGORY_QUERY = "select p.category, count(*) as prod_number from product p group by p.category;";
    private final String WAREHOUSE_COMPOSITION_QUERY = "select category, sum(qty) as qty from product group by category;";

    @Override
    public ArrayList<Product> getAll() {
        ArrayList<Product> resList = new ArrayList<Product>();
        String sql = "SELECT * FROM product";
        try {
            Statement stm = conn.createStatement();
            ResultSet resultSet = stm.executeQuery(sql);
            while (resultSet.next()) {
                Product p = new Product(resultSet.getInt("barcode"), resultSet.getString("name"), resultSet.getInt("qty"), resultSet.getDouble("price"), resultSet.getString("supplier"), resultSet.getString("category"), resultSet.getString("image"));
                resList.add(p);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
        return resList;
    }

    @Override
    public boolean save(Product p) {
        boolean res = false;
   
        String sql= "INSERT INTO product (barcode, name, qty, price, supplier, category, image) VALUES (?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, p.getId());
            ps.setString(2, p.getName());
            ps.setInt(3, p.getQty());
            ps.setDouble(4, p.getPrice());
            ps.setString(5, p.getSupplier());
            ps.setString(6, p.getCategory());
            ps.setString(7, p.getImage());
            ps.execute();
            res = true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return res;
    }

    @Override
    public boolean update(Product p) {
        boolean res = false;
        String sql= "UPDATE product SET name = ?, qty=?, price=?, supplier=?, category=?, image=? WHERE barcode=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, p.getName());
            ps.setInt(2, p.getQty());
            ps.setDouble(3, p.getPrice());
            ps.setString(4, p.getSupplier());
            ps.setString(5, p.getCategory());
            ps.setString(6, p.getImage());
            ps.setInt(7, p.getId());
            int test = ps.executeUpdate();
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
        String baseSql = "SELECT p.barcode, p.name, p.qty, p.price, p.category, p.supplier, p.image, c.name as categoryName FROM product p JOIN category c ON p.category = c.name ";
        String sql = null;
        ArrayList<Product> resList = new ArrayList<Product>();
        if(searchParam instanceof String){
            switch(paramName){
                case "name":
                    sql = baseSql + "WHERE p.name =? ";
                    break;
                case "category":
                    sql = baseSql + "WHERE c.name = ?";
                    break;
            }
            try {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, (String) searchParam);
                ResultSet resultSet = ps.executeQuery();

                while (resultSet.next()) {
                    Product p = new Product(resultSet.getInt("barcode"),resultSet.getString("name"), resultSet.getInt("qty"), resultSet.getDouble("price"),resultSet.getString("supplier"), resultSet.getString("categoryName"), resultSet.getString("image"));
                    resList.add(p);
                }
            } catch (SQLException ex) {
                System.out.println(ex.toString());
            }
        }else if(searchParam instanceof Integer){
            switch(paramName){
                case "barcode":
                    sql = baseSql + "WHERE p.barcode = ?";
                    break;
            }
            try {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, (int) searchParam);
                ResultSet resultSet = ps.executeQuery();

                while (resultSet.next()) {
                    Product p = new Product(resultSet.getInt("barcode"),resultSet.getString("name"), resultSet.getInt("qty"), resultSet.getDouble("price"),resultSet.getString("supplier"), resultSet.getString("categoryName"), resultSet.getString("image"));
                    resList.add(p);
                }
            } catch (SQLException ex) {
                System.out.println(ex.toString());
            }
        }
        return resList;
    }


    public ArrayList<Product> getWithLikeCondition(Object searchParam, String paramName){
        ArrayList<Product> resList = new ArrayList<Product>();
        String sql = "SELECT * FROM product p WHERE ";
        switch (paramName){
            case "name":
                sql += "p.name LIKE '"+searchParam+"%' ";
                break;
        }
        sql += "LIMIT 20";
        try{
            Statement stm = conn.createStatement();
            ResultSet resultSet = stm.executeQuery(sql);
            while (resultSet.next()) {
                Product p = new Product(resultSet.getInt("barcode"),resultSet.getString("name"), resultSet.getInt("qty"), resultSet.getDouble("price"),resultSet.getString("supplier"), resultSet.getString("category"), resultSet.getString("image"));
                resList.add(p);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return resList;
    }

    /**
     * @param productBarcode the barcode of the product
     * @return an HashMap with its usage per month;
     * the HashMap has as key the numeric representation of the month and as value the usage of the product
     */
    public LinkedHashMap<String,Integer> getProductUsageInLastYear(int productBarcode){
        LinkedHashMap<String, Integer> res = null;
        try {
            PreparedStatement ps = conn.prepareStatement(this.PRODUCT_USAGE_QUERY);
            ps.setInt(1, productBarcode);
            ResultSet resultSet = ps.executeQuery();
            res = new LinkedHashMap<String, Integer>();
            while (resultSet.next()) {
                String month = resultSet.getString("month");
                int usage = resultSet.getInt("count");
                res.put(month, usage);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
        return res;
    }

    public LinkedHashMap<String, Integer> getProductOrdersInLastYear(int productBarcode){
        LinkedHashMap<String, Integer> res = null;
        try {
            PreparedStatement ps = conn.prepareStatement(this.PRODUCT_ORDERS_QUERY);
            ps.setInt(1, productBarcode);
            ResultSet resultSet = ps.executeQuery();
            res = new LinkedHashMap<String, Integer>();
            while (resultSet.next()) {
                String month = resultSet.getString("month");
                int totOrders = resultSet.getInt("tot");
                res.put(month, totOrders);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
        return res;
    }

    public HashMap<String, Integer> getTotalProductsPerCategory(){
        HashMap<String, Integer> res = null;
        try{
            Statement stm = conn.createStatement();
            ResultSet resultSet = stm.executeQuery(this.PRODUCTS_PER_CATEGORY_QUERY);
            res = new HashMap<String, Integer>();
            while (resultSet.next()) {
                String category = resultSet.getString("category");
                int prodNumber = resultSet.getInt("prod_number");
                res.put(category, prodNumber);
            }
        }catch (SQLException ex){
            System.out.println(ex.toString());
        }
        return res;
    }

    /**
     *
     * @return an HashMap which elements are made by:
     * key = the category name, value = its percentage of occupancy of the warehouse
     */
    public HashMap<String, Double> getWarehouseComposition(){
        HashMap<String, Double> categoryOccupation = null;
        int totalProductsInWarehouse = 0;
        try{
            Statement stm = conn.createStatement();
            ResultSet resultSet = stm.executeQuery(this.WAREHOUSE_COMPOSITION_QUERY);
            categoryOccupation = new HashMap<String, Double>();
            while (resultSet.next()) {
                String category = resultSet.getString("category");
                double prodNumber = resultSet.getInt("qty");
                totalProductsInWarehouse += prodNumber;
                categoryOccupation.put(category, prodNumber);
            }
            for (Map.Entry<String, Double> entry : categoryOccupation.entrySet()) {
                categoryOccupation.put(entry.getKey(), entry.getValue()/totalProductsInWarehouse);
            }
        }catch (SQLException ex){
            System.out.println(ex.toString());
        }
        return categoryOccupation;
    }


    @Override
    public Product constructEntityFromMap(HashMap<String, Object> map) {
        int barcode =(int) map.get("barcode");
        String name =(String) map.get("name");
        int qty =(int) map.get("qty");
        double price =(double) map.get("price");
        String supplier =(String) map.get("supplier");
        String category =(String) map.get("category");
        String image = (String) map.get("image");
        return new Product(barcode, name, qty, price, supplier, category, image);
    }
    
}
