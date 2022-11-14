package business;

import java.util.ArrayList;
import model.dao.MenuTable;
import model.entity.Menu;

import java.util.HashMap;

public class MenuManager {

    private MenuTable menuTable;

    public MenuManager(){
        this.menuTable = new MenuTable();
    }

    public boolean updateMenu(HashMap<String, Object> dishinfo ){
        Menu menu = this.menuTable.constructEntityFromMap(dishinfo);
        return this.menuTable.update(menu);
    }
    
    public boolean deleteMenu(HashMap<String, Object> dishinfo ){
        Menu menu = this.menuTable.constructEntityFromMap(dishinfo);
        return this.menuTable.delete(menu);
    }

    public HashMap<String, Object> getMenu(String nameDish){
        HashMap<String, Object> res = null;
        ArrayList<Menu> menues = this.menuTable.getFrom(nameDish, "nameDish");
        if(!menues.isEmpty()){
            Menu menu = menues.get(0);
            res = menu.map();
        }
        return res;
    }

    public ArrayList<HashMap<String, Object>> getFrom(Object searchParam, String paramName){
        ArrayList<Menu> menues = this.menuTable.getFrom(searchParam, paramName);
        ArrayList<HashMap<String,Object>> res  = new ArrayList<HashMap<String,Object>>();
        for(Menu menu : menues){
            res.add(menu.map());
        }
        return res;
    }

    public ArrayList getAll(){
        ArrayList<Menu> menues = this.menuTable.getAll();
        ArrayList<HashMap<String,Object>> res  = new ArrayList<HashMap<String,Object>>();
        for(Menu menu : menues){
            res.add(menu.map());
        }
        return res;
    }
    public boolean saveDish(HashMap<String, Object> data){
        Menu dish = this.menuTable.constructEntityFromMap(data);
        return this.menuTable.save(dish);
    }
}
