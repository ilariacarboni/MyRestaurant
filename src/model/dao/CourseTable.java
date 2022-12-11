/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import model.database.dbConnection;
import model.entity.Course;


/**
 *
 * @author milar
 */
public class CourseTable implements Table<Course> {
    Connection conn = dbConnection.establishConnection();
    
public ArrayList<Course> getAll() {
        ArrayList <Course> resList = new ArrayList<Course>();
        String sql = "SELECT * FROM course ORDER BY name";
        Statement stm = null;
        try {
            stm = conn.createStatement();
            ResultSet resultSet = stm.executeQuery(sql);

            while (resultSet.next()) {
                Course c = new Course( resultSet.getString("name"),resultSet.getString("img"),resultSet.getString("dish-icon"),resultSet.getString("title-image"));
                resList.add(c);
            }
        } catch (SQLException ex) {
            resList = null;
        }finally {
            try {
                stm.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return resList;
    }

    public boolean save(Course c){
        boolean res = false;
        String sql= "INSERT INTO course (name) VALUES (?)"; 
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, c.getName());
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

    public boolean update(Course c) {
        boolean res = false;
        String sql= "UPDATE course SET name = ?  WHERE name = ?";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, c.getName());
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

    public boolean delete(Course c) {
        boolean res = false;
        String sql= "DELETE FROM course WHERE name = ?";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, c.getName());
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

    public ArrayList<Course> getFrom(Object searchParam, String paramName) { 
        //ricerca per nome
        ArrayList<Course> resList = new ArrayList<Course>();
        PreparedStatement ps = null;
        if(searchParam instanceof String){
            String sql = "SELECT * FROM course WHERE name = ?";
            try {
                ps = conn.prepareStatement(sql);
                ps.setString(1, (String)searchParam);
                ResultSet resultSet = ps.executeQuery();
                
                while(resultSet.next()){
                    Course c = new Course(resultSet.getString("name"),resultSet.getString("img"),resultSet.getString("dish-icon"),resultSet.getString("title-image"));
                    resList.add(c);
                }
            } catch (Exception e) {
                resList = null;
            } finally {
            try {
                ps.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        }
        return resList;
    }

    public Course constructEntityFromMap(HashMap<String, Object> map) {
        String name = (String) map.get("name");
        String img = (String) map.get("img");
        String dish_icon = (String) map.get("dish-icon");
        String title_image = (String) map.get("title-image");
        return new Course(name,img,dish_icon,title_image);
    }
    
}
