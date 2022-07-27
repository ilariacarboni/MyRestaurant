package model.entity;

import java.util.HashMap;

/**
 *
 * @author Natalia
 */
public interface Entity {
    
    String getTableName();
    
    //returns a key->value representation of the entity
    HashMap<String, Object> map();
}
