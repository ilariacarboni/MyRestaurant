/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.sceneControllers;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import model.entity.Course;
import view.utils.LocatedImage;

/**
 *
 * @author milar
 */
public class portataItemController extends BaseView implements Initializable {
     @FXML
    private ImageView portataIcon;

    @FXML
    private AnchorPane portataItem;

    @FXML
    private Label portataLabel;

    @FXML
    private Label totaldishesinPortataLbl;

    @FXML
    private Label totaldishesinPortataValue;
    
    public Course portata;

    @FXML
    void courseHovered(MouseEvent event) {
        portataItem.getStyleClass().add("category-hover");
    }
    
    public void setCourseInfo(Course portata){
        this.portata = portata;
        
        portataLabel.setText(portata.getName());
        //portataIcon.setImage(new LocatedImage((String) portata.getImg()));
        Image image = new Image(getClass().getResource("/view/style/img/menu-portate/finger-food.png").toExternalForm());
        portataIcon.setImage(image);
        
        // aggiungere info ulteriori piatti in una categoria per label
    }

    @FXML
    void courseSelected(MouseEvent event) {
        
        try {
            commController.getMenuPaneController().showDishesForPortata(this.portataLabel.getText());  //definire metodo show..
        } catch (IOException ex) {
            Logger.getLogger(portataItemController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    void courseNotHovered(MouseEvent event) {
         if( portataItem.getStyleClass().contains("category-hover")){
            portataItem.getStyleClass().remove("category-hover");
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
