/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.sceneControllers;

import java.io.IOException;
import java.net.URL;
import static java.nio.file.Files.delete;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.TranslateTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import model.entity.Course;

/**
 *
 * @author milar
 */
public class MenuPaneController extends BaseView implements Initializable{
    
    //private CourseManager courseManager = new CourseManager();
   
   // final String PRODUCT_PANE_LOCATION = "/view/scene/productsPane.fxml";
    
    private Node menuListPane = null;
    
    final int ANIMATION_DURATION = 275;
    final int ANIMATION_DISTANCE = 700;
    @FXML
    private BorderPane anchorPaneMenu;
    
    @FXML
    private Button insertDishBtn;
    @FXML
    private TextField dishSearchBar;

    @FXML
    private GridPane menuGridPane;

    @FXML
    private Label titoloLbl;
    
    public List<Course> portate = new ArrayList<>();
    private Image image;
    
    @FXML
    void insertDishBtnClicked(ActionEvent event) throws IOException {
     
      BorderPane borderPane = (BorderPane) anchorPaneMenu.getParent();
      borderPane.setRight(FXMLLoader.load(getClass().getResource("/view/scene/AddMenuDish.fxml")));  

    }
    
    private List<Course> coursesList() {
        List<Course> portate = new ArrayList<>();
        Course portata;

        portata = new Course("Antipasti","/view/style/img/menu-portate/finger-food.png");
        portate.add(portata); 

        portata = new Course("Pimi","/view/style/img/category-icons/pasta.png");
        portate.add(portata);
        
        portata = new Course("Secondi","/view/style/img/menu-portate/meat.png"); 
        portate.add(portata);
        
        portata = new Course("Contorni","/view/style/img/menu-portate/vegetables.png");
        portate.add(portata);
        
        portata = new Course("Dolci","/view/style/img/category-icons/dessert.png");
        portate.add(portata);
        
        portata = new Course("Bevande","/view/style/img/menu-portate/alcoholic-drink.png");
        portate.add(portata);
        
        return portate;
       //this.animate();
    }
    
    public void animate(){
        List<Node> courses = menuGridPane.getChildren();
        for(Node course: courses){
            TranslateTransition t = new TranslateTransition(Duration.millis(this.ANIMATION_DURATION), course);
            t.setFromX(this.ANIMATION_DISTANCE);
            t.setToX(0);
            t.play();
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       commController.setMenuPaneController(this);
        
       portate.addAll(coursesList());
       int column=3;
       int row=0;
       try {
            for (int i = 0; i < portate.size(); i++) {
                
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/view/scene/PortataItem.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                portataItemController itemController = fxmlLoader.getController();
                itemController.setCourseInfo(portate.get(i));
                if (column == 3) {
                    column = 0;
                    row++;
                }

                menuGridPane.add(anchorPane, column++, row); //(child,column,row)
                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
   
    @FXML
    void showDishesForPortata(String portata) throws IOException {
        
        BorderPane borderPane = (BorderPane) anchorPaneMenu.getParent();
        borderPane.setCenter(FXMLLoader.load(getClass().getResource("/view/scene/menuList.fxml"))); 
        borderPane.setRight(FXMLLoader.load(getClass().getResource("/view/scene/dishInfo.fxml")));
        
        /*if(this.menuListPane == null){
            this.menuListPane = FXMLLoader.load(getClass().getResource("/view/scene/menuList.fxml"));
        }
        MenuListController menuListContr = commController.getMenuListController();
        menuListContr.emptyProductInfo();
        menuListContr.loadDishesByCourse(portata);
        DashboardController dashboardController = commController.getDashboardController();
        //productsPaneContr.makeBackButton(dashboardController.getCenterPane(), dashboardController.getRightPane());
        dashboardController.setCenterPane(productsPane);
        dashboardController.setRightPane(null);*/
        
    }
      
}
