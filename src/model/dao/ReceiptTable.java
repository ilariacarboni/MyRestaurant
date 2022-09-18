/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import model.entity.Receipt;
import model.database.dbConnection;
import java.sql.Connection;
import java.sql.Date;
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
public class ReceiptTable implements Table<Receipt>{

    private final String REVENUE_PER_MONTH_QUERY = "select  strftime('%m-%Y', r.date) as date,\n" +
            "sum(r.total) as sum_for_month\n" +
            "from receipt r\n" +
            "where r.date between datetime('now', '-1 year') and datetime('now')\n" +
            "group by strftime('%m-%Y', r.date)\n" +
            "order by strftime('%Y', r.date) asc , strftime('%m', r.date) asc;";
    private final String MORE_ORDERED_DISHES_QUERY = "select * from \n" +
            "( SELECT ROW_NUMBER() \n" +
            "OVER (PARTITION BY strftime('%m-%Y', r.date) ORDER BY ri.qty desc) AS r,\n" +
            "ri.qty, ri.dish, strftime('%m-%Y', r.date) as date\n" +
            "FROM receipt r join receipt_item ri on ri.receipt = r.number order by strftime('%Y', r.date) asc, strftime('%m', r.date) asc) \n" +
            "x where x.r < 6;";
    Connection conn = dbConnection.enstablishConnection();

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
        return resList;
    }

    @Override
    public boolean save(Receipt r) {
        boolean res = false;
            
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

    public LinkedHashMap<String, Double> getRevenuePerMonth(){
        LinkedHashMap<String, Double> res = null;
        try{
            Statement stm = conn.createStatement();
            ResultSet resultSet = stm.executeQuery(this.REVENUE_PER_MONTH_QUERY);
            res = new LinkedHashMap<String, Double>();
            while (resultSet.next()) {
                String month = resultSet.getString("date");
                Double revenue = resultSet.getDouble("sum_for_month");
                res.put(month, revenue);
            }
        }catch (SQLException ex){
            System.out.println(ex.toString());
        }
        return res;
    }

    public LinkedHashMap<String, LinkedHashMap<String, Integer>> getMoreOrderedDishes(){
        LinkedHashMap<String, LinkedHashMap<String, Integer>> res = null;
        try{
            Statement stm = conn.createStatement();
            ResultSet resultSet = stm.executeQuery(this.MORE_ORDERED_DISHES_QUERY);
            res = new LinkedHashMap<String, LinkedHashMap<String, Integer>>();
            while (resultSet.next()) {
                String key = resultSet.getString("date");
                int qty = resultSet.getInt("qty");
                String dName = resultSet.getString("dish");

                if(res.get(key) == null){
                    LinkedHashMap<String, Integer> value = new LinkedHashMap<>();
                    value.put(dName, qty);
                    res.put(key, value);
                }else{
                    res.get(key).put(dName, qty);
                }
            }
        }catch (SQLException ex){
            System.out.println(ex.toString());
        }
        return res;
    }

    @Override
    public Receipt constructEntityFromMap(HashMap<String, Object> map) {
        int number = (int) map.get("number");
        LocalDate date = (LocalDate) map.get("date");
        double total = (double) map.get("total");
        return new Receipt(number, date, total);
    }
    
}
