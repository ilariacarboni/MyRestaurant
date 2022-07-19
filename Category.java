/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author milar
 */
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
