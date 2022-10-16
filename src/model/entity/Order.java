 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.entity;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;

/**
 *
 * @author Natalia
 */
public class Order implements Entity{
    private final String TABLE_NAME = "order";
    public static final String CREATED_STATE   = "created";
    public static final String DELIVERED_STATE = "delivered";
    public static final String DELETED_STATE   = "deleted";
    /**
     * the order number is not mandatory, it's null when the order is created by the operator
     * in the DB this field is auto-incremental, so it will have a value when the order is retrieved from the DB
     */
    private int number;
    private String date;
    private int product_barcode;
    private int qty;
    private String state;
    private String supplier = null;

    public Order(int number, String date, int prodBarcode, int qty, String state){
        this.number=number;
        this.date=date;
        this.product_barcode=prodBarcode;
        this.qty=qty;
        this.state=state;
    }

    public Order(String date, int prodBarcode, int qty, String state){
        this.date=date;
        this.product_barcode=prodBarcode;
        this.qty=qty;
        this.state=state;
    }

    public Order(int number, String date, int prodBarcode, int qty, String state, String supplier){
        this.number=number;
        this.date=date;
        this.product_barcode=prodBarcode;
        this.qty=qty;
        this.state=state;
        this.supplier = supplier;
    }
    
    public int getNumber(){
        return this.number;
    }
    
    public String getDate(){
        return this.date;
    }
    
    public int getProduct(){
        return this.product_barcode;
    }
    
    public int getQty(){
        return this.qty;
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
        res.put("number", this.number);
        res.put("date", this.date);
        res.put("productBarcode", this.product_barcode);
        res.put("qty", this.qty);
        res.put("state", this.state);
        if(this.supplier != null){
            res.put("supplier", this.supplier);
        }
        return res;
    }
    
}
