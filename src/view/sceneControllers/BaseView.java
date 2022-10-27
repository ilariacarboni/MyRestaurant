/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.sceneControllers;

import controller.ControllerForView;
import controller.IControllerForView;

/**
 *
 * @author Natalia
 */
public class BaseView {
    //costanti per i percorsi relativi dei file fxml delle scene
    protected final String BACKGROUND_PATH = "/view/style/img/background/grey.jpeg";
    protected final String CHART_PANE_PATH = "/view/scene/chartsPane.fxml";
    protected final String MENU_PANE_PATH = "/view/scene/MenuPanel.fxml";
    protected final String EMPLOYEE_LIST_PANE_PATH = "/view/scene/EmployeesList.fxml";
    protected final String CATEGORY_PANE_PATH = "/view/scene/categoryPane.fxml";
    protected final String UTILITIES_PANE_PATH = "/view/scene/UtilitiesPanel.fxml";
    protected final String ORDER_PANE_PATH = "/view/scene/orderPane.fxml";
    protected final String ADD_CATEGORY_PANE_PATH = "/view/scene/addCategory.fxml";
    protected final String PRODUCT_PANE_PATH ="/view/scene/productsPane.fxml";
    protected final String CATEGORY_COMPONENT_PATH ="/view/scene/category.fxml";
    protected final String ORDER_SEARCH_PANE_PATH ="/view/scene/ordersSearchPane.fxml";
    protected final String ORDER_COMPONENT_PATH ="/view/scene/order.fxml";
    protected final String ADD_ORDER_PANE_PATH ="/view/scene/addOrderPane.fxml";
    protected final String PRODUCT_INFO_PANE_PATH ="/view/scene/productInfoPane.fxml";
    protected final String PRODUCT_COMPONENT_PATH ="/view/scene/product.fxml";
    protected final String ADD_PRODUCT_PANE_PATH ="/view/scene/addProductPane.fxml";
    protected final String LOGIN_PANE_PATH = "/view/scene/loginPane.fxml";
    protected final String USER_COMPONENT_PATH = "view/scene/userInfoPane.fxml";

    public int permissionLevel;

    protected CommunicationController commController = CommunicationController.getInstance();
}
