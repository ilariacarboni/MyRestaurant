package view.sceneControllers;

import business.OrderManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class OrderPaneController extends BaseView implements Initializable {

    public static final String ORDERS_ON_DELIVERY_MODE = "DELIVERING";
    public static final String ORDERS_HISTORY_MODE     = "HISTORY";

    public BorderPane mainContainer;
    public ImageView renderingModeIcon;
    public AnchorPane newOrderBtn;
    public Label statusLabel;
    public AnchorPane changeRenderingModeBtn;
    public Label renderingModeLabel;
    public GridPane ordersContainer;
    public AnchorPane searchComponentContainer;
    public AnchorPane pageSelectionContainer;
    public ComboBox<Integer> pageLengthSelector;
    public ScrollPane ordersOuterContainer;

    private int index = 0;
    private int pageNumber = 1;
    private int lastPage;
    private Integer[] pageLengthValues = {15,30,60};
    private int  currentPageLength = pageLengthValues[0];
    private int totalOrders;
    private OrderSearchController orderSearchController;
    private OrderManager orderManager = new OrderManager();
    private final String TO_HISTORY_TEXT = "Storico Ordini";
    private final String TO_CURRENT_TEXT = "Ordini in consegna";
    private String renderingMode;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        commController.setOrderPaneController(this);
        ordersOuterContainer.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        mainContainer.setBackground(imagesProvider.getBackground());
        ordersContainer.getChildren().clear();
        totalOrders = orderManager.getTotalDelivering();
        try {
            this.insertSearchComponent();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        pageLengthSelector.setValue(pageLengthValues[0]);
        orderManager.setOrdersPageLength(pageLengthValues[0]);
        pageLengthSelector.getItems().addAll(pageLengthValues);
        lastPage = (int)(Math.ceil(totalOrders/(double)currentPageLength));
        addFilterListener(orderSearchController.numberSearchBar);
        addFilterListener(orderSearchController.dateSearchBar);
        addFilterListener(orderSearchController.supplierSearchBar);
        this.refresh();
    }

    public void refresh(){
        newOrderBtn.setVisible(true);
        newOrderBtn.setManaged(true);
    }
    private void addFilterListener(TextField field){
        field.textProperty().addListener((observable, oldValue, newValue) ->{
            insertOrders(pageNumber);
        });
    }
    public void setMode(String mode){
        this.renderingMode = mode;
        this.insertOrders(1);
    }

    private void insertSearchComponent() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(this.ORDER_SEARCH_PANE_PATH));
        Node orderSearch = loader.load();
        this.orderSearchController = commController.getOrderSearchController();
        searchComponentContainer.getChildren().add(orderSearch);
    }
    private void insertOrders(int pageNumber){
        HashMap<String, String> filters = this.getFilters();
        this.ordersContainer.getChildren().clear();
        index = 0;
        ArrayList<HashMap<String, Object>> orders = null;
        if(this.renderingMode == this.ORDERS_ON_DELIVERY_MODE){
            orders = this.orderManager.getDeliveringOrdersPage(pageNumber, filters);
        }else {
            orders = orderManager.getDeliveredOrdersPage(pageNumber, filters);
        }
        orders.forEach((order)->{
            try {
                this.addOrder(order, index);
                index ++;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private HashMap<String, String> getFilters(){
        String orderNumberFilter = this.orderSearchController.numberSearchBar.getText();
        String orderDateFilter = this.orderSearchController.dateSearchBar.getText();
        String orderSupplierFilter = this.orderSearchController.supplierSearchBar.getText();
        HashMap<String, String> res = new HashMap<>();
        res.put("number", orderNumberFilter);
        res.put("date", orderDateFilter);
        res.put("supplier", orderSupplierFilter);
        return res;
    }

    private void addOrder(HashMap<String, Object> order, int i) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(this.ORDER_COMPONENT_PATH));
        Node orderNode = loader.load();
        OrderController orderController = loader.getController();
        if(this.renderingMode == this.ORDERS_HISTORY_MODE){
            orderController.hideDeliveredBtn();
        }
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
            pageNumber = (int)Math.ceil((double)pageNumber/ratio);
        }else if(nextPageLength < currentPageLength){
        }
        this.orderManager.setOrdersPageLength(nextPageLength);
        currentPageLength = nextPageLength;
        lastPage = (int)(Math.ceil(totalOrders/(double)currentPageLength));
        this.insertOrders(pageNumber);
    }

    public void changeRenderingMode(MouseEvent mouseEvent) {
        if(renderingMode == this.ORDERS_ON_DELIVERY_MODE){
            renderingMode = this.ORDERS_HISTORY_MODE;
            totalOrders = orderManager.getTotalDelivered();
            renderingModeLabel.setText(this.TO_CURRENT_TEXT);
            renderingModeIcon.setImage(imagesProvider.getDeliveringImage());
            statusLabel.setText(this.TO_HISTORY_TEXT);
        }else if(renderingMode == this.ORDERS_HISTORY_MODE){
            renderingMode = this.ORDERS_ON_DELIVERY_MODE;
            totalOrders = orderManager.getTotalDelivered();
            renderingModeLabel.setText(this.TO_HISTORY_TEXT);
            renderingModeIcon.setImage(imagesProvider.getHistoryImage());
            statusLabel.setText(this.TO_CURRENT_TEXT);
        }
        this.insertOrders(pageNumber);
    }

    public void setDelivered(HashMap<String, Object> order) {
        boolean res = orderManager.setDelivered(order);
        if(res){
            this.insertOrders(pageNumber);
        }
    }
    public void newOrderBtnClicked(MouseEvent mouseEvent) {
        showNewOrderPane(null);
    }

    public void showNewOrderPane(HashMap<String, Object> orderInfo) {
        Node addOrderPane = null;
        if(commController.getAddOrderPaneController() != null){
            AddOrderPaneController addOrderPaneController =  commController.getAddOrderPaneController();
            addOrderPaneController.refresh();
            addOrderPane = addOrderPaneController.getAddOrderPane();
        }else{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(this.ADD_ORDER_PANE_PATH));
            try {
                addOrderPane = loader.load();
            } catch (IOException e) {
                System.out.println(e);
            }
        }
        this.commController.getDashboardController().setRightPane(addOrderPane);
        hideAddOrderBtn();
        if(orderInfo != null){
            commController.getAddOrderPaneController().setOrderInfo(orderInfo);
        }
    }

    public void updateOrders(){
        pageNumber = 1;
        this.insertOrders(1);
    }

    public void showAddOrderBtn(){
        newOrderBtn.setVisible(true);
        newOrderBtn.setManaged(true);
    }

    private void hideAddOrderBtn(){
        newOrderBtn.setVisible(false);
        newOrderBtn.setManaged(false);
    }
}
