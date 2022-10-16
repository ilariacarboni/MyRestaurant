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
    public static final String TABLE_NAME = "category";
    private String name;
    private String img;
    private String icon;
    private String nameImg;
    
    public Category(String name, String img, String icon, String nameImg){
        this.name = name;
        this.img = img;
        this.icon = icon;
        this.nameImg = nameImg;
    }
    
    public String getName(){
        return this.name;
    }
    public String getImg(){ return this.img;}
    public String getIcon(){
        return this.icon;
    }
    public String getNameImg(){ return this.nameImg;}

    @Override
    public String getTableName() {
        return this.TABLE_NAME;
    }

    @Override
    public HashMap<String, Object> map() {
        HashMap<String, Object> res = new HashMap<String, Object>();
        res.put("name", this.name);
        res.put("img", this.img);
        res.put("icon", this.icon);
        res.put("nameImg", this.nameImg);
        return res;
    }

    
    
}
