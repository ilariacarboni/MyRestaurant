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
public class Supplier implements Entity{
    
    private String name;
    private String site;
    
    public Supplier(String name, String site){
        this.name=name;
        this.site=site;
    }
    
    public String getName(){
        return this.name;
    }
    
    public String getSite(){
        return this.site;
    }

    @Override
    public String getTableName() {
        return "supplier";
    }
    
}
