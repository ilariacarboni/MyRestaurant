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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author Natalia
 */
public class OrderTable implements Table<Order>{

    Connection conn = dbConnection.establishConnection();
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
            "where o.date between datetime('now', '-1 year') and datetime('now')\n" +
            "group by strftime('%m-%Y', o.date)\n" +
            "order by strftime('%Y', o.date) asc , strftime('%m', o.date) asc;";
    private int pageLength = 0;

    public void setPageLength(int l){
        this.pageLength = l;
    }

    @Override
    public ArrayList<Order> getAll() {
        ArrayList <Order> resList = new ArrayList<Order>();
        String sql = "SELECT * FROM orders";
        try {
            Statement stm = conn.createStatement();
            ResultSet resultSet = stm.executeQuery(sql);

            while (resultSet.next()) {
                Order o = new Order( resultSet.getInt("number"), resultSet.getString("date"), resultSet.getInt("product_barcode"), resultSet.getInt("qty"), resultSet.getString("state"));
                resList.add(o);
            }
        } catch (SQLException ex) {
            resList = null;
        }
        return resList;
    }

    public ArrayList<Order> getAllByStatus(String status) {
        ArrayList <Order> resList = new ArrayList<Order>();
        String sql = "SELECT * FROM orders WHERE state = ?";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, status);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                Order o = new Order( resultSet.getInt("number"), resultSet.getString("date"), resultSet.getInt("product_barcode"), resultSet.getInt("qty"), resultSet.getString("state"));
                resList.add(o);
            }
        } catch (SQLException ex) {
            resList = null;
        } finally {
            try {
                ps.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return resList;
    }

    public ArrayList<Order> getPageWithStatus(String status, int page, HashMap<String, String> filters){
        ArrayList <Order> resList = new ArrayList<Order>();
        int offset =  (page-1) * this.pageLength;
        String selectOrders = "SELECT o.*, p.supplier FROM orders o JOIN product p ON o.product_barcode = p.barcode ";
        String orderBy = " ORDER BY o.date desc";
        String where = " WHERE  state = ? ";
        String limit = " LIMIT " + offset + "," + this.pageLength;
        String sql = "SELECT * FROM (" + selectOrders + where + orderBy + limit + ")";

        boolean filterEmpty = true;
        String ordersPageWheres = " WHERE ";
        for (Map.Entry<String, String> entry : filters.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if(!value.isEmpty()){
                filterEmpty = false;
                ordersPageWheres += key+" LIKE '%"+value+"%' AND ";
            }
        }
        //eliminazione ultimo AND
        if(!filterEmpty){
            ordersPageWheres = ordersPageWheres.substring(0, ordersPageWheres.length() - 4);
            sql += ordersPageWheres;
        }

        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, status);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                Order o = new Order(
                        resultSet.getInt("number"),
                        resultSet.getString("date"),
                        resultSet.getInt("product_barcode"),
                        resultSet.getInt("qty"),
                        resultSet.getString("state"),
                        resultSet.getString("supplier")
                );
                resList.add(o);
            }
        } catch (SQLException ex) {
            resList = null;
        }finally {
            try {
                ps.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return resList;
    }

    @Override
    public boolean save(Order o) {
        boolean res = false;
        String sql= "INSERT INTO orders (date, product_barcode, qty, state) VALUES (?,?,?,?)";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, o.getDate());
            ps.setInt(2, o.getProduct());
            ps.setInt(3, o.getQty());
            ps.setString(4, o.getState());

            ps.execute();
            res = true;
        } catch (SQLException ex) {
        } finally {
            try {
                ps.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return res;
    }

    @Override
    public boolean update(Order o){
        boolean res = false;
        String sql= "UPDATE orders SET date=?, product_barcode=?, qty=?, state=? WHERE number=?";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, o.getDate());
            ps.setInt(2, o.getProduct());
            ps.setDouble(3, o.getQty());
            ps.setString(4, o.getState());
            ps.setInt(5, o.getNumber());
            ps.execute();
            res = true;
        } catch (SQLException ex) {
        } finally {
            try {
                ps.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return res;
    }

    @Override
    public boolean delete(Order o){
        boolean res = false;
        String sql= "DELETE FROM orders WHERE number = ?";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, o.getNumber());
            res = true;
        } catch (SQLException ex) {
        }finally {
            try {
                ps.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return res;
    }

    @Override
    public ArrayList<Order> getFrom(Object searchParam, String paramName) {
        //ricerca per numero
        ArrayList<Order> resList = new ArrayList<Order>();
        PreparedStatement ps = null;
        if(searchParam instanceof Integer && paramName.equals("number")){
            String sql = "SELECT * FROM orders WHERE number =?";
            try {
                ps = conn.prepareStatement(sql);
                ps.setInt(1, (int) searchParam);
                ResultSet resultSet = ps.executeQuery();
                
                while (resultSet.next()) {
                    Order o = new Order(resultSet.getInt("number"),resultSet.getString("date"),resultSet.getInt("product_barcode"), resultSet.getInt("qty"), resultSet.getString("state"));
                    resList.add(o);
                }
            } catch (SQLException ex) {
                resList = null;
            }finally {
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        
        return resList;
    }

    public HashMap<String, String> getLastOrderPerCategory(){
        HashMap<String, String> res = null;
        Statement stm = null;
        try {
            stm = conn.createStatement();
            ResultSet resultSet = stm.executeQuery(this.LAST_ORDER_PER_CATEGORY);
            res = new HashMap<String, String>();
            while (resultSet.next()) {
                String category = resultSet.getString("category");
                String lastOrderDate = resultSet.getString("last_order");
                res.put(category, lastOrderDate);
            }
        } catch (SQLException ex) {
            res = null;
        }finally {
            try {
                stm.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return res;
    }

    public HashMap<String, Double> averageMonthlyExpensePerCategory() {
        HashMap<String, Double> res = null;
        Statement stm = null;
        try {
            stm = conn.createStatement();
            ResultSet resultSet = stm.executeQuery(this.AVERAGE_MONTHLY_EXPENSE_PER_CATEGORY);
            res = new HashMap<String, Double>();
            while (resultSet.next()) {
                String category = resultSet.getString("category");
                Double averageExpense = resultSet.getDouble("average_expense");
                res.put(category, averageExpense);
            }
        } catch (SQLException ex) {
            res = null;
        } finally {
            try {
                stm.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return res;
    }

    public LinkedHashMap<String, Double> getMonthlyExpense() {
        LinkedHashMap<String, Double> res = null;
        Statement stm = null;
        try{
            stm = conn.createStatement();
            ResultSet resultSet = stm.executeQuery(this.MONTHLY_ORDERS_EXPENSE_QUERY);
            res = new LinkedHashMap<String, Double>();
            while (resultSet.next()) {
                String month = resultSet.getString("date");
                Double expense = resultSet.getDouble("expense");
                res.put(month, expense);
            }
        }catch (SQLException ex){
            res = null;
        } finally {
            try {
                stm.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return res;
    }
    public int getTotal() {
        int res = 0;
        String sql= "SELECT count(*) as tot FROM orders";
        Statement stm = null;
        try {
            stm = conn.createStatement();
            ResultSet resultSet = stm.executeQuery(sql);
            res = resultSet.getInt("tot");
        } catch (SQLException ex) {
        } finally {
            try {
                stm.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return res;
    }

    public int getTotal(String state){
        int res = 0;
        String sql= "SELECT count(*) as tot FROM orders WHERE state = ?";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, state);
            ResultSet resultSet = ps.executeQuery();
            res = resultSet.getInt("tot");
        } catch (SQLException ex) {
        } finally {
            try {
                ps.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return res;
    }

    @Override
    public Order constructEntityFromMap(HashMap<String, Object> map) {
        Order res = null;
        int number = (int) map.get("number");
        String date =  map.get("date").toString();
        int productBarcode = (int) map.get("productBarcode");
        int qty = (int) map.get("qty");
        String state = map.get("state").toString();
        if(map.containsKey("supplier")){
            String supplier = map.get("supplier").toString();
            res = new Order(number, date, productBarcode, qty, state, supplier);
        }else{
            res = new Order(number, date, productBarcode, qty, state);
        }
        return res;
    }
    
}
