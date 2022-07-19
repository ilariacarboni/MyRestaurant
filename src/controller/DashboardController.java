
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;


/**
 * FXML Controller class
 *
 * @author Natalia
 */
public class DashboardController implements Initializable {


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


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    } 

    private void resetAllExcept(Button btn){
        for(Node node: buttonContainer.getChildren() ){
            if(node.getId() != btn.getId()){
                node.setStyle("-fx-background-color: #c2c6c8;");
                for(Node btnChild : ((Parent)node).getChildrenUnmodifiable()){
                    if(btnChild.getId() != null && btnChild.getId().contains("Rect")){
                        btnChild.setStyle("-fx-fill: #6e6c6c;");
                    }
                }
            }
        }
        
    }
    
    private void select(Button btn){
        btn.setStyle("-fx-background-color: #eef2e6;"); 
        for(Node node : btn.getChildrenUnmodifiable()){
            if(node.getId() != null && node.getId().contains("Rect")){
                node.setStyle("-fx-fill: #211f1f;");
            }
        }
    }
    

    @FXML
    private void dashboardBtnClicked(ActionEvent event) throws IOException {
        select(dashboardBtn);
        resetAllExcept(dashboardBtn);
        borderPane.setRight(FXMLLoader.load(getClass().getResource("/view/pane1.fxml")));
    }

    @FXML
    private void menuBtnClicked(ActionEvent event) throws IOException {
        select(menuBtn);
        resetAllExcept(menuBtn);
        borderPane.setRight(FXMLLoader.load(getClass().getResource("/view/pane2.fxml")));
    }

    @FXML
    private void employeesBtnClicked(ActionEvent event) {
        select(employeesBtn);
        resetAllExcept(employeesBtn);
    }

    @FXML
    private void storeBtnClicked(ActionEvent event) throws IOException {
        select(storeBtn);
        resetAllExcept(storeBtn);
        borderPane.setCenter(FXMLLoader.load(getClass().getResource("/view/categoryPane.fxml")));
    }

    @FXML
    private void billsBtnClicked(ActionEvent event) {
        select(billsBtn);
        resetAllExcept(billsBtn);
    }

    @FXML
    private void ordersBtnClicked(ActionEvent event) {
        select(ordersBtn);
        resetAllExcept(ordersBtn);
    }
    
    public void setCenterPane(Node node){
        borderPane.setCenter(node);
    }
    
}
