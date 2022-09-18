package business;

import model.dao.UtilityTable;
import model.entity.Utility;

import java.util.HashMap;

public class UtilityManager {

    private UtilityTable utilityTable;

    public UtilityManager(){
        this.utilityTable = new UtilityTable();
    }

    public boolean save(HashMap<String, Object> data){
        Utility utility = this.utilityTable.constructEntityFromMap(data);
        return this.utilityTable.save(utility);
    }
}
