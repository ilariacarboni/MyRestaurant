/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.sceneControllers;

import business.CourseManager;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import view.utils.BackButton;
import view.utils.CustomGridPane;

/**
 *
 * @author milar
 */
public class MenuPaneController extends BaseView implements Initializable{

    public BorderPane anchorPaneMenu;
    public Button insertDishBtn;
    public Label titoloLbl;
    public ScrollPane menuPaneScrollPane;
    
    final int ANIMATION_DURATION = 275;
    final int ANIMATION_DISTANCE = 700;
    private CourseManager courseManager = new CourseManager();
    private Node menuListPane = null;
    private CustomGridPane menuGridPane;
    final int GRIDPANE_COLUMNS_NUMBER = 3;
    
    @FXML
    void insertDishBtnClicked(ActionEvent event) throws IOException {
      BorderPane borderPane = (BorderPane) anchorPaneMenu.getParent();
      borderPane.setRight(FXMLLoader.load(getClass().getResource(this.ADD_MENU_PATH)));  
        this.insertDishBtn.setVisible(false);
        this.insertDishBtn.setManaged(false);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        commController.setMenuPaneController(this);
        anchorPaneMenu.setBackground(imagesProvider.getBackground());
        
        menuGridPane = new CustomGridPane(this.GRIDPANE_COLUMNS_NUMBER);
        menuGridPane.setBreakPoint(0, 1100, 1);
        menuGridPane.setBreakPoint(1100, 1400, 2);
        menuGridPane.setBreakPoint(1400, Double.MAX_VALUE, 3);
        menuGridPane.startToListenForAdjustments(commController.getStage());
        menuGridPane.setHgap(20);
        menuGridPane.setVgap(20);
        menuGridPane.setPadding(new Insets(5, 20, 20, 20));
        
        menuPaneScrollPane.setContent(menuGridPane);
       
    }
    public void initializeCourses(){
        menuGridPane.getChildren().clear();
        ArrayList<HashMap<String,Object>> portate =  this.courseManager.getAll(); //lista portate
        imagesProvider.initializeCoursesImages(portate);
        HashMap<String, HashMap<String, Object>> coursesInfo = this.courseManager.getCoursesInfo();
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource(this.COURSE_ITEM_PATH));
        Node courseNode = loader.load();
        portataItemController portataContr = loader.getController();
        portataContr.setCourseInfo(course);
        int index = menuGridPane.getChildren().size();
        int columnIndex = index%GRIDPANE_COLUMNS_NUMBER;
        int rowIndex = (int) Math.floor(index/GRIDPANE_COLUMNS_NUMBER);
        menuGridPane.add(courseNode, columnIndex, rowIndex);
        this.animate();
    }

    @FXML
    void showDishesForPortata(HashMap<String, Object> course) throws IOException {
        
        if(this.menuListPane == null){
            this.menuListPane = FXMLLoader.load(getClass().getResource(this.MENU_LIST_PATH));
        }
        MenuListController menuListContr = commController.getMenuListController();
        menuListContr.loadDishesByCourses(course);
        
        DashboardController dashboardController = commController.getDashboardController();
        BackButton backButton = this.makeBackButton(dashboardController);
        backButton.setOnMouseClicked((e) -> {
            this.initializeCourses();
        });
        dashboardController.setCenterPane(menuListPane, backButton);
        
    }
     private BackButton makeBackButton(DashboardController dc){
        BackButton backButton = new BackButton();
        backButton.setCenterScene(dc.getCenterPane());
        backButton.setRightScene(dc.getRightPane());
        backButton.setDashboardController(dc);
        return backButton;
    }
     public void showAddDishBtn(){
        insertDishBtn.setVisible(true);
        insertDishBtn.setManaged(true);
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
