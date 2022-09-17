package business;

import model.dao.ProductTable;
import model.dao.ReceiptTable;
import model.dao.UtilityTable;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class ChartsManager {
    private ReceiptTable receiptTable = new ReceiptTable();
    private ProductTable productTable = new ProductTable();
    private UtilityTable utilityTable = new UtilityTable();

    /**
     * @return an HashMap containing the revenue month by month in a time window of one year;
     * the keys of the HashMap represents month/year and the values the revenue for that month
     */
    public LinkedHashMap<String, Double> getRevenueData(){
        return this.receiptTable.getRevenuePerMonth();
    }

    public HashMap<String, Double> getWarehouseOccupationData(){
        return this.productTable.getWarehouseComposition();
    }

    public LinkedHashMap<String, Double> getUtilityCostPerMonthData(){
        return this.utilityTable.getUtilityCostPerMonth();
    }

    public LinkedHashMap<String, LinkedHashMap<String, Integer>> getMoreOrderedDishes(){
        return this.receiptTable.getMoreOrderedDishes();
    }
}