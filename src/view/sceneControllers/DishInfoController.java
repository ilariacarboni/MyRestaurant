/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package view.sceneControllers;

import business.MenuManager;
import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import model.entity.Menu;
import view.utils.LocatedImage;
/**
 * FXML Controller class
 *
 * @author milar
 * 
 */

    public class DishInfoController extends BaseView implements Initializable {

    private final String DEFAULT_IMAGE_PATH = "src/view/style/img/others/default-dish.png";
    private MenuManager menuManager;    
    @FXML
    private VBox chosenDishCard;

    @FXML
    private Button deleteBtn;

    @FXML
    private ImageView dishImg;

    @FXML
    private Label dishNameLbl;

    /*@FXML
    private Label dishPriceLbl;*/

    @FXML
    private Button modifyBtn;

    @FXML
    private TextField priceTxtfield;
    
    private HashMap<String, Object> dishInfo;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         commController.setDishInfoController(this);
         this.menuManager = new MenuManager();
    }  
    
    public void setChosenDish(HashMap<String, Object> menu){
        this.dishInfo = menu;
        this.dishNameLbl.setText(String.valueOf(menu.get("nameDish")));
       // this.dishPriceLbl.setText(String.valueOf(menu.get("price")));
       if (menu.get("image") != null){
             this.dishImg.setImage(new LocatedImage((String) menu.get("image"))); 
        }else{
            this.dishImg.setImage(new Image(new File(this.DEFAULT_IMAGE_PATH).toURI().toString()));
        }
        
    }
    
    
    //scelta immagine
    
    //modifica prezzo con rif a update 
    
    //cancellazione menu delete
    
}
