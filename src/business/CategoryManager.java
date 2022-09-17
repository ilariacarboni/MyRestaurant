package business;

import model.dao.OrderTable;
import model.dao.ProductTable;

import java.util.HashMap;
import java.util.Set;

public class CategoryManager {

    private ProductTable productTable;
    private OrderTable orderTable;

    public CategoryManager(){
        this.productTable = new ProductTable();
        this.orderTable = new OrderTable();
    }

    public HashMap<String, HashMap<String, Object>> getCategoriesBasicInfo(){
        HashMap<String, Integer> productsPerCategory = this.productTable.getTotalProductsPerCategory();
        HashMap<String, String> lastOrderPerCategory = this.orderTable.getLastOrderPerCategory();
        HashMap<String, Double> averageExpensePerCategory = this.orderTable.averageMonthlyExpensePerCategory() ;

        Set<String> categories = productsPerCategory.keySet();
        HashMap<String, HashMap<String, Object>> res = new HashMap<>();
        for (String category:categories){
            HashMap<String, Object> categoryInfo = new HashMap<>();
            categoryInfo.put("productsTotal", productsPerCategory.get(category));
            categoryInfo.put("lastOrder", lastOrderPerCategory.get(category));
            categoryInfo.put("averageExpense", averageExpensePerCategory.get(category));
            res.put(category, categoryInfo);
        }
        return res;
    }
}
