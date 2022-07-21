/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import entity.Menu;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import view.MyListener;

public class MenuListController extends BaseView implements Initializable {

    @FXML
    private GridPane menuListGridPane;

    @FXML
    private ScrollPane menuListScrollPane;
    
    private List<Menu> dishes = new ArrayList<>();
    private MyListener myListener;
    
    private List<Menu> getData() { //definisce array di frutti -> dati da passare a entity Menu
        List<Menu> dishes = new ArrayList<>();
        Menu menu;

        menu = new Menu("Fettuccine",10,"antipasti");
        //menu.getNameDish("Kiwi");
        //menu.getPrice(5);
        //menu.getCategory("antipasti");
        //fruit.setImgSrc("/img/kiwi.png");
        //fruit.setColor("6A7324");
        dishes.add(menu);

        menu = new Menu("Pane",2,"antipasti");
        //menu.setName("Coconut");
        //menu.setPrice(3.99);
        //fruit.setImgSrc("/img/coconut.png");
        //fruit.setColor("A7745B");
        dishes.add(menu);
        
        menu = new Menu("polpette",5,"antipasti");
        dishes.add(menu);
        
        menu = new Menu("bruschette",6,"antipasti");
        dishes.add(menu);
        
        return dishes;
        
    }

    public void initialize(URL location, ResourceBundle resources) {
       
        dishes.addAll(getData());  
        
        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < dishes.size(); i++) {
                
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/view/menuItem.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                menuItemController itemController = fxmlLoader.getController();
                itemController.setData(dishes.get(i),myListener);

                if (column == 3) {
                    column = 0;
                    row++;
                }

                menuListGridPane.add(anchorPane, column++, row); //(child,column,row)
                //set grid width
               /* menuListGridPane.setMinWidth(Region.USE_COMPUTED_SIZE);
                menuListGridPane.setPrefWidth(Region.USE_COMPUTED_SIZE);
                menuListGridPane.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                menuListGridPane.setMinHeight(Region.USE_COMPUTED_SIZE);
                menuListGridPane.setPrefHeight(Region.USE_COMPUTED_SIZE);
                menuListGridPane.setMaxHeight(Region.USE_PREF_SIZE);*/
               

                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
