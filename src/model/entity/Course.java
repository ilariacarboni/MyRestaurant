/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.entity;

import java.util.HashMap;

/**
 *
 * @author milar
 */
public class Course {
    private String name;
    private String img;
    private String dish_icon;
    
public Course(String name,String img,String dish_icon){
        this.name = name;
        this.img = img;
        this.dish_icon = dish_icon;
    }
    
    public String getName(){
        return this.name;
    }
    public String getImg(){ 
        return this.img;
    }
    
    public String getIcon(){
        return this.dish_icon;
    }
    public String getTableName() {
        return "course";
    }

    public HashMap<String, Object> map() {
        HashMap<String, Object> res = new HashMap<String, Object>();
        res.put("name", this.name);
        res.put("img", this.img);
        res.put("dish-icon", this.dish_icon);
        return res;
    }
    
}
