/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.sceneControllers;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import javafx.scene.image.ImageView;
import javafx.util.Duration;
import view.utils.LocatedImage;

/**
 * FXML Controller class
 *
 * @author Natalia
 */
public class CategoryController extends BaseView implements Initializable {

    final String TOTAL_PROD_IN_CATEGORY_LABEL = "Totale prodotti:";
    final String LAST_ORDER_LABEL = "Ultimo ordine:";
    final String MONTHLY_EXPENSE_LABEL = "Spesa media mensile:";
    @FXML
    public Label categoryLabel;
    @FXML
    public ImageView categoryIcon;
    @FXML
    public Label totalProductsInCategoryLabel;
    @FXML
    public Label lastOrderLabel;
    @FXML
    public Label monthlyExpenseLabel;
    @FXML
    private AnchorPane category;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void setCategoryInfo(HashMap<String, Object> info){
        String name = (String)info.get("name");
        this.categoryLabel.setText(name.substring(0, 1).toUpperCase() + name.substring(1));
        if(info.get("img") != null){
            this.categoryIcon.setImage(new LocatedImage((String) info.get("img")));
        }
        this.totalProductsInCategoryLabel.setText(this.TOTAL_PROD_IN_CATEGORY_LABEL);
        this.lastOrderLabel.setText(this.LAST_ORDER_LABEL);
        this.monthlyExpenseLabel.setText(this.MONTHLY_EXPENSE_LABEL);
    }

    @FXML
    private void categorySelected(MouseEvent event) throws IOException, InterruptedException {
        //visualizzazione slider -> a transizione finita invoco showProductForCategory
        try {
            commController.getCategoryPaneController().showProductsForCategory(this.categoryLabel.getText());
        } catch (IOException ex) {
            Logger.getLogger(CategoryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void categoryHovered(MouseEvent mouseEvent) {
        category.getStyleClass().add("category-hover");
    }

    public void catergoyNotHovered(MouseEvent mouseEvent) {
        if( category.getStyleClass().contains("category-hover")){
            category.getStyleClass().remove("category-hover");
        }
    }

    private void animateCategory(){
        int i=0;
        while(i<100){
            String style = String.format("-fx-background-color: linear-gradient(to right, #2D819D %d%%, #969696 %d%%);", i, i);
            category.setStyle(style);
            i++;
        }
        return;
    }
}
