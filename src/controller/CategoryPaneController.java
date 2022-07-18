/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;

/**
 * FXML Controller class
 *
 * @author Natalia
 */
public class CategoryPaneController extends BaseView implements Initializable {


    @FXML
    private BorderPane storeMainPane;
    @FXML
    private FlowPane categoryContainer;
    @FXML
    private Button addCategoryButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        commController.setCategoryPaneController(this);
        
        ArrayList<HashMap<String,Object>> categories =  this.controllerForView.getAll("category");
        for(HashMap<String,Object> category: categories){
            try {
                this.addCategory((String) category.get("name"));
            } catch (IOException ex) {
                Logger.getLogger(CategoryPaneController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }    

    private void testCategorySelected(ActionEvent event) throws IOException {
        BorderPane dashboardBorderPane = (BorderPane) storeMainPane.getParent();
        dashboardBorderPane.setCenter(FXMLLoader.load(getClass().getResource("/view/testCategoryProducts.fxml")));
    }
    
    public void addCategory(String label) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/category.fxml"));
        Node category = loader.load();
        CategoryController categoryContr = loader.getController();
        categoryContr.setCategoryName(label);
        categoryContainer.getChildren().add(category);
        
    }

    @FXML
    private void addCategoryButtonClicked(ActionEvent event) throws IOException {
        BorderPane dashboardBorderPane = (BorderPane) storeMainPane.getParent();
        dashboardBorderPane.setRight(FXMLLoader.load(getClass().getResource("/view/addCategory.fxml")));
    }
    
    public void showProductsForCategory(String category) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/productsPane.fxml"));
        Node productsPane = loader.load();
        ProductsPaneController productsPaneContr = loader.getController();
        productsPaneContr.loadProductsByCategory(category);
        BorderPane dashboardBorderPane = (BorderPane) storeMainPane.getParent();
        dashboardBorderPane.setCenter(productsPane);
    }
    
}
