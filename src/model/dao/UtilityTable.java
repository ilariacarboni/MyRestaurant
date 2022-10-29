package model.dao;

import model.database.dbConnection;
import model.entity.Utility;
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


public class UtilityTable implements Table<Utility>{
    
    Connection conn = dbConnection.establishConnection();
    ArrayList<Utility> utilitiesList = new ArrayList<Utility>();
    private final String UTILITY_COST_PER_MONTH_QUERY = "select  strftime('%m-%Y', u.date) as date,\n" +
            "sum(u.total) as sum_for_month\n" +
            "from utility u\n" +
            "where u.date between datetime('now', '-1 year') and datetime('now')\n" +
            "group by strftime('%m-%Y', u.date)\n" +
            "order by strftime('%Y', u.date) asc , strftime('%m', u.date) asc;";
    private int pageLength = 0;
    
    
    @Override
    public ArrayList<Utility> getAll() {
        
            ArrayList<Utility> resList = new ArrayList<Utility>();
            String sql = "SELECT * FROM utility";
            try {
                Statement stm = conn.createStatement();
                ResultSet resultSet = stm.executeQuery(sql);

                while (resultSet.next()) {
                    Utility u= new Utility(resultSet.getInt("numberId"),resultSet.getDouble("total"), resultSet.getString("type"),
                            resultSet.getString("date"), resultSet.getString("state"));
                    resList.add(u);
                }
            } catch (SQLException ex) {
                System.out.println(ex.toString());
            }
            return resList;
    }

    @Override
    public boolean save(Utility u) {
        //lo inserisce nella lista e nel db
        //se il dipendente è nella lista significa che è stato già inserito nel db
        
        boolean res = false;
            
            String sql= "INSERT INTO Utility (numberId, total, type, date, state) VALUES (?,?,?,?,?)";
            PreparedStatement ps = null;
            try {
                ps = conn.prepareStatement(sql);
                ps.setInt(1, u.getNumberId());
                ps.setDouble(2, (double)u.getTotal());
                ps.setString(3, u.getType());
                ps.setString(4, u.getDate());
                ps.setString(3, u.getState());
                ps.execute();
                
                res = true;
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            finally {
                
            try {
                ps.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
         return res;
    }

    @Override
    public boolean update (Utility u) {
        //t deve essere un istanza di Product con lo stesso identificativo 
        //dell'istanza che si vuole modificare
        boolean res = false;
            
            String sql= "UPDATE Utility  SET numberId = ?, total = ? , type =? , date = ? , state = ? WHERE numberId = ?";
            PreparedStatement ps = null;
            try {
                ps = conn.prepareStatement(sql);
                ps.setInt(1, u.getNumberId());
                ps.setDouble(2, (double)u.getTotal());
                ps.setString(3, u.getType());
                ps.setString(4, u.getDate());
                ps.setString(5, u.getState());
                ps.execute();
                
                res = true;
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            finally {
            try {
                ps.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
         return res;
    }
  

    @Override
    public boolean delete(Utility u) {
        
        boolean res = false;
            
            String sql= "DELETE FROM Utility WHERE numberId = ?";
            PreparedStatement ps = null;
            try {
                ps = conn.prepareStatement(sql);
                ps.setInt(1, u.getNumberId());
                //ps.execute();
                
                res = true;

            } catch (SQLException ex) { 
                ex.printStackTrace();
            }finally {
            try {
                ps.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        
        this.utilitiesList.remove(u);
        return res;
    }
    
    
    public ArrayList<Utility> getFrom (Object searchParam, String paramName) { 
        
        ArrayList<Utility> resList = new ArrayList<Utility>();
        PreparedStatement ps = null;
        if(searchParam instanceof String){   //passso un oggetto di ricerca textinput
         
        String sql= "SELECT * FROM Utility WHERE type =? ORDER BY date";
        try {
                ps = conn.prepareStatement(sql); 
                ps.setString(1, (String) searchParam);
                ps.execute();
                ResultSet resultSet = ps.executeQuery();
                
                while (resultSet.next()) {
                    Utility u = new Utility(resultSet.getInt("numberId"), resultSet.getDouble("total"),resultSet.getString("type"),
                            resultSet.getString("date"),resultSet.getString("state"));
                    resList.add(u);
                }
            }
            catch (SQLException ex) {
        
                ex.printStackTrace();
            }   
        } 
        
        else if (searchParam instanceof Integer) {
            
        String sql=  "SELECT * FROM Utility WHERE numberId=? ORDER BY date"; 
       
        
            try {
                ps = conn.prepareStatement(sql); 
                ps.setInt(1, (int) searchParam);
                ps.execute();
                ResultSet resultSet = ps.executeQuery();
                
                while (resultSet.next()) {
                    Utility u = new Utility(resultSet.getInt("numberId"), resultSet.getDouble("total"),resultSet.getString("type"),
                             resultSet.getString("date"),resultSet.getString("state"));
                    resList.add(u);
                }
            }
            catch (SQLException ex) {
                ex.printStackTrace();
            }  
        }     
         return resList;
    }

    public LinkedHashMap<String, Double> getUtilityCostPerMonth(){
        LinkedHashMap<String, Double> res = null;
        Statement stm = null;
        try{
            stm = conn.createStatement();
            ResultSet resultSet = stm.executeQuery(this.UTILITY_COST_PER_MONTH_QUERY);
            res = new LinkedHashMap<String, Double>();
            while (resultSet.next()) {
                String month = resultSet.getString("date");
                Double cost = resultSet.getDouble("sum_for_month");
                res.put(month, cost);
            }
        }catch (SQLException ex){
            System.out.println(ex.toString());
        }
        finally {
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
        String sql= "SELECT count(*) as total FROM utility";
        Statement stm = null;
        try {
            stm = conn.createStatement();
            ResultSet resultSet = stm.executeQuery(sql);
            res = resultSet.getInt("total");
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                stm.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return res;
    }
    
    public void setPageLength(int l){
        this.pageLength = l;
    }
    
    @Override
    public Utility constructEntityFromMap(HashMap<String, Object> map) {
        int numberId =(int) map.get("numberId");
        double total =(double) map.get("total");
        String type =(String) map.get("type");
        String date =(String) map.get("date");
        String state =(String) map.get("state");
        
        return new Utility(numberId, total, type, date,state);
    }
    
}
