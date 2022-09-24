package business;

import model.dao.OrderTable;
import model.entity.Order;

import java.util.ArrayList;
import java.util.HashMap;

public class OrderManager {

    private OrderTable orderTable = new OrderTable();
    public int currentPageLength;
    public ArrayList getDeliveringOrdersPage(int page){
        ArrayList<Order> orders = this.orderTable.getPageWithStatus(Order.CREATED_STATE, page);
        return this.parseRes(orders);
    }
    public ArrayList getAllDeliveringOrders(){
        return this.getAllByStatus(Order.CREATED_STATE);
    }
    public ArrayList getAllDeliveredOrders(){
        return this.getAllByStatus(Order.DELIVERED_STATE);
    }
    private ArrayList getAllByStatus(String status){
        ArrayList<Order> orders = this.orderTable.getAllByStatus(status);
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
}
