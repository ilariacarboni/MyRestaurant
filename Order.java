/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;

/**
 *
 * @author Natalia
 */
public class Order implements Entity{
    
    private int number;
    private LocalDate date;
    private int product_barcode;
    private int qty;
    private int state;

    public Order(int number, LocalDate date, int prodBarcode, int qty, int state){
        this.number=number;
        this.date=date;
        this.product_barcode=prodBarcode;
        this.qty=qty;
        this.state=state;
    }
    
    public int getNumber(){
        return this.number;
    }
    
    public Date getDate(){
        return Date.valueOf(this.date);
    }
    
    public int getProduct(){
        return this.product_barcode;
    }
    
    public int getQty(){
        return this.qty;
    }
    
    public int getState(){
        return this.state;
    }
    
    
    @Override
    public String getTableName() {
        return "order";
    }
    
    @Override
    public HashMap<String, Object> map() {
        HashMap<String, Object> res = new HashMap<String, Object>();
        res.put("number", this.number);
        res.put("date", this.date);
        res.put("productBarcode", this.product_barcode);
        res.put("qty", this.qty);
        res.put("state", this.state);
        return res;
    }
    
}
