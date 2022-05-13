/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.OrderTable;
import dao.ProductTable;
import dao.ReceiptTable;
import dao.SupplierTable;
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
        tableMap.put("receipt", new ReceiptTable());
        tableMap.put("order", new OrderTable());
        tableMap.put("supplier", new SupplierTable());

    }

    @Override
    public void save(Entity e){ 
        Table entityTable = this.tableMap.get(e.getTableName());
        entityTable.save(e);
        //return 1;
    }


    @Override
    public void update(Entity e) {
        Table entityTable = this.tableMap.get(e.getTableName());
        entityTable.update(e);
    }


    @Override
    public void delete(Entity e) {
        Table entityTable = this.tableMap.get(e.getTableName());
        entityTable.delete(e);
    }
    
}
