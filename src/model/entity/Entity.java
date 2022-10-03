package model.entity;

import model.dao.Table;

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
