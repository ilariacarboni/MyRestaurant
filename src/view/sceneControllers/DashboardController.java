
package view.sceneControllers;
<<<<<<< HEAD
=======

>>>>>>> 27523d0ef7379d8b22f3e5c267dfc4473f2b3714
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
<<<<<<< HEAD
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import javafx.scene.image.ImageView;
import view.utils.LocatedImage;
=======
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
>>>>>>> 27523d0ef7379d8b22f3e5c267dfc4473f2b3714


/**
 * FXML Controller class
 *
 * @author Natalia
 */
<<<<<<< HEAD
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

    @FXML
    public ImageView dashboardBtnIcon;
    @FXML
    public ImageView menuBtnIcon;
    @FXML
    public ImageView ordersBtnIcon;
    @FXML
    public ImageView utilityBtnIcon;
    @FXML
    public ImageView storeBtnIcon;
    @FXML
    public ImageView employeesBtnIcon;
    @FXML
    public Button loginBtn;
    @FXML
    public ImageView loginBtnIcon;
    @FXML
    public AnchorPane loginBtnContainer;
=======
public class DashboardController implements Initializable {

>>>>>>> 27523d0ef7379d8b22f3e5c267dfc4473f2b3714
    @FXML
    private Button dashboardBtn;
    @FXML
    private Button menuBtn;
    @FXML
    private Button employeesBtn;
    @FXML
    private Button storeBtn;
    @FXML
<<<<<<< HEAD
    private Button utilityBtn;
    @FXML
    private Button ordersBtn;
    @FXML
=======
    private Button billsBtn;
    @FXML
    private Button ordersBtn;
    @FXML
    private Rectangle dashRect;
    @FXML
    private Rectangle menuRect;
    @FXML
    private Rectangle employeeRect;
    @FXML
    private Rectangle storeRect;
    @FXML
    private Rectangle billsRect;
    @FXML
    private Rectangle ordersRect;
    @FXML
>>>>>>> 27523d0ef7379d8b22f3e5c267dfc4473f2b3714
    private Pane buttonContainer;
    @FXML
    private BorderPane borderPane;

    private Node categoryPane = null;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
<<<<<<< HEAD
        dashboardBtnIcon.setImage(new LocatedImage(this.DASHBOARD_BTN_ICON_PATH));
        employeesBtnIcon.setImage(new LocatedImage(this.EMPLOYEES_BTN_ICON_PATH));
        menuBtnIcon.setImage(new LocatedImage(this.MENU_BTN_ICON_PATH));
        ordersBtnIcon.setImage(new LocatedImage(this.ORDERS_BTN_ICON_PATH));
        storeBtnIcon.setImage(new LocatedImage(this.STORE_BTN_ICON_PATH));
        utilityBtnIcon.setImage(new LocatedImage(this.UTILITY_BTN_ICON_PATH));
        loginBtnIcon.setImage(new LocatedImage(this.LOGIN_BTN_ICON_PATH));
        dashboardBtn.fire();
=======
>>>>>>> 27523d0ef7379d8b22f3e5c267dfc4473f2b3714
    } 

    private void resetAllExcept(Button btn){
        for(Node node: buttonContainer.getChildren() ){
<<<<<<< HEAD
            if(node.getId() != btn.getId() && node.getStyleClass().contains(this.BTN_SELECTED_STYLE_CLASS)){
                node.getStyleClass().remove(this.BTN_SELECTED_STYLE_CLASS);
=======
            if(node.getId() != btn.getId()){
                node.setStyle("-fx-background-color: #c2c6c8;");
                for(Node btnChild : ((Parent)node).getChildrenUnmodifiable()){
                    if(btnChild.getId() != null && btnChild.getId().contains("Rect")){
                        btnChild.setStyle("-fx-fill: #6e6c6c;");
                    }
                }
>>>>>>> 27523d0ef7379d8b22f3e5c267dfc4473f2b3714
            }
        }
        
    }
    
    private void select(Button btn){
<<<<<<< HEAD
        btn.getStyleClass().add(this.BTN_SELECTED_STYLE_CLASS);
=======
        btn.setStyle("-fx-background-color: #eef2e6;"); 
        for(Node node : btn.getChildrenUnmodifiable()){
            if(node.getId() != null && node.getId().contains("Rect")){
                node.setStyle("-fx-fill: #211f1f;");
            }
        }
>>>>>>> 27523d0ef7379d8b22f3e5c267dfc4473f2b3714
    }
    

    @FXML
    private void dashboardBtnClicked(ActionEvent event) throws IOException {
        select(dashboardBtn);
        resetAllExcept(dashboardBtn);
    }

    @FXML
    private void menuBtnClicked(ActionEvent event) throws IOException {
        select(menuBtn);
        resetAllExcept(menuBtn);
        borderPane.setCenter(FXMLLoader.load(getClass().getResource("/view/scene/MenuPanel.fxml")));
        borderPane.setRight(FXMLLoader.load(getClass().getResource("/view/scene/AddMenuDish.fxml")));

    }

    @FXML
    private void employeesBtnClicked(ActionEvent event) throws IOException {
        select(employeesBtn);
        resetAllExcept(employeesBtn);
        borderPane.setRight(FXMLLoader.load(getClass().getResource("/view/scene/AddEmployee.fxml")));
        borderPane.setCenter(FXMLLoader.load(getClass().getResource("/view/scene/EmployeesList.fxml")));
    }

    @FXML
    private void storeBtnClicked(ActionEvent event) throws IOException {
        select(storeBtn);
        resetAllExcept(storeBtn);

        if(this.categoryPane == null){
            this.categoryPane = FXMLLoader.load(getClass().getResource("/view/scene/categoryPane.fxml"));
        }
        borderPane.setCenter(this.categoryPane);
<<<<<<< HEAD
        CategoryPaneController categoryPaneController = commController.getCategoryPaneController();
        categoryPaneController.animate();
=======
>>>>>>> 27523d0ef7379d8b22f3e5c267dfc4473f2b3714
        borderPane.setRight(null);
    }

    @FXML
<<<<<<< HEAD
    private void utilityBtnClicked(ActionEvent event) throws IOException {
        select(utilityBtn);
        resetAllExcept(utilityBtn);
=======
    private void billsBtnClicked(ActionEvent event) throws IOException {
        select(billsBtn);
        resetAllExcept(billsBtn);
>>>>>>> 27523d0ef7379d8b22f3e5c267dfc4473f2b3714
        borderPane.setCenter(FXMLLoader.load(getClass().getResource("/view/scene/UtilitiesPanel.fxml")));
    }

    @FXML
    private void ordersBtnClicked(ActionEvent event) {
        select(ordersBtn);
        resetAllExcept(ordersBtn);
    }
    
    public void setCenterPane(Node node){
        borderPane.setCenter(node);
    }
<<<<<<< HEAD

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

    public void loginBtnClicked(ActionEvent actionEvent) {
    }
=======
    
>>>>>>> 27523d0ef7379d8b22f3e5c267dfc4473f2b3714
}
