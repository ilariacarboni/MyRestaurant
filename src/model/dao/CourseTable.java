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
public class CourseTable {
    Connection conn = dbConnection.enstablishConnection();
    
public ArrayList<Course> getAll() {
        ArrayList <Course> resList = new ArrayList<Course>();
        String sql = "SELECT * FROM course";
        try {
            Statement stm = conn.createStatement();
            ResultSet resultSet = stm.executeQuery(sql);

            while (resultSet.next()) {
                Course c = new Course( resultSet.getString("name"),resultSet.getString("img"));
                resList.add(c);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
        return resList;
    }

    public boolean save(Course c){
        boolean res = false;
        String sql= "INSERT INTO course (name) VALUES (?)"; //manca img
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

    public boolean update(Course c) {
        boolean res = false;
        String sql= "UPDATE course SET name = ?  WHERE name=?";
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

    public boolean delete(Course c) {
        boolean res = false;
        String sql= "DELETE FROM course WHERE name = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, c.getName());
            res = true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return res;
    }

    public ArrayList<Course> getFrom(Object searchParam, String paramName) { 
        //ricerca per nome
        ArrayList<Course> resList = new ArrayList<Course>();
        if(searchParam instanceof String){
            String sql = "SELECT * FROM course WHERE id = ?";
            try {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, (String)searchParam);
                ResultSet resultSet = ps.executeQuery();
                
                while(resultSet.next()){
                    Course c = new Course(resultSet.getString("name"),resultSet.getString("img"));
                    resList.add(c);
                }
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }
        return resList;
    }

    public Course constructEntityFromMap(HashMap<String, Object> map) {
        String name = (String) map.get("name");
        String img = (String) map.get("img");
        return new Course(name,img);
    }
    
}
