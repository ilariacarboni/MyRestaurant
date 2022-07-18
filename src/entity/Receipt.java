/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.sql.Date;
import java.time.LocalDate;

/**
 *
 * @author Natalia
 */
public class Receipt implements Entity{
    
    private int number;
    private LocalDate date;
    private double total;

    public Receipt(int number, LocalDate date, double total ){
        this.number=number;
        this.date=date;
        this.total=total;
    }
    
    public int getNumber(){
        return this.number;
    }
    
    public Date getDate(){
        return  Date.valueOf(this.date);
    }
    
    public double getTotal(){
        return this.total;
    }
    
    @Override
    public String getTableName() {
        return "receipt";
    }
    
}
