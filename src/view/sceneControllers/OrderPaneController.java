package view.sceneControllers;

import business.OrderManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class OrderPaneController extends BaseView implements Initializable {

    //definizione costati per modalità di rendering ordini in sospeso, storico ordini
    //renderizzazione ordini solo dopo aver settato la modalità
    public static final String ORDERS_ON_DELIVERY_MODE = "DELIVERING";
    public static final String ORDERS_HISTORY_MODE     = "HISTORY";

    private OrderManager orderManager = new OrderManager();
    private String renderingMode;
    @FXML
    private Label statusLabel;
    @FXML
    private GridPane ordersContainer;
    @FXML
    private AnchorPane searchComponentContainer;
    //aggiungere componente slider
    private int index = 0;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.commController.setOrderPaneController(this);
        this.ordersContainer.getChildren().clear();
        try {
            this.insertSearchComponent();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setMode(String mode){
        this.renderingMode = mode;
        this.insertOrders();
    }

    private void insertSearchComponent() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/scene/ordersSearchPane.fxml"));
        Node orderSearch = loader.load();
        OrderSearchController orderSearchController = loader.getController();
        searchComponentContainer.getChildren().add(orderSearch);
    }

    private void insertOrders(){
        if(this.renderingMode == this.ORDERS_ON_DELIVERY_MODE){
            ArrayList<HashMap<String,Object>> orders = this.orderManager.getAllDeliveringOrders();
            orders.forEach((order)->{
                try {
                    this.addOrder(order, index);
                    index ++;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    private void addOrder(HashMap<String, Object> order, int i) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/scene/order.fxml"));
        Node orderNode = loader.load();
        OrderController orderController = loader.getController();
        orderController.setOrderInfo(order);
        ordersContainer.add(orderNode, 0, i);
    }
}
