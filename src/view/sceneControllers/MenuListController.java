/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package view.sceneControllers;

import model.entity.Menu;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

public class MenuListController extends BaseView implements Initializable {

    @FXML
    private GridPane menuListGridPane;

    @FXML
    private ScrollPane menuListScrollPane;
    
    @FXML
    private BorderPane menuListPane;
     
    @FXML
    private Button backBtn;

    @FXML
    private ImageView backImg;
    
    private List<Menu> dishes = new ArrayList<>();
    //private MyListener myListener;
    
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
        
        menu = new Menu("Polpette",5,"antipasti");
        dishes.add(menu);
        
        menu = new Menu("Bruschette",5,"antipasti");
        dishes.add(menu);
        
        return dishes;
        
    }
    
    @FXML
    void goBack(ActionEvent event) throws IOException {
        
      BorderPane borderPane = (BorderPane) menuListPane.getParent();
      borderPane.setCenter(FXMLLoader.load(getClass().getResource("/view/scene/MenuPanel.fxml"))); 
      borderPane.setRight(null);

    }
    

    public void initialize(URL location, ResourceBundle resources) {
       
        dishes.addAll(getData());  
        
        int column = 0; //gridpane 0x1 con elementi menuItem
        int row = 1;
        try {
            for (int i = 0; i < dishes.size(); i++) {
                
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/view/scene/menuItem.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                menuItemController itemController = fxmlLoader.getController();
                itemController.setData(dishes.get(i)); //mylistener 

                if (column == 3) {
                    column = 0;
                    row++;
                }

                menuListGridPane.add(anchorPane, column++, row); //(child,column,row)
                
                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
        /*private void setChosenDish(Menu menu) {
        fruitNameLable.setText(fruit.getName());
        fruitPriceLabel.setText(Main.CURRENCY + fruit.getPrice());
        image = new Image(getClass().getResourceAsStream(fruit.getImgSrc()));
        fruitImg.setImage(image);
        chosenFruitCard.setStyle("-fx-background-color: #" + fruit.getColor() + ";\n" +
                "    -fx-background-radius: 30;");
    }*/
}
