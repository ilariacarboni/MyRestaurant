package model.dao;

import model.database.dbConnection;
import model.entity.Admin;
import model.entity.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class AdminsTable implements Table<Admin>{

    Connection conn = dbConnection.establishConnection();

    @Override
    public ArrayList<Admin> getAll() {
        ArrayList <Admin> resList = new ArrayList<Admin>();
        String sql = "SELECT * FROM "+ Admin.TABLE_NAME;
        Statement stm = null;
        try {
            stm = conn.createStatement();
            ResultSet resultSet = stm.executeQuery(sql);

            while (resultSet.next()) {
                Admin a = new Admin(resultSet.getString("username"),resultSet.getString("password"),resultSet.getInt("level"));
                resList.add(a);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        } finally {
            try {
                stm.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return resList;
    }

    @Override
    public boolean save(Admin a) {
        boolean res = false;
        String sql= "INSERT INTO "+Admin.TABLE_NAME+" (username,password,level) VALUES (?,?,?)";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, a.getUsername());
            ps.setString(2, a.getPassword());
            ps.setInt(3, a.getLevel());
            ps.execute();
            res = true;
        } catch (SQLException ex) {
            ex.printStackTrace();
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
    public boolean update(Admin a) {
        boolean res = false;
        String sql= "UPDATE "+Admin.TABLE_NAME+" SET password=?, level=? WHERE username=?,";
        PreparedStatement ps = null;
        try {
            ps= conn.prepareStatement(sql);
            ps.setString(1, a.getPassword());
            ps.setInt(2, a.getLevel());
            ps.setString(3, a.getUsername());
            ps.execute();
            res = true;
        } catch (SQLException ex) {
            ex.printStackTrace();
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
    public boolean delete(Admin a) {
        boolean res = false;
        String sql= "DELETE FROM "+Admin.TABLE_NAME+" WHERE username=?";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, a.getUsername());
            res = true;
        } catch (SQLException ex) {
            ex.printStackTrace();
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
    public ArrayList<Admin> getFrom(Object searchParam, String paramName) {
        //ricerca per username
        ArrayList<Admin> resList = new ArrayList<Admin>();
        PreparedStatement ps = null;
        if(paramName == "username"){
            String sql = "SELECT * FROM "+Admin.TABLE_NAME+" WHERE username = ?";
            try {
                ps = conn.prepareStatement(sql);
                ps.setString(1, (String)searchParam);
                ResultSet resultSet = ps.executeQuery();

                while(resultSet.next()){
                    Admin a = new Admin( resultSet.getString("username"),resultSet.getString("password"), resultSet.getInt("level"));
                    resList.add(a);
                }
            } catch (Exception e) {
                System.out.println(e.toString());
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

    @Override
    public Admin constructEntityFromMap(HashMap<String, Object> map) {
        String username = (String) map.get("username");
        String password = (String) map.get("password");
        int level = (int) map.get("level");
        return new Admin(username,password,level);
    }
}
