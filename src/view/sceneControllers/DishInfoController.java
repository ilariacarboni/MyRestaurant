/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package view.sceneControllers;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import model.entity.Menu;
/**
 * FXML Controller class
 *
 * @author milar
 * 
 */

    public class DishInfoController extends BaseView implements Initializable {

    @FXML
    private VBox chosenDishCard;

    @FXML
    private Button deleteBtn;

    @FXML
    private ImageView dishImg;

    @FXML
    private Label dishNameLbl;

    @FXML
    private Label dishPriceLbl;

    @FXML
    private Button modifyBtn;

    @FXML
    private TextField priceTxtfield;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }  
    
    public void setChosenDish(Menu menu){
        this.dishNameLbl.setText(menu.getNameDish());
        this.dishPriceLbl.setText(String.valueOf(menu.getPrice()));
    }
    
}
