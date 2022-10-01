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
public class Category implements Entity{
    
    private String name;
    private String img;
    //aggiunta del tipo di dato
    public String[] fields = {"name", "img"};
    
    public Category(String name, String img){
        this.name = name;
        this.img = img;
    }
    
    public String getName(){
        return this.name;
    }
    public String getImg(){ return this.img;}

    @Override
    public String getTableName() {
        return "category";
    }

    @Override
    public HashMap<String, Object> map() {
        HashMap<String, Object> res = new HashMap<String, Object>();
        res.put("name", this.name);
        res.put("img", this.img);
        return res;
    }

    
    
}
