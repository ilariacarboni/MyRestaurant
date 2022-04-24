/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Entity;

/**
 *
 * @author Natalia
 */
public interface IControllerForView {
    
    void save(Entity e);
    void update(Entity e);
    void remove(Entity e);
    Entity getById(String tableName, int id);
     
}
