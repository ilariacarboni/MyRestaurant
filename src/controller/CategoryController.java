/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

/**
 * FXML Controller class
 *
 * @author Natalia
 */
public class CategoryController extends BaseView implements Initializable {

    @FXML
    private Button category;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void setCategoryName(String name){
        category.setText(name);
    }

    @FXML
    private void categorySelected(ActionEvent event) {
        try {
            commController.getCategoryPaneController().showProductsForCategory(this.category.getText());
        } catch (IOException ex) {
            Logger.getLogger(CategoryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
