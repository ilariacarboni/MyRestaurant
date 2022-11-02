/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package view.sceneControllers;

import business.EmployeeManager;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author milar
 */
public class EmployeesListController extends BaseView implements Initializable {
    @FXML
    private Button addEmployeeBtn;

    @FXML
    private ScrollPane employeeListScrollPane;

    @FXML
    private GridPane employeesGridPane;

    @FXML
    private BorderPane employeesPane;

    @FXML
    private TextField searchBar;

    @FXML
    private Label titoloLbl;
    
    final int ANIMATION_DURATION = 275;
    final int ANIMATION_DISTANCE = 700;
    final int GRIDPANE_COLUMNS_NUMBER = 2;
    
    private ArrayList employees;
    private EmployeeManager employeeManager = new EmployeeManager();

    @FXML
    void insertEmployeeBtnClicked(ActionEvent event) throws IOException {
      BorderPane borderPane = (BorderPane) employeesPane.getParent();
      borderPane.setRight(FXMLLoader.load(getClass().getResource("/view/scene/AddEmployee.fxml")));  

    }
    
    public void initialize(URL location, ResourceBundle resources) {
       
        commController.setEmployeePaneController(this);
        employeesPane.setStyle("-fx-background-image: url(\"/view/style/img/background/grey.jpeg\");-fx-background-repeat: no-repeat;");
        employeeListScrollPane.setStyle("-fx-background: url(\"/view/style/img/background/grey.jpeg\");-fx-background-repeat: no-repeat;");
        this.employees = new ArrayList<>();
        
        ArrayList<HashMap<String, Object>> employeeslist = this.employeeManager.getAll();
        for(int i = 0; i<employeeslist.size(); i++){
            HashMap<String, Object> employee = employeeslist.get(i);
            this.addEmployee(employee);
        }
    }

    private void addEmployee(HashMap<String, Object> employee) {
        int index = this.employees.size() ;
        this.employees.add(index,employee);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/scene/employeeItem.fxml"));
        
        Node empNode = null;
        try {
                empNode = loader.load();
                EmployeeItemController empitemContr = loader.getController();
                empitemContr.setEmployeeInfo(employee); //definire metodo
                int column = index%this.GRIDPANE_COLUMNS_NUMBER;
                int row = (int) Math.floor(index/this.GRIDPANE_COLUMNS_NUMBER);
               
                employeesGridPane.add(empNode,column ,row ); 
                this.animate();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void animate(){
        List<Node> employees = employeesGridPane.getChildren();
        for(Node employee: employees){
            TranslateTransition t = new TranslateTransition(Duration.millis(this.ANIMATION_DURATION), employee);
            t.setFromX(this.ANIMATION_DISTANCE);
            t.setToX(0);
            t.play();
        }
    }
    

}
