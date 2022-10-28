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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;


/**
 * FXML Controller class
 *
 * @author milar
 */
public class UtilitiesPaneController extends BaseView implements Initializable  {

    
    @FXML
    private MenuButton filterMenu;

    @FXML
    private Button insertUtilitiesBtn;

    @FXML
    private TextField searchBar;

    @FXML
    private BorderPane utilitiesBorderPane;

    @FXML
    private GridPane utilitiesGridPane;

    @FXML
    private ScrollPane utilitiesScrollPane;
    
    private ArrayList utilities ;
    
    final int GRIDPANE_COLUMNS_NUMBER = 1;
    final int ANIMATION_DURATION = 275;
    final int ANIMATION_DISTANCE = 700;
    
    private UtilityManager utilityManager = new UtilityManager();
    

    @FXML
    void insertUtilityBtnClicked(ActionEvent event) throws IOException {
      BorderPane borderPane = (BorderPane) utilitiesBorderPane.getParent();
      borderPane.setRight(FXMLLoader.load(getClass().getResource(this.ADD_UTILITIES_PANE_PATH))); 
      
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        commController.setUtilitiesPaneController(this);
        this.utilities = new ArrayList<>();
        //ricerca utilities
        ArrayList<HashMap<String, Object>> utilitieslist = this.utilityManager.getAll();
        for(int i = 0; i<utilitieslist.size(); i++){
            HashMap<String, Object> utility = utilitieslist.get(i);
            try {
                this.addUtility(utility,i);
            } catch (IOException ex) {
                Logger.getLogger(UtilitiesPaneController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    } 
    
    private void addUtility(HashMap<String, Object> utility, int i ) throws IOException {
        /*int index = this.utilities.size() ;
        this.utilities.add(index, utility);*/
        FXMLLoader loader = new FXMLLoader(getClass().getResource(UTILITY_ITEM_PATH));
        Node utilityNode = loader.load();
        UtilityItemController utilityitemContr = loader.getController();
        utilityitemContr.setUtilityInfo(utility); 
        //int row = (int) Math.floor(index/this.GRIDPANE_COLUMNS_NUMBER);

        utilitiesGridPane.add(utilityNode, 0 , i); 
        this.animate();
        
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
