/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author Natalia
 */
public class Product implements Entity{
    
    private int barcode;
    private String name;
    private int qty;
    private double price;
    private String category;
    
    public Product(int barcode, String name, int qty, double price){
        this.barcode=barcode;
        this.name=name;
        this.qty=qty;
        this.price=price;
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

    @Override
    public String getTableName() {
        return "product";
    }
    
}
