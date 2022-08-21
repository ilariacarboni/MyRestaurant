/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.entity;

import java.util.HashMap;

/**
 *
 * @author Natalia
 */
public class Product implements Entity{
    
    private int barcode;
    private String name;
    private int qty;
    private double price;
    private String supplier;
    private String category;
    private String img;
    
    public Product(int barcode, String name, int qty, double price, String supplier, String category, String img){
        this.barcode=barcode;
        this.name=name;
        this.qty=qty;
        this.price=price;
        this.supplier = supplier;
        this.category=category;
        this.img = img;
    }
    
    public int getId(){
        return this.barcode;
    }
    
    public String getName(){
        return this.name;
    }
    
    public int getQty(){
        return this.qty;
    }
    
    public double getPrice(){
        return this.price;
    }
    
    public String getSupplier(){
        return this.supplier;
    }
    
    public String getCategory(){
        return this.category;
    }
    public String getImage(){ return this.img; }

    @Override
    public String getTableName() {
        return "product";
    }

    @Override
    public HashMap<String, Object> map() {
        HashMap<String, Object> res = new HashMap<String, Object>();
        res.put("barcode", this.barcode);
        res.put("name", this.name);
        res.put("qty", this.qty);
        res.put("price", this.price);
        res.put("supplier", this.supplier);
        res.put("category", this.category);
        res.put("image", this.img);
        return res;
    }
    
}
