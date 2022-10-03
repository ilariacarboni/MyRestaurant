package model.dao;

import model.database.dbConnection;
import model.entity.Category;

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
public class CategoryTable extends DataObject implements Table<Category>{

    Connection conn = dbConnection.establishConnection();
    protected String tableName = "category";
    
    @Override
    public ArrayList<Category> getAll() {
        ArrayList <Category> resList = new ArrayList<Category>();
        String sql = "SELECT * FROM category";
        try {
            Statement stm = conn.createStatement();
            ResultSet resultSet = stm.executeQuery(sql);

            while (resultSet.next()) {
                Category c = new Category( resultSet.getString("name"),resultSet.getString("img"));
                resList.add(c);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
        return resList;
    }

    @Override
    public boolean save(Category c){
        boolean res = false;
        String sql= "INSERT INTO category (name,img) VALUES (?,?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, c.getName());
            ps.setString(2, c.getImg());
            ps.execute();
            res = true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return res;
    }

    @Override
    public boolean update(Category c) {
        boolean res = false;
        String sql= "UPDATE category SET name = ?,img=? WHERE name=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, c.getName());
            ps.setString(2, c.getImg());
            ps.execute();
            res = true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return res;
    }

    @Override
    public boolean delete(Category c) {
        boolean res = false;
        String sql= "DELETE FROM category WHERE name = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, c.getName());
            res = true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return res;
    }

    @Override
    public ArrayList<Category> getFrom(Object searchParam, String paramName) { 
        //ricerca per nome
        ArrayList<Category> resList = new ArrayList<Category>();
        if(searchParam instanceof String){
            String sql = "SELECT * FROM category WHERE id = ?";
            try {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, (String)searchParam);
                ResultSet resultSet = ps.executeQuery();
                
                while(resultSet.next()){
                    Category c = new Category(resultSet.getString("name"),resultSet.getString("img"));
                    resList.add(c);
                }
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }
        return resList;
    }

    @Override
    public Category constructEntityFromMap(HashMap<String, Object> map) {
        String name = (String) map.get("name");
        String img = (String) map.get("img");
        return new Category(name,img);
    }

    @Override
    protected String[] getQueryFrom() {
        return new String[0];
    }

    @Override
    protected String[] getQueryWhere() {
        return new String[0];
    }

    @Override
    protected String[] getQueryOrderBy() {
        return new String[0];
    }
}
