/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.ProductTable;
import dao.Table;
import entity.Entity;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Natalia
 */
public class ControllerForView implements IControllerForView{
    
    //mappa le stringhe che vengono restituite dalle implementazioni delle Entity in
    //istanze di Entity
    private Map<String, Table> tableMap = new HashMap<>();
    private static ControllerForView instance = null;
    
    public static IControllerForView getInstance(){
        if(instance == null){
            instance=new ControllerForView();
        }
        return instance;
    }
    
    public ControllerForView(){
        tableMap.put("product", new ProductTable());
    }

    @Override
    public void save(Entity e){ 
        Table entityTable = this.tableMap.get(e.getTableName());
        entityTable.save(e);
    }


    @Override
    public void update(Entity e) {
    }


    @Override
    public void remove(Entity e) {
    }


    @Override
    public Entity getById(String tableName, int id) {
        return null;
    }
    
}
