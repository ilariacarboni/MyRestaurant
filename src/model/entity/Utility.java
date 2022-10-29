package model.entity;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;

public class Utility implements Entity{
    private final String TABLE_NAME = "utility";
    private int numberId;
    private double total;
    private String type;
    private String date;
    private String state;
    
    public Utility (int numberId, double total, String type, String date,String state ){    
	this.numberId=numberId;
    	this.total=total;
    	this.type=type;
        this.date=date;
        this.state=state;
    }
    
    public int getNumberId(){
        return this.numberId;
    }
    
    public double getTotal(){
        return this.total;
    }
    
    public String getType(){
        return this.type;
    }
    
    public String getDate(){
	return this.date;
    }
    
    public String getState(){
	return this.state;
    }

    @Override
    public String getTableName() {
        return this.TABLE_NAME;
    }
    
        @Override
    public HashMap<String, Object> map() {
        HashMap<String, Object> res = new HashMap<String, Object>();
        res.put("numberId", this.numberId);
        res.put("total", this.total);
        res.put("type", this.type);
        res.put("date", this.date);
        res.put("state", this.state);
        return res;
    }
    
}
