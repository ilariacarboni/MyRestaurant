/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.entity;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;

public class Utility implements Entity{
    
    private int numberId;
    private int total;
    private String type;
    private LocalDate date;
    
    public Utility (int numberId, int total, String type, LocalDate date){    
	this.numberId=numberId;
    	this.total=total;
    	this.type=type;
        this.date=date;
    }
    
    public int getNumberId(){
        return this.numberId;
    }
    
    public int getTotal(){
        return this.total;
    }
    
    public String getType(){
        return this.type;
    }
    
    public Date getDate(){
	Date sql_date = Date.valueOf(this.date);
	return sql_date;
    }

    @Override
    public String getTableName() {
        return "utility";
    }
    
        @Override
    public HashMap<String, Object> map() {
        HashMap<String, Object> res = new HashMap<String, Object>();
        res.put("numberId", this.numberId);
        res.put("total", this.total);
        res.put("type", this.type);
        res.put("date", this.date);
        return res;
    }
    
}

