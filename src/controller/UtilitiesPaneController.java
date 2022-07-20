/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author milar
 */
public class UtilitiesPaneController extends BaseView implements Initializable  {

    
    @FXML
    private Button acquaBtn;
    
    @FXML
    private AnchorPane anchorPaneUtenze;

    @FXML
    private Button energiaBtn;

    @FXML
    private Button gasBtn;

    @FXML
    private Button insertutilityBtn;
  
    @FXML
    private Pane rightPane;
    
    @FXML
    private Pane leftPane;

    @FXML
    private Label titoloLbl;
   
    @FXML
    private Label dettagliLbl;
    
    @FXML
    private TextField searchBar;
    
    
    

 
   /* private AddUtility AddUtility;
    private Scene sceneinsertUtility; 
    private AddUtilityController addUtilityController;*/

    @FXML
    void insertUtilityBtnClicked(ActionEvent event) throws IOException {
        
       //rightPane = FXMLLoader.load(getClass().getResource("/Test/AddUtility.fxml"));
       
       
       //rightPane.getChildren().setAll(addUtilities);
        /*FXMLLoader loader = new FXMLLoader(getClass().getResource("/Test/AddUtility.fxml"));
        AnchorPane anchorpaneAddUtility = loader.load();
        BorderPane borderPane = (BorderPane) insertutilityBtn.getScene().getRoot();
        borderPane.setRight(anchorpaneAddUtility);*/
        
      BorderPane borderPane = (BorderPane) anchorPaneUtenze.getParent();
      borderPane.setRight(FXMLLoader.load(getClass().getResource("/view/AddUtility.fxml"))); 
      
        
 
      

    }
    
    //metodi che portano a secondo pane lista (3 categorie utenze) con click bottone 
    
   //metodo ricerca che porta a seccondo pane
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        commController.setUtilitiesPaneController(this);
       
    } 
    
}
