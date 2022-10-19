package business;

import model.dao.MenuTable;
import model.entity.Menu;

import java.util.HashMap;

public class MenuManager {

    private MenuTable menuTable;

    public MenuManager(){
        this.menuTable = new MenuTable();
    }

    public boolean saveDish(HashMap<String, Object> data){
        Menu dish = this.menuTable.constructEntityFromMap(data);
        return this.menuTable.save(dish);
    }
}
