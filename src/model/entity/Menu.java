package model.entity;

import java.util.HashMap;

/**
 *
 * @author milar
 */
public class Menu implements Entity{
    private final String TABLE_NAME = "menu";
    private String nameDish;
    private int price;
    private String course;
    private String image;
   
    
    public Menu (String nameDish, int price, String course, String image){    
	this.nameDish=nameDish;
    	this.price=price;
	this.course=course;
        this.image=image;
    }
    
    public String getNameDish(){
        return this.nameDish;
    }
    
    public int getPrice(){
        return this.price;
    }
    
    public String getCourse(){
        return this.course;
    }
    
    public String getImage(){
        return this.image;
    }
    @Override
    public String getTableName() {
        return this.TABLE_NAME;
    }
    
        @Override
    public HashMap<String, Object> map() {
        HashMap<String, Object> res = new HashMap<String, Object>();
        res.put("nameDish", this.nameDish);
        res.put("price", this.price);
        res.put("course", this.course);
        res.put("image", this.image);
        return res;
    }
    
}
