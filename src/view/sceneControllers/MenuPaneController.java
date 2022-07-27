<<<<<<< HEAD:src/view/sceneControllers/MenuPaneController.java
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
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
 *
 * @author milar
 */
public class MenuPaneController extends BaseView implements Initializable{
    
     @FXML
    private Button antipastiBtn;

    @FXML
    private Button bevandeBtn;

    @FXML
    private Button contorniBtn;

    @FXML
    private TextField dishSearchBar;

    @FXML
    private Button dolciBtn;

    @FXML
    private GridPane menuGridPane;

    @FXML
    private Button primiBtn;

    @FXML
    private Button secondiBtn;

    @FXML
    private Label titoloLbl;
    
@Override
public void initialize(URL url, ResourceBundle rb) {
        commController.setMenuPaneController(this);
    }

    
    
}
=======
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

/**
 *
 * @author milar
 */
public class MenuPaneController extends BaseView implements Initializable{
    
    @FXML
    private AnchorPane anchorPaneMenu;
    
    @FXML
    private Button antipastiBtn;

    @FXML
    private Button bevandeBtn;

    @FXML
    private Button contorniBtn;

    @FXML
    private TextField dishSearchBar;

    @FXML
    private Button dolciBtn;

    @FXML
    private GridPane menuGridPane;

    @FXML
    private Button primiBtn;

    @FXML
    private Button secondiBtn;

    @FXML
    private Label titoloLbl;
    
     @FXML
    void antipastiBtnClicked(ActionEvent event) throws IOException {
        
        BorderPane borderPane = (BorderPane) anchorPaneMenu.getParent();
        borderPane.setCenter(FXMLLoader.load(getClass().getResource("/view/menuList.fxml"))); 
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        commController.setMenuPaneController(this);
    }

    
    
}
>>>>>>> 75c338deaea6b1b1500fb9430a85d86a0134f511:src/controller/MenuPaneController.java
