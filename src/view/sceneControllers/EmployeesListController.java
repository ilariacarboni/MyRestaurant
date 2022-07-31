<<<<<<< HEAD
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package view.sceneControllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author milar
 */
public class EmployeesListController extends BaseView implements Initializable {
    
    @FXML
    private Button dipendente1Btn;

    @FXML
    private GridPane gridPaneEmployees;

    @FXML
    private Label titoloLbl;
    
    @FXML
    private TextField employeeSearchBar;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        commController.setEmployeePaneController(this);
    }    
    
}
=======
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package view.sceneControllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author milar
 */
public class EmployeesListController extends BaseView implements Initializable {
    
    @FXML
    private Button dipendente1Btn;

    @FXML
    private GridPane gridPaneEmployees;

    @FXML
    private Label titoloLbl;
    
    @FXML
    private TextField employeeSearchBar;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        commController.setEmployeePaneController(this);
    }    
    
}
>>>>>>> 27523d0ef7379d8b22f3e5c267dfc4473f2b3714
