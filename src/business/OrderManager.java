package business;

import model.dao.OrderTable;
import model.entity.Order;

import java.util.ArrayList;
import java.util.HashMap;

public class OrderManager {

    private OrderTable orderTable = new OrderTable();
    //salvataggio
    //modifica
    //getAll
    public ArrayList getAllDeliveringOrders(){
        ArrayList<Order> orders = this.orderTable.getAllDelivering();
        ArrayList<HashMap<String,Object>> res  = new ArrayList<HashMap<String,Object>>();
        for(Order order : orders){
            res.add(order.map());
        }
        return res;
    }

    //getPage

}
