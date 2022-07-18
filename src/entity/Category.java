/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.HashMap;

/**
 *
 * @author Natalia
 */
public class Category implements Entity{
    
    private String name;
    
    public Category(String name){
        this.name = name;
    }
    
    public String getName(){
        return this.name;
    }

    @Override
    public String getTableName() {
        return "category";
    }

    @Override
    public HashMap<String, Object> map() {
        HashMap<String, Object> res = new HashMap<String, Object>();
        res.put("name", this.name);
        return res;
    }

    
    
}
