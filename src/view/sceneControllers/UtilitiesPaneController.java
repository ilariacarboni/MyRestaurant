/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package view.sceneControllers;

import business.UtilityManager;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;


/**
 * FXML Controller class
 *
 * @author milar
 */
public class UtilitiesPaneController extends BaseView implements Initializable  {

    public MenuButton filterMenu;
    public Button insertUtilitiesBtn;
    public TextField idsearchBar;
    public TextField datesearchBar;
    public BorderPane utilitiesBorderPane;
    public GridPane utilitiesGridPane;
    public ScrollPane utilitiesScrollPane;
    public AnchorPane selectpageContainer;
    

    private final int GRIDPANE_COLUMNS_NUMBER = 1;
    private final int ANIMATION_DURATION = 275;
    private final int ANIMATION_DISTANCE = 700;

    private UtilityManager utilityManager = new UtilityManager();
    private ArrayList utilities ;
    private int pageNumber = 1;
    private int lastPage;
    private int index = 0;
    private int totalUtilities;
    
    private final String UTILITY_ID = "#numberidLbl";
    private final String UTILITY_TOTAL = "#totalLbl";
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        commController.setUtilitiesPaneController(this);
        
        utilitiesBorderPane.setBackground(imagesProvider.getBackground());
        utilitiesGridPane.getChildren().clear();
        totalUtilities = utilityManager.getTotalUtilities();
        
        utilityManager.setUtilitiesPageLength(7);
        lastPage = (int)(Math.ceil(totalUtilities/7));
        this.insertUtilitiesInPage(1);
        //ricerca per codice 
        idsearchBar.textProperty().addListener((observable, oldValue, newValue) ->{
            insertUtilitiesInPage(pageNumber);
        });
        datesearchBar.textProperty().addListener((observable, oldValue, newValue) ->{
            insertUtilitiesInPage(pageNumber);
        });
        
    } 
    
    @FXML
    void insertUtilityBtnClicked(ActionEvent event) throws IOException {
      BorderPane borderPane = (BorderPane) utilitiesBorderPane.getParent();
      borderPane.setRight(FXMLLoader.load(getClass().getResource(this.ADD_UTILITIES_PANE_PATH))); 
      
    }
    
    @FXML
    void goToNextPage(MouseEvent event) {
        if(this.pageNumber < lastPage){
            pageNumber++;
            this.insertUtilitiesInPage(pageNumber);
        }
    }

    @FXML
    void goToPreviousPage(MouseEvent event) {
         if(this.pageNumber != 1){
            pageNumber --;
            this.insertUtilitiesInPage(pageNumber);
        }
    }
    
    private void insertUtilitiesInPage(int pageNumber){
        HashMap<String, String> filters = this.getFilters();
        this.utilitiesGridPane.getChildren().clear();
        index = 0;
        ArrayList<HashMap<String, Object>> utilitieslist = this.utilityManager.getAllbyPage(pageNumber,filters);
        utilitieslist.forEach((utility)->{
            try {
                this.addUtility(utility, index);
                index ++;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
    
    private HashMap<String, String> getFilters(){
        String utilityNumberFilter = this.idsearchBar.getText();
        String dateFilter = this.datesearchBar.getText();
        HashMap<String, String> res = new HashMap<>();
        res.put("numberId", utilityNumberFilter);
        res.put("date", dateFilter);
        return res;
    }
    
    private void addUtility(HashMap<String, Object> utility, int i ) throws IOException {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource(this.UTILITY_ITEM_PATH));
        Node utilityNode = loader.load();
        UtilityItemController utilityitemContr = loader.getController();
        utilityitemContr.setUtilityInfo(utility); 

        utilitiesGridPane.add(utilityNode, 0 , i); 
        this.animate();
        
    }
    
    public void updateUtilities(){
        pageNumber = 1;
        this.insertUtilitiesInPage(1);
    }
    
    public void animate(){
        List<Node> utilities = utilitiesGridPane.getChildren();
        for(Node utility: utilities){
            TranslateTransition t = new TranslateTransition(Duration.millis(this.ANIMATION_DURATION), utility);
            t.setFromX(this.ANIMATION_DISTANCE);
            t.setToX(0);
            t.play();
        }
    }
    
}
