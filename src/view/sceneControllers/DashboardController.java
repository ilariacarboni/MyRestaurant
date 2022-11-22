
package view.sceneControllers;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import view.utils.BackButton;
import view.utils.CustomDialog;
import view.utils.imageManagers.LocatedImage;


/**
 * FXML Controller class
 *
 * @author Natalia
 */
public class DashboardController extends BaseView implements Initializable {


    final String BTN_SELECTED_STYLE_CLASS = "menuButton-selected";
    final String BTN_HOVER_STYLE_CLASS = "menuButton-hover";
    final String DASHBOARD_BTN_ICON_PATH = "/view/style/img/menu-icons/home.png";
    final String MENU_BTN_ICON_PATH = "/view/style/img/menu-icons/menu.png";
    final String ORDERS_BTN_ICON_PATH = "/view/style/img/menu-icons/orders.png";
    final String UTILITY_BTN_ICON_PATH = "/view/style/img/menu-icons/utility.png";
    final String STORE_BTN_ICON_PATH = "/view/style/img/menu-icons/store.png";
    final String EMPLOYEES_BTN_ICON_PATH = "/view/style/img/menu-icons/employees.png";
    final String LOGIN_BTN_ICON_PATH = "/view/style/img/menu-icons/login.png";

    public StackPane centralPane;
    public ImageView dashboardBtnIcon;
    public ImageView menuBtnIcon;
    public ImageView ordersBtnIcon;
    public ImageView utilityBtnIcon;
    public ImageView storeBtnIcon;
    public ImageView employeesBtnIcon;
    public Button loginBtn;
    public ImageView loginBtnIcon;
    public AnchorPane loginBtnContainer;
    public AnchorPane backButtonContainer;
    public Button dashboardBtn;
    public Button menuBtn;
    public Button employeesBtn;
    public Button storeBtn;
    public Button utilityBtn;
    public Button ordersBtn;
    public Pane buttonContainer;
    public BorderPane borderPane;
    public Label usernameTextField;

    private Node employeesPane = null;
    private Node menuPane = null;
    private Node utilitiesPane = null;
    private Node categoryPane = null;
    private Node ordersPane = null;
    private Node loginPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        this.commController.setDashboardController(this);

        dashboardBtnIcon.setImage(new LocatedImage(this.DASHBOARD_BTN_ICON_PATH));
        employeesBtnIcon.setImage(new LocatedImage(this.EMPLOYEES_BTN_ICON_PATH));
        menuBtnIcon.setImage(new LocatedImage(this.MENU_BTN_ICON_PATH));
        ordersBtnIcon.setImage(new LocatedImage(this.ORDERS_BTN_ICON_PATH));
        storeBtnIcon.setImage(new LocatedImage(this.STORE_BTN_ICON_PATH));
        utilityBtnIcon.setImage(new LocatedImage(this.UTILITY_BTN_ICON_PATH));

        loginBtnIcon.setImage(new LocatedImage(this.LOGIN_BTN_ICON_PATH));
        loginBtn.fire();
    }

    public void addBackButton(BackButton btn){
        this.backButtonContainer.getChildren().clear();
        this.backButtonContainer.getChildren().add(btn);
    }

    public void removeBackButton(){
        this.backButtonContainer.getChildren().clear();
    }
    private void resetAllExcept(Button btn){
        for(Node node: buttonContainer.getChildren() ){
            if(node.getId() != btn.getId() && node.getStyleClass().contains(this.BTN_SELECTED_STYLE_CLASS)){
                node.getStyleClass().remove(this.BTN_SELECTED_STYLE_CLASS);
            }
        }
        this.setCenterPane(null, null);
        this.setRightPane(null);
    }
    
    private void select(Button btn){
        resetAllExcept(btn);
        if(!btn.getStyleClass().contains(this.BTN_SELECTED_STYLE_CLASS)){
            btn.getStyleClass().add(this.BTN_SELECTED_STYLE_CLASS);
        }
    }

    @FXML
    private void dashboardBtnClicked(ActionEvent event) throws IOException {
        select(dashboardBtn);
        boolean isLogged = this.checkLogin();
        if(isLogged){
            borderPane.setCenter(FXMLLoader.load(getClass().getResource(this.CHART_PANE_PATH)));
        }
    }

    @FXML
    private void menuBtnClicked(ActionEvent event) throws IOException {
        select(menuBtn);
        boolean logged = this.checkLogin();
       if(logged){
            if(this.menuPane == null){
                this.menuPane = FXMLLoader.load(getClass().getResource(this.MENU_PANE_PATH));
            }
            borderPane.setCenter(this.menuPane);
            MenuPaneController menuPaneController = commController.getMenuPaneController();
            menuPaneController.animate();
            borderPane.setRight(null);
        }
    }

    @FXML
    private void employeesBtnClicked(ActionEvent event) throws IOException {
        select(employeesBtn);
        boolean logged = this.checkLogin();
        if(logged){
            if(this.employeesPane == null){
                this.employeesPane = FXMLLoader.load(getClass().getResource(EMPLOYEE_LIST_PANE_PATH));
            }
            borderPane.setCenter(this.employeesPane);
            EmployeesListController empListController = commController.getEmployeePaneController();
            empListController.animate();
            borderPane.setRight(null);
            empListController.showAddEmployeeBtn();
        }
    }

    @FXML
    private void storeBtnClicked(ActionEvent event) throws IOException {
        select(storeBtn);
        boolean isLogged = checkLogin();
        if(isLogged){
            if(this.categoryPane == null){
                this.categoryPane = FXMLLoader.load(getClass().getResource(this.CATEGORY_PANE_PATH));
            }
            CategoryPaneController categoryPaneController = commController.getCategoryPaneController();
            borderPane.setCenter(this.categoryPane);
            categoryPaneController.animate();
            borderPane.setRight(null);
        }
    }

    @FXML
    private void utilityBtnClicked(ActionEvent event) throws IOException {
        select(utilityBtn);
        boolean isLogged = this.checkLogin();
        if(isLogged){
            if(this.utilitiesPane == null){
                this.utilitiesPane = FXMLLoader.load(getClass().getResource(this.UTILITIES_PANE_PATH));
            }
            borderPane.setCenter(this.utilitiesPane);
            borderPane.setRight(null);
            UtilitiesPaneController utilitiesPaneController = commController.getUtilitiesPaneController();
            utilitiesPaneController.showAddUtilityBtn();
       }
    }

    @FXML
    private void ordersBtnClicked(ActionEvent event) throws IOException {
        select(ordersBtn);
        boolean isLogged = this.checkLogin();
        if(isLogged){
            if(this.ordersPane == null){
                this.ordersPane =  FXMLLoader.load(getClass().getResource(this.ORDER_PANE_PATH));
            }
            borderPane.setCenter(this.ordersPane);
            borderPane.setRight(null);
            OrderPaneController orderPaneController = commController.getOrderPaneController();
            orderPaneController.refresh();
            orderPaneController.setMode(orderPaneController.ORDERS_ON_DELIVERY_MODE);
        }
    }

    public void loginBtnClicked(ActionEvent actionEvent) throws IOException {
        select(loginBtn);

        if(this.loginPane == null){
            this.loginPane =  FXMLLoader.load(getClass().getResource(this.LOGIN_PANE_PATH));
        }
        borderPane.setCenter(this.loginPane);
        borderPane.setRight(null);
        LoginPaneController loginPaneController = commController.getLoginPaneController();
    }
    
    public void setCenterPane(Node node, BackButton backButton){
        if(backButton != null){
            this.addBackButton(backButton);
        }else{
            this.removeBackButton();
        }
        borderPane.setCenter(node);
    }
    public Node getCenterPane(){ return borderPane.getCenter();}
    public void setRightPane(Node node){ borderPane.setRight(node);}
    public Node getRightPane(){ return borderPane.getRight();}
    public void menuBtnHovered(MouseEvent mouseEvent) {
        Button btn = (Button)mouseEvent.getSource();
        if(!btn.getStyleClass().contains(this.BTN_SELECTED_STYLE_CLASS)){
            btn.getStyleClass().add("menuButton-hover");
            ImageView iconContainer = (ImageView) btn.getGraphic();
            String staticIconUrl = ((LocatedImage)iconContainer.getImage()).getUrl();
            String gifIconUrl = staticIconUrl.replace("png","gif");
            ImageView btnIcon = (ImageView) buttonContainer.lookup("#"+iconContainer.getId());
            if(btnIcon == null){
                btnIcon = (ImageView) loginBtnContainer.lookup("#"+iconContainer.getId());
            }
            btnIcon.setImage(new LocatedImage(gifIconUrl));
        }
    }

    public void menuBtnNotHovered(MouseEvent mouseEvent) {
        Button btn = (Button)mouseEvent.getSource();
        if(btn.getStyleClass().contains(this.BTN_HOVER_STYLE_CLASS)){
            btn.getStyleClass().remove(this.BTN_HOVER_STYLE_CLASS);
            ImageView iconContainer = (ImageView) btn.getGraphic();
            String gifIconUrl = ((LocatedImage)iconContainer.getImage()).getUrl();
            String staticIconUrl = gifIconUrl.replace("gif","png");
            ImageView btnIcon = (ImageView) buttonContainer.lookup("#"+iconContainer.getId());
            if(btnIcon == null){
                btnIcon = (ImageView) loginBtnContainer.lookup("#"+iconContainer.getId());
            }
            btnIcon.setImage(new LocatedImage(staticIconUrl));
        }
    }

    public void newOrderFor(String productName){
        ordersBtn.fire();
        OrderPaneController orderPaneController = commController.getOrderPaneController();
        HashMap<String, Object> info = new HashMap<>();
        info.put("name", productName);
        orderPaneController.showNewOrderPane(info);
    }

    private boolean checkLogin(){
        boolean res = false;
        HashMap<String, Object> loggedUser = commController.getLoggedUser();
        if(loggedUser != null){
            res = true;
        }
        if(!res){
            String text = "Per accedere a questa sezione devi effettuare il Login.";
            dialog.setInfo(text, CustomDialog.TYPE_LOCK);
            dialog.setButtons(ButtonType.OK);
            Optional<ButtonType> dialogRes = dialog.showAndWait("Attenzione !");
            if(dialogRes.get() == ButtonType.OK){
                loginBtn.fire();
            }
        }
        return res;
    }

    public void setUsername(String username){
        usernameTextField.setText(username);
    }

    public Button getDashboardBtn(){
        return this.dashboardBtn;
    }

    public void removeLoginButton(){
        this.loginBtn.setVisible(false);
        this.loginBtn.setManaged(false);
    }
}
