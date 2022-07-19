/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Natalia
 */
public interface IControllerForView {
    
    boolean save(HashMap<String, Object> EntityMap, String table);
    boolean update(HashMap<String, Object> EntityMap, String table);
    boolean delete(HashMap<String, Object> EntityMap, String table);
    ArrayList<HashMap<String, Object>> getAll(String tableName);
    ArrayList<HashMap<String, Object>> getFrom(Object searchParam, String paramName, String tableName);
}
