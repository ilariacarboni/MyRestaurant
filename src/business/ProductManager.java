package business;

import model.dao.ProductTable;
import model.entity.Product;

import java.util.ArrayList;
import java.util.HashMap;

public class ProductManager {
    private ProductTable productTable;

    public ProductManager(){
        this.productTable = new ProductTable();
    }

    public boolean updateProduct(HashMap<String, Object> productInfo){
        Product prod = this.productTable.constructEntityFromMap(productInfo);
        return this.productTable.update(prod);
    }

    public HashMap<String, Object> getProduct(int productId){
        HashMap<String, Object> res = null;
        ArrayList<Product> products = this.productTable.getFrom(productId, "barcode");
        if(!products.isEmpty()){
            //la ricerca per id (barcode) se produce risultati ne produce solo uno
            Product prod = products.get(0);
            res = prod.map();
        }
        return res;
    }

    public HashMap<Integer, Integer> getStoreStatistics(int prodId){
        return this.productTable.getProductUsageInLastYear(prodId);
    }
}
