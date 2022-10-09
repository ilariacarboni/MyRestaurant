/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package view.sceneControllers;

import business.MenuManager;
import model.entity.Menu;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
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
    private MenuManager menuManager = new MenuManager();
    
    private List<Menu> getData() { //definisce array di frutti -> dati da passare a entity Menu
        List<Menu> dishes = new ArrayList<>();
        Menu menu;

        menu = new Menu("Fettuccine",10,"antipasti","/view/style/img/menu-portate/finger-food.png");
        dishes.add(menu);

        menu = new Menu("Pane",2,"antipasti","/view/style/img/menu-portate/finger-food.png");
        dishes.add(menu);
        
        menu = new Menu("Polpette",5,"antipasti","/view/style/img/menu-portate/finger-food.png");
        dishes.add(menu);
        
        menu = new Menu("Bruschette",5,"antipasti","/view/style/img/menu-portate/finger-food.png");
        dishes.add(menu);
        
        return dishes;
        
    }
 
    public void initialize(URL location, ResourceBundle resources) {
       //definire metodo search
       //this.shownMenu = new ArrayList<>();
        dishes.addAll(getData());  
        
        int column = 0; //gridpane 0x1 con elementi menuItem
        int row = 1;
        try {
            for (int i = 0; i < dishes.size(); i++) {
                
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/view/scene/menuItem.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                menuItemController itemController = fxmlLoader.getController();
                itemController.setDishInfo(dishes.get(i)); //mylistener 

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
    
    
  /* public void loadDishesByCourses(String portata){
        portata = portata.toLowerCase();
       // this.courseLbl.setText(portata); //label per portata come titolo
        menuListGridPane.getChildren().clear();
        ArrayList<HashMap<String, Object>> dishes = this.menuManager.getFrom(course, "course");
        for(int i = 0; i<dishes.size(); i++){
            HashMap<String, Object> menuDish = dishes.get(i);
            this.addMenu(menuDish);
        }
    }
*/
   
   /*public void addMenu(HashMap<String, Object> dishInfo){
        int index = this.shownMenu.size() ;
        this.shownMenu.add(index, dishInfo);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/scene/menuItem.fxml"));
        Node menuNode = null;
        try {
            menuNode = loader.load();
            menuItemController menuContr = loader.getController();
            menuContr.setDishInfo(dishInfo); //definire metodo
           
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }*/
   
   @FXML
    void goBack(ActionEvent event) throws IOException {
      
      BorderPane borderPane = (BorderPane) menuListPane.getParent();
      borderPane.setCenter(FXMLLoader.load(getClass().getResource("/view/scene/MenuPanel.fxml"))); 
      borderPane.setRight(null);

    }
    
}
    
 
