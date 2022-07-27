/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package view.sceneControllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

/**
 * FXML Controller class
 *
 * @author milar
 */

public class UtilitiesListController implements Initializable {
    
@FXML
private ListView<String> listaUtenze;

@FXML
private Label titoloLbl;
        
String[] utenze = {"n.1", "n.2", "n.3 "};
        
String selectedUtility;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        listaUtenze.getItems().addAll(utenze);
    }
        // TODO
    }    
    

