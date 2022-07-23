
package controller;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;


/**
 * FXML Controller class
 *
 * @author Natalia
 */
public class DashboardController extends BaseView implements Initializable {


    final String BTN_SELECTED_STYLE_CLASS = "menuButton-selected";
    @FXML
    private Button dashboardBtn;
    @FXML
    private Button menuBtn;
    @FXML
    private Button employeesBtn;
    @FXML
    private Button storeBtn;
    @FXML
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
    private Pane buttonContainer;
    @FXML
    private BorderPane borderPane;

    private Node categoryPane = null;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dashboardBtn.fire();
    } 

    private void resetAllExcept(Button btn){
        for(Node node: buttonContainer.getChildren() ){
            if(node.getId() != btn.getId() && node.getStyleClass().contains(this.BTN_SELECTED_STYLE_CLASS)){
                node.getStyleClass().remove(this.BTN_SELECTED_STYLE_CLASS);
            }
        }
        
    }
    
    private void select(Button btn){
        btn.getStyleClass().add(this.BTN_SELECTED_STYLE_CLASS);
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
        borderPane.setCenter(FXMLLoader.load(getClass().getResource("/view/MenuPanel.fxml")));
        borderPane.setRight(FXMLLoader.load(getClass().getResource("/view/AddMenuDish.fxml")));

    }

    @FXML
    private void employeesBtnClicked(ActionEvent event) throws IOException {
        select(employeesBtn);
        resetAllExcept(employeesBtn);
        borderPane.setRight(FXMLLoader.load(getClass().getResource("/view/AddEmployee.fxml")));
        borderPane.setCenter(FXMLLoader.load(getClass().getResource("/view/EmployeesList.fxml")));
    }

    @FXML
    private void storeBtnClicked(ActionEvent event) throws IOException {
        select(storeBtn);
        resetAllExcept(storeBtn);

        if(this.categoryPane == null){
            this.categoryPane = FXMLLoader.load(getClass().getResource("/view/categoryPane.fxml"));
        }
        borderPane.setCenter(this.categoryPane);
        CategoryPaneController categoryPaneController = commController.getCategoryPaneController();
        categoryPaneController.animate();
        borderPane.setRight(null);
    }

    @FXML
    private void billsBtnClicked(ActionEvent event) throws IOException {
        select(billsBtn);
        resetAllExcept(billsBtn);
        borderPane.setCenter(FXMLLoader.load(getClass().getResource("/view/UtilitiesPanel.fxml")));
    }

    @FXML
    private void ordersBtnClicked(ActionEvent event) {
        select(ordersBtn);
        resetAllExcept(ordersBtn);
    }
    
    public void setCenterPane(Node node){
        borderPane.setCenter(node);
    }

    public void menuBtnHovered(MouseEvent mouseEvent) {
        Node btn = (Node)mouseEvent.getSource();
        if(!btn.getStyleClass().contains(this.BTN_SELECTED_STYLE_CLASS)){
            btn.getStyleClass().add("menuButton-hover");
        }
    }

    public void menuBtnNotHovered(MouseEvent mouseEvent) {
        Node btn = (Node)mouseEvent.getSource();
        if(btn.getStyleClass().contains("menuButton-hover")){
            btn.getStyleClass().remove("menuButton-hover");
        }
    }
}
