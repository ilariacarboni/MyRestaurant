/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import model.entity.Order;
import model.database.dbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 *
 * @author Natalia
 */
public class OrderTable implements Table<Order>{
    
    Connection conn = dbConnection.enstablishConnection();
    private final String LAST_ORDER_PER_CATEGORY = "select p.category, max(o.date) as last_order from product p left join orders o on p.barcode = o.product_barcode group by p.category;";
    private final String AVERAGE_MONTHLY_EXPENSE_PER_CATEGORY =
            "select avg(costo_tot) as average_expense, category from(\n" +
            "    select p.category as category,  strftime('%m', o.date) as month, sum(o.qty*p.price) as costo_tot\n" +
            "    from product p join orders o on p.barcode = o.product_barcode\n" +
            "    group by p.category, month\n" +
            ") group by category;";
    private final String MONTHLY_ORDERS_EXPENSE_QUERY = "select  strftime('%m-%Y', o.date) as date,\n" +
            "sum(p.price*o.qty) as expense\n" +
            "from orders o join product p on o.product_barcode = p.barcode\n" +
            "group by strftime('%m-%Y', o.date)\n" +
            "order by strftime('%Y', o.date) asc , strftime('%m', o.date) asc;";

    @Override
    public ArrayList<Order> getAll() {
        ArrayList <Order> resList = new ArrayList<Order>();
        String sql = "SELECT * FROM orders";
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
        String sql= "INSERT INTO orders (number, date, product_barcode, qty, state) VALUES (?,?,?,?,?)";
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
        String sql= "UPDATE orders SET date=?, product_barcode=?, qty=?, state=? WHERE number=?";
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
        String sql= "DELETE FROM orders WHERE number = ?";
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
            String sql = "SELECT * FROM orders WHERE number =?";
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

    public HashMap<String, String> getLastOrderPerCategory(){
        HashMap<String, String> res = null;
        try {
            Statement stm = conn.createStatement();
            ResultSet resultSet = stm.executeQuery(this.LAST_ORDER_PER_CATEGORY);
            res = new HashMap<String, String>();
            while (resultSet.next()) {
                String category = resultSet.getString("category");
                String lastOrderDate = resultSet.getString("last_order");
                res.put(category, lastOrderDate);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
        return res;
    }

    public HashMap<String, Double> averageMonthlyExpensePerCategory(){
        HashMap<String, Double> res = null;
        try {
            Statement stm = conn.createStatement();
            ResultSet resultSet = stm.executeQuery(this.AVERAGE_MONTHLY_EXPENSE_PER_CATEGORY);
            res = new HashMap<String, Double>();
            while (resultSet.next()) {
                String category = resultSet.getString("category");
                Double averageExpense = resultSet.getDouble("average_expense");
                res.put(category, averageExpense);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
        return res;
    }

    public LinkedHashMap<String, Double> getMonthlyExpense(){
        LinkedHashMap<String, Double> res = null;
        try{
            Statement stm = conn.createStatement();
            ResultSet resultSet = stm.executeQuery(this.MONTHLY_ORDERS_EXPENSE_QUERY);
            res = new LinkedHashMap<String, Double>();
            while (resultSet.next()) {
                String month = resultSet.getString("date");
                Double expense = resultSet.getDouble("expense");
                res.put(month, expense);
            }
        }catch (SQLException ex){
            System.out.println(ex.toString());
        }
        return res;
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
