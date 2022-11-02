/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.sceneControllers;

import business.CourseManager;
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
import view.utils.BackButton;

/**
 *
 * @author milar
 */
public class MenuPaneController extends BaseView implements Initializable{
    
    private CourseManager courseManager = new CourseManager();
   
    final String MENU_PANE_LOCATION = "/view/scene/menuList.fxml";
    
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
    
    //public List<Course> portate = new ArrayList<>();
   
    
    @FXML
    void insertDishBtnClicked(ActionEvent event) throws IOException {
     
      BorderPane borderPane = (BorderPane) anchorPaneMenu.getParent();
      borderPane.setRight(FXMLLoader.load(getClass().getResource("/view/scene/AddMenuDish.fxml")));  

    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        commController.setMenuPaneController(this);
        anchorPaneMenu.setStyle("-fx-background-image: url(\"/view/style/img/background/grey.jpeg\");-fx-background-repeat: no-repeat;");
        ArrayList<HashMap<String,Object>> portate =  this.courseManager.getAll(); //lista portate
        HashMap<String, HashMap<String, Object>> coursesInfo = this.courseManager.getTotalDishes();
        portate.forEach((portata) -> {
            try{
                HashMap<String, Object> courseInfo = coursesInfo.get(portata.get("name"));
                portata.put("info", courseInfo);
                this.addCourse(portata);
            }catch (IOException ex){
                Logger.getLogger(MenuPaneController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
      
    }
    
        public void addCourse(HashMap<String,Object> course ) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/scene/PortataItem.fxml"));
        Node courseNode = loader.load();
        portataItemController portataContr = loader.getController();
        portataContr.setCourseInfo(course);
        int index = menuGridPane.getChildren().size();
        int columnIndex = index%3;
        int rowIndex = (int) Math.floor(index/3);
        menuGridPane.add(courseNode, columnIndex, rowIndex);
        this.animate();
        }

    @FXML
    void showDishesForPortata(HashMap<String, Object> course) throws IOException {
        
        if(this.menuListPane == null){
            this.menuListPane = FXMLLoader.load(getClass().getResource("/view/scene/menuList.fxml"));
        }
        MenuListController menuListContr = commController.getMenuListController();
        menuListContr.loadDishesByCourses(course);
        
        DashboardController dashboardController = commController.getDashboardController();
        BackButton backButton = this.makeBackButton(dashboardController);
        dashboardController.setCenterPane(menuListPane, backButton);
        //dashboardController.setRightPane(null);
        
    }
     private BackButton makeBackButton(DashboardController dc){
        BackButton backButton = new BackButton();
        backButton.setCenterScene(dc.getCenterPane());
        backButton.setRightScene(dc.getRightPane());
        backButton.setDashboardController(dc);
        return backButton;
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
      
}
