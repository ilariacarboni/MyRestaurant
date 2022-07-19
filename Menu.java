/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.util.HashMap;

/**
 *
 * @author milar
 */
public class Menu implements Entity{
    
    private String nameDish;
    private int price;
    private String category;
   
    
    public Menu (String nameDish, int price, String category){    
	this.nameDish=nameDish;
    	this.price=price;
	this.category=category;
    }
    
    public String getNameDish(){
        return this.nameDish;
    }
    
    public int getPrice(){
        return this.price;
    }
    
    public String getCategory(){
        return this.category;
    }
    

    @Override
    public String getTableName() {
        return "menu";
    }
    
        @Override
    public HashMap<String, Object> map() {
        HashMap<String, Object> res = new HashMap<String, Object>();
        res.put("nameDish", this.nameDish);
        res.put("price", this.price);
        res.put("category", this.category);
        return res;
    }
    
}
