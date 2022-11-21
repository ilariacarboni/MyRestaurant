/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.sceneControllers;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author milar
 */
public class portataItemController extends BaseView implements Initializable {

    public ImageView portataIcon;
    public AnchorPane portataItem;
    public Label portataLabel;
    public Label totaldishesLbl;
    public Label totaldishesValue;
    public Label mostRequestedLbl;
    public Label mostrequestedValue;
    
    private HashMap<String, Object> portata;

    public void setCourseInfo(HashMap<String, Object> portata){
        this.portata = portata;
        String name = portata.get("name").toString();
        this.portataLabel.setText(name);
         if (portata.get("img") != null){
             this.portataIcon.setImage(imagesProvider.getCourseImage(name));
         }
        HashMap<String, Object> infoPortata = (HashMap<String, Object>) portata.get("info");
        if(infoPortata!=null){
            if(infoPortata.get("totalDishes") != null){
            this.totaldishesValue.setText(infoPortata.get("totalDishes").toString());
            }
            if(infoPortata.get("most-ordered") != null){
                this.mostrequestedValue.setText(infoPortata.get("most-ordered").toString());
            }
        }
        
    }

    @FXML
    void courseSelected(MouseEvent event) throws IOException, InterruptedException  {
        try {
            commController.getMenuPaneController().showDishesForPortata(this.portata);  
        } catch (IOException ex) {
            Logger.getLogger(portataItemController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    void courseHovered(MouseEvent event) {
        portataItem.getStyleClass().add("category-hover");
    }
    
    @FXML
    void courseNotHovered(MouseEvent event) {
         if( portataItem.getStyleClass().contains("category-hover")){
            portataItem.getStyleClass().remove("category-hover");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
    
}
