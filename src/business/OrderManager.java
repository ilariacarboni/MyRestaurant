package business;

import model.dao.OrderTable;
import model.entity.Order;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class OrderManager {

    private OrderTable orderTable = new OrderTable();
    private ProductManager productManager = new ProductManager();
    public ArrayList getDeliveringOrdersPage(int page, HashMap<String, String> filters){
        ArrayList<Order> orders = this.orderTable.getPageWithStatus(Order.CREATED_STATE, page, filters);
        return this.parseRes(orders);
    }
    public ArrayList getDeliveredOrdersPage(int page, HashMap<String, String> filters){
        ArrayList<Order> orders = this.orderTable.getPageWithStatus(Order.DELIVERED_STATE, page, filters);
        return this.parseRes(orders);
    }
    public ArrayList getAllDeliveringOrders(){
        return this.getAllByStatus(Order.CREATED_STATE);
    }
    public ArrayList getAllDeliveredOrders(){
        return this.getAllByStatus(Order.DELIVERED_STATE);
    }
    private ArrayList getAllByStatus(String status){
        ArrayList<Order> orders = null;
        orders = this.orderTable.getAllByStatus(status);

        return this.parseRes(orders);
    }
    private ArrayList parseRes(ArrayList<Order> list){
        ArrayList<HashMap<String,Object>> res  = new ArrayList<HashMap<String,Object>>();
        for(Order order : list){
            res.add(order.map());
        }
        return res;
    }
    public void setOrdersPageLength(int l){
        this.orderTable.setPageLength(l);
    }
    public int getTotalOrders(){
        return orderTable.getTotal();
    }
    public boolean setDelivered(HashMap<String, Object> order) {
        order.put("state", Order.DELIVERED_STATE);
        Order orderEntity = orderTable.constructEntityFromMap(order);
        return orderTable.update(orderEntity);
    }

    public boolean insertOrder(String date, String productName, int qty){
        boolean res = false;
        ArrayList<HashMap<String, Object>> products = this.productManager.getFrom(productName, "name");
        if(!products.isEmpty()){
            HashMap<String, Object> product = products.get(0);
            int barcode = (int)product.get("barcode");
            Order order = new Order(date, barcode, qty, Order.CREATED_STATE);
            res = this.orderTable.save(order);
        }
        return res;
    }
}
