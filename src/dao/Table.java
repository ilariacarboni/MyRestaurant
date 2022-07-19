/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Natalia
 * @param <T>
 */
public interface Table<T> {
    
    ArrayList<T> getAll();
    
    //se servisse di restituire un oggetto nella lista
    //T get(int id);
    
    boolean save(T entity);
    
    boolean update(T entity);
    
    boolean delete(T entity);
    
    ArrayList<T> getFrom(Object searchParam, String paramName);
    
    T constructEntityFromMap(HashMap<String,Object> map);
}
