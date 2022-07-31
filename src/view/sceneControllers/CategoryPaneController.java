/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.sceneControllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
<<<<<<< HEAD

import javafx.animation.TranslateTransition;
=======
import javafx.event.ActionEvent;
>>>>>>> 27523d0ef7379d8b22f3e5c267dfc4473f2b3714
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author Natalia
 */
public class CategoryPaneController extends BaseView implements Initializable {

    final int GRIDPANE_COLUMNS_NUMBER = 3;
<<<<<<< HEAD
    final int ANIMATION_DURATION = 275;
    final int ANIMATION_DISTANCE = 700;
    final String PRODUCT_PANE_LOCATION ="/view/scene/productsPane.fxml";
=======
    
>>>>>>> 27523d0ef7379d8b22f3e5c267dfc4473f2b3714
    @FXML
    private BorderPane storeMainPane;
    @FXML
    private GridPane categoryContainer;
    @FXML
    private Button addCategoryButton;
    
    private Node productsPane = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        commController.setCategoryPaneController(this);
        
        ArrayList<HashMap<String,Object>> categories =  this.controllerForView.getAll("category");
        categories.forEach((category) -> {
            try{
                this.addCategory((String) category.get("name"));
            }catch (IOException ex){
                Logger.getLogger(CategoryPaneController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }    
    
<<<<<<< HEAD
    public void addCategory(HashMap<String,Object> category) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/scene/category.fxml"));
        Node categoryNode = loader.load();
=======
    public void addCategory(String label) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/scene/category.fxml"));
        Node category = loader.load();
>>>>>>> 27523d0ef7379d8b22f3e5c267dfc4473f2b3714
        CategoryController categoryContr = loader.getController();
        categoryContr.setCategoryName(label);
        int index = categoryContainer.getChildren().size();
        int columnIndex = index%this.GRIDPANE_COLUMNS_NUMBER;
        int rowIndex = (int) Math.floor(index/this.GRIDPANE_COLUMNS_NUMBER);
        categoryContainer.add(category, columnIndex, rowIndex);
    }

    @FXML
    private void addCategoryButtonClicked(ActionEvent event) throws IOException {
        BorderPane dashboardBorderPane = (BorderPane) storeMainPane.getParent();
        dashboardBorderPane.setRight(FXMLLoader.load(getClass().getResource("/view/scene/addCategory.fxml")));
    }
    
    public void showProductsForCategory(String category) throws IOException{
        if(this.productsPane == null){
<<<<<<< HEAD
            this.productsPane = FXMLLoader.load(getClass().getResource(this.PRODUCT_PANE_LOCATION));
=======
            this.productsPane = FXMLLoader.load(getClass().getResource("/view/scene/productsPane.fxml"));
>>>>>>> 27523d0ef7379d8b22f3e5c267dfc4473f2b3714
        }
        ProductsPaneController productsPaneContr = commController.getProductsPaneController();
        productsPaneContr.loadProductsByCategory(category);
        BorderPane dashboardBorderPane = (BorderPane) storeMainPane.getParent();
        dashboardBorderPane.setCenter(productsPane);
        dashboardBorderPane.setRight(null);
    }
    
}
