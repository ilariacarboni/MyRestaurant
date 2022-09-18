package business;

import model.dao.SupplierTable;
import model.entity.Category;
import model.entity.Supplier;

import java.util.ArrayList;
import java.util.HashMap;

public class SupplierManager {

    private SupplierTable supplierTable;

    public SupplierManager(){
        this.supplierTable = new SupplierTable();
    }

    public ArrayList getAll(){
        ArrayList<Supplier> suppliers = this.supplierTable.getAll();
        ArrayList<HashMap<String,Object>> res  = new ArrayList<HashMap<String,Object>>();
        for(Supplier supplier : suppliers){
            res.add(supplier.map());
        }
        return res;
    }
}
