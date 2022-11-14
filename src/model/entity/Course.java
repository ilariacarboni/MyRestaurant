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
public class Course implements Entity {
    private final String TABLE_NAME = "course";
    private String name;
    private String img;
    private String dish_icon;
    private String title_image;
    
public Course(String name,String img,String dish_icon,String title_image){
        this.name = name;
        this.img = img;
        this.dish_icon = dish_icon;
        this.title_image = title_image;
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
    
    public String getTitleImage(){
        return this.title_image;
    }
    @Override
    public String getTableName() {
        return this.TABLE_NAME;
    }

    @Override
    public HashMap<String, Object> map() {
        HashMap<String, Object> res = new HashMap<String, Object>();
        res.put("name", this.name);
        res.put("img", this.img);
        res.put("dish-icon", this.dish_icon);
        res.put("title-image", this.title_image);
        return res;
    }
    
}
