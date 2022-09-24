package view.sceneControllers;

import business.OrderManager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class OrderPaneController extends BaseView implements Initializable {

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
    @FXML
    private AnchorPane pageSelectionContainer;
    @FXML
    private Button previousPageBtn;
    @FXML
    private ComboBox<Integer> pageLengthSelector;
    @FXML
    private Button nextPageButton;
    private int index = 0;
    private int pageNumber = 1;
    private int lastPage;
    private Integer[] pageLengthValues = {15,30,60};
    private int  currentPageLength = pageLengthValues[0];
    private int totalOrders;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        commController.setOrderPaneController(this);
        ordersContainer.getChildren().clear();
        totalOrders = orderManager.getTotalOrders();
        try {
            this.insertSearchComponent();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        pageLengthSelector.setValue(pageLengthValues[0]);
        orderManager.setOrdersPageLength(pageLengthValues[0]);
        pageLengthSelector.getItems().addAll(pageLengthValues);
        lastPage = (int)(Math.ceil(totalOrders/(double)currentPageLength));
    }
    public void setMode(String mode){
        this.renderingMode = mode;
        this.insertOrders(1);
    }

    private void insertSearchComponent() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/scene/ordersSearchPane.fxml"));
        Node orderSearch = loader.load();
        OrderSearchController orderSearchController = loader.getController();
        searchComponentContainer.getChildren().add(orderSearch);
    }

    private void insertOrders(int pageNumber){
        this.ordersContainer.getChildren().clear();
        index = 0;
        if(this.renderingMode == this.ORDERS_ON_DELIVERY_MODE){
            ArrayList<HashMap<String,Object>> orders = this.orderManager.getDeliveringOrdersPage(pageNumber);
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

    public void goToPreviousPage(MouseEvent mouseEvent) {
        if(this.pageNumber != 1){
            pageNumber --;
            this.insertOrders(pageNumber);
        }
    }
    public void goToNextPage(MouseEvent mouseEvent) {
        if(this.pageNumber < lastPage){
            pageNumber++;
            this.insertOrders(pageNumber);
        }
    }
    public void changePageLength(ActionEvent actionEvent) {
        int nextPageLength = pageLengthSelector.getValue();
        if(nextPageLength > currentPageLength){
            int ratio = (int)Math.ceil(nextPageLength/(double)currentPageLength);
            pageNumber = (int)Math.ceil(pageNumber/ratio);
        }else if(nextPageLength < currentPageLength){

        }

        this.orderManager.setOrdersPageLength(nextPageLength);
        currentPageLength = nextPageLength;
        lastPage = (int)(Math.ceil(totalOrders/(double)currentPageLength));
        this.insertOrders(pageNumber);
    }
}
