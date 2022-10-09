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
    
public Course(String name,String img){
        this.name = name;
        this.img = img;
    }
    
    public String getName(){
        return this.name;
    }
    public String getImg(){ 
        return this.img;
    }
    
    public String getTableName() {
        return "course";
    }

    public HashMap<String, Object> map() {
        HashMap<String, Object> res = new HashMap<String, Object>();
        res.put("name", this.name);
        res.put("img", this.img);
        return res;
    }
    
}
