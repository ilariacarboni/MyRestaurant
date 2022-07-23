/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sun.xml.internal.ws.util.StringUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author Natalia
 */
public class CategoryController extends BaseView implements Initializable {

    @FXML
    public Label categoryLabel;
    @FXML
    public ImageView categoryIcon;
    @FXML
    private AnchorPane category;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void setCategoryInfo(HashMap<String, Object> info){
        String name = (String)info.get("name");
        this.categoryLabel.setText(name.substring(0, 1).toUpperCase() + name.substring(1));
        if(info.get("img") != null){
            this.categoryIcon.setImage(new Image((String) info.get("img")));
        }
    }

    @FXML
    private void categorySelected(MouseEvent event) {
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
}
