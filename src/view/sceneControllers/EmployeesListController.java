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
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author milar
 */
public class EmployeesListController extends BaseView implements Initializable {
    public Button addEmployeeBtn;
    public ScrollPane employeeListScrollPane;
    public GridPane employeesGridPane;
    public BorderPane employeesPane;
    public TextField searchBar;
    public Label titoloLbl;
    
    final int ANIMATION_DURATION = 275;
    final int ANIMATION_DISTANCE = 700;
    final int GRIDPANE_COLUMNS_NUMBER = 2;
    
    private ArrayList employees;
    private EmployeeManager employeeManager = new EmployeeManager();
    private final String EMPLOYEE_NAME = "#nameLbl";
    private final String EMPLOYEE_SURNAME = "#surnameLbl";

    @FXML
    void insertEmployeeBtnClicked(ActionEvent event) throws IOException {
        BorderPane borderPane = (BorderPane) employeesPane.getParent();
        borderPane.setRight(FXMLLoader.load(getClass().getResource(this.ADD_EMPLOYEE_PATH)));
    }
    
    public void initialize(URL location, ResourceBundle resources) {
       
        commController.setEmployeePaneController(this);
        employeesPane.setBackground(imagesProvider.getBackground());
        this.employees = new ArrayList<>();
        
        ArrayList<HashMap<String, Object>> employeeslist = this.employeeManager.getAll();
        imagesProvider.initializeEmployeesImages(employeeslist);
        for(int i = 0; i<employeeslist.size(); i++){
            HashMap<String, Object> employee = employeeslist.get(i);
            this.addEmployee(employee);
        }
        
        searchBar.textProperty().addListener((observable, oldValue, newValue) ->{
            ObservableList<Node> employeesearch = employeesGridPane.getChildren();
            for(Node employee : employeesearch){
                Label employeeNameLabel = (Label)((AnchorPane) employee).lookup(this.EMPLOYEE_NAME);
                String employeeName = employeeNameLabel.getText();
                Label employeeSurnameLabel = (Label)((AnchorPane) employee).lookup(this.EMPLOYEE_SURNAME);
                String employeeSurname = employeeSurnameLabel.getText();
                if(!employeeName.contains(newValue) && !employeeSurname.contains(newValue) ){
                    employee.setVisible(false);
                    employee.setManaged(false);
                }else{
                    employee.setVisible(true);
                    employee.setManaged(true);
                }
            }
        });
    }

    private void addEmployee(HashMap<String, Object> employee) {
        int index = this.employees.size() ;
        this.employees.add(index,employee);
        FXMLLoader loader = new FXMLLoader(getClass().getResource(this.EMPLOYEE_ITEM_PATH));
        
        Node empNode = null;
        try {
            empNode = loader.load();
            EmployeeItemController empitemContr = loader.getController();
            empitemContr.setEmployeeInfo(employee); 
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
