/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;


import java.util.HashMap;


public interface Entity {
    
    String getTableName();
    
    //returns a key->value representation of the entity
    HashMap<String, Object> map();
}
